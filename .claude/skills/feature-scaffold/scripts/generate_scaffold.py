#!/usr/bin/env python3
"""Android Feature Scaffold Generator.

Usage:
    python3 generate_scaffold.py --name Profile --layers all
    python3 generate_scaffold.py --name Settings --layers feature
    python3 generate_scaffold.py --name Forecast --layers domain,data

Config is read from config.json next to this script's parent SKILL.md.
Falls back to auto-detection from existing project files if config.json is absent.
"""

import argparse
import json
import re
import sys
from pathlib import Path


# ---------------------------------------------------------------------------
# CONFIG
# ---------------------------------------------------------------------------

_REQUIRED_KEYS = ("base_package", "plugin_namespace", "theme_package", "theme_class")


def load_config(root: Path) -> dict:
    """Load config from config.json (preferred) or auto-detect from project files."""
    config_path = Path(__file__).parent.parent / "config.json"
    if config_path.exists():
        cfg = json.loads(config_path.read_text())
        missing = [k for k in _REQUIRED_KEYS if k not in cfg]
        if missing:
            print(f"Error: config.json is missing keys: {missing}", file=sys.stderr)
            sys.exit(1)
        return cfg

    return _auto_detect(root)


def _auto_detect(root: Path) -> dict:
    """Fallback: detect config from existing Kotlin/Gradle files."""
    cfg = {}

    for gradle in root.glob("feature/*/build.gradle.kts"):
        text = gradle.read_text()
        ns = re.search(r'namespace\s*=\s*"([^"]+)"', text)
        if ns:
            parts = ns.group(1).split(".feature.")
            if len(parts) == 2:
                cfg["base_package"] = parts[0]
        plugin = re.search(r'alias\(libs\.plugins\.(\w+)\.android\.\w+\)', text)
        if plugin:
            cfg["plugin_namespace"] = plugin.group(1)
        if "base_package" in cfg and "plugin_namespace" in cfg:
            break

    for kt in root.glob("feature/*/src/**/*Screen.kt"):
        text = kt.read_text()
        m = re.search(r'import ([\w.]+\.(\w+Theme))\b', text)
        if m:
            full = m.group(1)
            cfg["theme_package"] = full.rsplit(".", 1)[0]
            cfg["theme_class"] = m.group(2)
            break

    missing = [k for k in _REQUIRED_KEYS if k not in cfg]
    if missing:
        print(f"Error: could not detect: {missing}", file=sys.stderr)
        print("Fix: fill in .claude/skills/feature-scaffold/config.json", file=sys.stderr)
        sys.exit(1)

    return cfg


# ---------------------------------------------------------------------------
# HELPERS
# ---------------------------------------------------------------------------

def to_lower(name: str) -> str:
    return name.lower()


def pascal(name: str) -> str:
    return name[0].upper() + name[1:] if name else name


def write(path: Path, content: str, root: Path):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content.lstrip() + "\n")
    print(f"  ✓ {path.relative_to(root)}")


def src_path(layer: str, name: str, root: Path, base_pkg: str) -> Path:
    n = to_lower(name)
    return root / layer / n / "src" / "main" / "kotlin" / Path(*base_pkg.split(".")) / layer / n


# ---------------------------------------------------------------------------
# FEATURE LAYER
# ---------------------------------------------------------------------------

def generate_feature(name: str, root: Path, cfg: dict):
    n = to_lower(name)
    N = pascal(name)
    base_pkg = cfg["base_package"]
    plugin_ns = cfg["plugin_namespace"]
    theme = cfg["theme_class"]
    theme_pkg = cfg["theme_package"]
    pkg = f"{base_pkg}.feature.{n}"
    s = src_path("feature", name, root, base_pkg)
    w = lambda path, content: write(path, content, root)

    w(root / "feature" / n / "build.gradle.kts", f"""
plugins {{
  alias(libs.plugins.{plugin_ns}.android.feature)
}}

android {{
  namespace = "{pkg}"
}}

dependencies {{
  implementation(project(":domain:{n}"))
}}
""")

    w(root / "feature" / n / "src" / "main" / "AndroidManifest.xml", """
<?xml version="1.0" encoding="utf-8"?>
<manifest />
""")

    w(root / "feature" / n / "src" / "main" / "res" / "values" / "strings.xml", f"""
<?xml version="1.0" encoding="utf-8"?>
<resources>
  <string name="{n}_screen_title">TODO</string>
</resources>
""")

    w(s / "di" / f"Feature{N}Module.kt", f"""
package {pkg}.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("{pkg}")
class Feature{N}Module
""")

    w(s / "presentation" / f"{N}Action.kt", f"""
package {pkg}.presentation

internal sealed interface {N}Action {{
  data object RefreshClick : {N}Action
}}
""")

    w(s / "presentation" / f"{N}Event.kt", f"""
package {pkg}.presentation

internal sealed interface {N}Event {{
  data object NavigateBack : {N}Event
}}
""")

    w(s / "presentation" / "state" / f"{N}UiState.kt", f"""
package {pkg}.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface {N}UiState {{

  @Immutable
  data object Loading : {N}UiState

  @Immutable
  data class Loaded(val items: List<{N}ItemUiState>) : {N}UiState

  @Immutable
  data class Error(val message: String) : {N}UiState
}}
""")

    w(s / "presentation" / "state" / f"{N}ItemUiState.kt", f"""
package {pkg}.presentation.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class {N}ItemUiState(
  val id: String,
  val title: String
)
""")

    w(s / "presentation" / f"{N}StateFactory.kt", f"""
package {pkg}.presentation

import {base_pkg}.domain.{n}.model.{N}Item
import {pkg}.presentation.state.{N}ItemUiState
import {pkg}.presentation.state.{N}UiState
import org.koin.core.annotation.Factory

@Factory
internal class {N}StateFactory {{

  fun create(items: List<{N}Item>): {N}UiState.Loaded =
    {N}UiState.Loaded(items = items.map(::createItem))

  private fun createItem(item: {N}Item): {N}ItemUiState =
    {N}ItemUiState(
      id = item.id,
      title = item.title
    )
}}
""")

    w(s / "presentation" / f"{N}ViewModel.kt", f"""
package {pkg}.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import {base_pkg}.domain.{n}.model.{N}Item
import {base_pkg}.domain.{n}.usecase.Fetch{N}Data
import {pkg}.presentation.{N}Action.RefreshClick
import {pkg}.presentation.state.{N}UiState
import {pkg}.presentation.state.{N}UiState.Loading
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class {N}ViewModel(
  private val fetch{N}Data: Fetch{N}Data,
  private val stateFactory: {N}StateFactory
) : ViewModel() {{

  private val _state = MutableStateFlow<{N}UiState>(Loading)
  val state: StateFlow<{N}UiState> = _state.asStateFlow()

  private val _event = Channel<{N}Event>()
  val event: Flow<{N}Event> = _event.receiveAsFlow()

  init {{
    load()
  }}

  fun dispatch(action: {N}Action) {{
    when (action) {{
      is RefreshClick -> onRefreshClick()
    }}
  }}

  private fun onRefreshClick() {{
    load()
  }}

  private fun load() {{
    _state.update {{ Loading }}
    fetch{N}Data()
      .onEach {{ on{N}Result(it) }}
      .launchIn(viewModelScope)
  }}

  private fun on{N}Result(result: Result<List<{N}Item>>) {{
    result
      .onSuccess {{ on{N}Success(it) }}
      .onFailure {{ on{N}Error(it) }}
  }}

  private fun on{N}Success(items: List<{N}Item>) {{
    _state.update {{ stateFactory.create(items) }}
  }}

  private fun on{N}Error(error: Throwable) {{
    _state.update {{ {N}UiState.Error(error.message.orEmpty()) }}
  }}

  private fun send(event: {N}Event) {{
    viewModelScope.launch {{ _event.send(event) }}
  }}
}}
""")

    w(s / "ui" / f"{N}Resources.kt", f"""
package {pkg}.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import {base_pkg}.feature.{n}.R
import org.koin.core.annotation.Factory

@Factory
internal class {N}Resources {{

  object Texts {{

    @Composable
    fun screenTitle(): String =
      stringResource(R.string.{n}_screen_title)
  }}
}}
""")

    w(s / "ui" / "screen" / f"{N}Screen.kt", f"""
package {pkg}.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import {theme_pkg}.{theme}
import {pkg}.presentation.{N}Action
import {pkg}.presentation.{N}Event.NavigateBack
import {pkg}.presentation.{N}ViewModel
import {pkg}.presentation.state.{N}UiState
import {pkg}.presentation.state.{N}UiState.Error
import {pkg}.presentation.state.{N}UiState.Loaded
import {pkg}.presentation.state.{N}UiState.Loading
import org.koin.androidx.compose.koinViewModel

@Composable
fun {N}Screen(
  onNavigateBack: () -> Unit
) {{
  val viewModel: {N}ViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {{
    viewModel.event.collect {{ event ->
      when (event) {{
        is NavigateBack -> onNavigateBack()
      }}
    }}
  }}

  {N}Content(
    state = state,
    dispatch = viewModel::dispatch
  )
}}

@Composable
internal fun {N}Content(
  modifier: Modifier = Modifier,
  state: {N}UiState,
  dispatch: ({N}Action) -> Unit
) {{
  when (state) {{
    is Loading -> {N}LoadingState(modifier)
    is Loaded -> {N}LoadedContent(modifier = modifier, state = state, dispatch = dispatch)
    is Error -> {N}ErrorState(modifier = modifier, message = state.message)
  }}
}}

@Composable
private fun {N}LoadedContent(
  modifier: Modifier = Modifier,
  state: {N}UiState.Loaded,
  dispatch: ({N}Action) -> Unit
) {{
  // TODO: implement {N} loaded UI — state.items available
}}

@PreviewLightDark
@Composable
private fun Preview() {{
  {theme} {{
    {N}Content(
      state = Loading,
      dispatch = {{}}
    )
  }}
}}
""")

    w(s / "ui" / "screen" / f"{N}LoadingState.kt", f"""
package {pkg}.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import {theme_pkg}.{theme}
import {theme_pkg}.{theme}.colors

@Composable
internal fun {N}LoadingState(modifier: Modifier = Modifier) {{
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {{
    CircularProgressIndicator(color = colors.accent)
  }}
}}

@PreviewLightDark
@Composable
private fun Preview() {{
  {theme} {{
    {N}LoadingState()
  }}
}}
""")

    w(s / "ui" / "screen" / f"{N}ErrorState.kt", f"""
package {pkg}.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import {theme_pkg}.AppDimens.PaddingMedium
import {theme_pkg}.{theme}
import {theme_pkg}.{theme}.colors
import {theme_pkg}.{theme}.typography

@Composable
internal fun {N}ErrorState(
  modifier: Modifier = Modifier,
  message: String
) {{
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {{
    Text(
      text = message,
      color = colors.onSurfaceVariant,
      style = typography.bodyMedium,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(PaddingMedium)
    )
  }}
}}

@PreviewLightDark
@Composable
private fun Preview() {{
  {theme} {{
    {N}ErrorState(message = "Something went wrong")
  }}
}}
""")

    print(f"\n  ✓ :feature:{n} — 12 files")


# ---------------------------------------------------------------------------
# DOMAIN LAYER
# ---------------------------------------------------------------------------

def generate_domain(name: str, root: Path, cfg: dict):
    n = to_lower(name)
    N = pascal(name)
    base_pkg = cfg["base_package"]
    plugin_ns = cfg["plugin_namespace"]
    pkg = f"{base_pkg}.domain.{n}"
    s = src_path("domain", name, root, base_pkg)
    w = lambda path, content: write(path, content, root)

    w(root / "domain" / n / "build.gradle.kts", f"""
plugins {{
  alias(libs.plugins.{plugin_ns}.android.library)
  alias(libs.plugins.{plugin_ns}.android.koin)
  alias(libs.kotlin.serialization)
}}

android {{
  namespace = "{pkg}"
}}

dependencies {{
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.koin.core)
}}
""")

    w(s / "di" / f"Domain{N}Module.kt", f"""
package {pkg}.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("{pkg}")
class Domain{N}Module
""")

    w(s / "model" / f"{N}Item.kt", f"""
package {pkg}.model

data class {N}Item(
  val id: String,
  val title: String
)
""")

    w(s / "repository" / f"{N}Repository.kt", f"""
package {pkg}.repository

import {pkg}.model.{N}Item

interface {N}Repository {{
  suspend fun fetch{N}Items(): List<{N}Item>
}}
""")

    w(s / "usecase" / f"Fetch{N}Data.kt", f"""
package {pkg}.usecase

import {pkg}.model.{N}Item
import {pkg}.repository.{N}Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class Fetch{N}Data(private val repository: {N}Repository) {{

  operator fun invoke(): Flow<Result<List<{N}Item>>> =
    flow {{
      val result = repository.fetch{N}Items()
      emit(Result.success(result))
    }}
      .catch {{ emit(Result.failure(it)) }}
}}
""")

    print(f"  ✓ :domain:{n} — 5 files")


# ---------------------------------------------------------------------------
# DATA LAYER
# ---------------------------------------------------------------------------

def generate_data(name: str, root: Path, cfg: dict):
    n = to_lower(name)
    N = pascal(name)
    base_pkg = cfg["base_package"]
    plugin_ns = cfg["plugin_namespace"]
    pkg = f"{base_pkg}.data.{n}"
    domain_pkg = f"{base_pkg}.domain.{n}"
    s = src_path("data", name, root, base_pkg)
    w = lambda path, content: write(path, content, root)

    w(root / "data" / n / "build.gradle.kts", f"""
plugins {{
  alias(libs.plugins.{plugin_ns}.android.library)
  alias(libs.plugins.{plugin_ns}.android.koin)
  alias(libs.plugins.{plugin_ns}.android.room)
  alias(libs.plugins.{plugin_ns}.android.ktor)
}}

android {{
  namespace = "{pkg}"
}}

dependencies {{
  implementation(project(":domain:{n}"))
  implementation(project(":core:network"))
  implementation(libs.koin.android)
}}
""")

    w(s / "di" / f"Data{N}Module.kt", f"""
package {pkg}.di

import android.content.Context
import androidx.room.Room
import {pkg}.local.{N}Database
import {pkg}.local.dao.{N}Dao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("{pkg}")
class Data{N}Module {{

  @Single
  fun provide{N}Database(context: Context): {N}Database =
    Room.databaseBuilder(
      context = context,
      klass = {N}Database::class.java,
      name = "{n}.db"
    ).build()

  @Single
  fun provide{N}Dao(database: {N}Database): {N}Dao =
    database.{n}Dao()
}}
""")

    w(s / "remote" / "dto" / f"{N}Response.kt", f"""
package {pkg}.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class {N}Response(
  @SerialName("id") val id: String,
  @SerialName("title") val title: String
)
""")

    w(s / "remote" / "api" / f"{N}ApiService.kt", f"""
package {pkg}.remote.api

import {pkg}.remote.dto.{N}Response

interface {N}ApiService {{
  suspend fun fetch{N}Items(): List<{N}Response>
}}
""")

    w(s / "remote" / "api" / f"Default{N}ApiService.kt", f"""
package {pkg}.remote.api

import {pkg}.remote.dto.{N}Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Single

@Single(binds = [{N}ApiService::class])
internal class Default{N}ApiService(
  private val client: HttpClient
) : {N}ApiService {{

  override suspend fun fetch{N}Items(): List<{N}Response> =
    client.get("{n}s").body()
}}
""")

    w(s / "local" / "entity" / f"{N}Entity.kt", f"""
package {pkg}.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "{n}s")
data class {N}Entity(
  @PrimaryKey @ColumnInfo(name = "id") val id: String,
  @ColumnInfo(name = "title") val title: String
)
""")

    w(s / "local" / "dao" / f"{N}Dao.kt", f"""
package {pkg}.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import {pkg}.local.entity.{N}Entity
import kotlinx.coroutines.flow.Flow

@Dao
interface {N}Dao {{

  @Query("SELECT * FROM {n}s")
  fun getAll(): Flow<List<{N}Entity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(items: List<{N}Entity>)
}}
""")

    w(s / "local" / f"{N}Database.kt", f"""
package {pkg}.local

import androidx.room.Database
import androidx.room.RoomDatabase
import {pkg}.local.dao.{N}Dao
import {pkg}.local.entity.{N}Entity

@Database(
  entities = [{N}Entity::class],
  version = 1
)
abstract class {N}Database : RoomDatabase() {{
  abstract fun {n}Dao(): {N}Dao
}}
""")

    w(s / "mapper" / f"{N}ToDomain.kt", f"""
package {pkg}.mapper

import {pkg}.local.entity.{N}Entity
import {pkg}.remote.dto.{N}Response
import {domain_pkg}.model.{N}Item

internal fun {N}Response.toDomain(): {N}Item =
  {N}Item(
    id = id,
    title = title
  )

internal fun {N}Entity.toDomain(): {N}Item =
  {N}Item(
    id = id,
    title = title
  )

internal fun {N}Item.toEntity(): {N}Entity =
  {N}Entity(
    id = id,
    title = title
  )
""")

    w(s / "repository" / f"Default{N}Repository.kt", f"""
package {pkg}.repository

import {pkg}.mapper.toDomain
import {pkg}.remote.api.{N}ApiService
import {domain_pkg}.model.{N}Item
import {domain_pkg}.repository.{N}Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(binds = [{N}Repository::class])
internal class Default{N}Repository(
  private val apiService: {N}ApiService
) : {N}Repository {{

  override suspend fun fetch{N}Items(): List<{N}Item> =
    withContext(Dispatchers.IO) {{
      apiService.fetch{N}Items().map {{ it.toDomain() }}
    }}
}}
""")

    print(f"  ✓ :data:{n} — 9 files")


# ---------------------------------------------------------------------------
# SETTINGS UPDATE
# ---------------------------------------------------------------------------

def update_settings(name: str, layers: list, root: Path):
    settings = root / "settings.gradle.kts"
    if not settings.exists():
        print(f"\n  ⚠ settings.gradle.kts not found — add includes manually")
        return

    content = settings.read_text()
    n = to_lower(name)
    to_add = []
    if "domain" in layers:
        to_add.append(f'include(":domain:{n}")')
    if "data" in layers:
        to_add.append(f'include(":data:{n}")')
    if "feature" in layers:
        to_add.append(f'include(":feature:{n}")')

    added = [line for line in to_add if line not in content]
    if added:
        settings.write_text(content.rstrip() + "\n" + "\n".join(added) + "\n")
        for line in added:
            print(f"  ✓ settings.gradle.kts ← {line}")
    else:
        print(f"  ✓ settings.gradle.kts — already registered")


# ---------------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------------

def main():
    parser = argparse.ArgumentParser(description="Android Feature Scaffold Generator")
    parser.add_argument("--name", required=True, help="Feature name in PascalCase (e.g., Profile)")
    parser.add_argument(
        "--layers",
        default="all",
        help="Layers: all | feature | domain | data | comma-separated"
    )
    parser.add_argument(
        "--root",
        default=str(Path.cwd()),
        help="Project root directory (default: cwd)"
    )
    args = parser.parse_args()

    name = pascal(args.name)
    root = Path(args.root).resolve()

    if args.layers == "all":
        layers = ["domain", "data", "feature"]
    else:
        layers = [layer.strip() for layer in args.layers.split(",")]

    invalid = [layer for layer in layers if layer not in ("domain", "data", "feature")]
    if invalid:
        print(f"Error: unknown layers: {invalid}. Valid: all, domain, data, feature", file=sys.stderr)
        sys.exit(1)

    cfg = load_config(root)
    print(f"\nScaffolding '{name}' [{', '.join(layers)}] in {root}")
    print(f"  package: {cfg['base_package']}  theme: {cfg['theme_class']}  plugins: {cfg['plugin_namespace']}.*\n")

    if "domain" in layers:
        generate_domain(name, root, cfg)
    if "data" in layers:
        generate_data(name, root, cfg)
    if "feature" in layers:
        generate_feature(name, root, cfg)

    print()
    update_settings(name, layers, root)

    print(f"\n✅  Done! Next steps:")
    print(f"   1. Fill in TODOs (StateFactory, API endpoints, UI)")
    print(f"   2. Register Feature{name}Module in :app if needed")
    print(f"   3. ./gradlew assembleDebug")


if __name__ == "__main__":
    main()
