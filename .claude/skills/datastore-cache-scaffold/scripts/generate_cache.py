#!/usr/bin/env python3
"""DataStore Proto Cache Scaffold Generator.

Usage:
    python3 generate_cache.py --name Briefing --data-module weather --fields "city_name:string,date:string,briefing_text:string"
    python3 generate_cache.py --name UserPrefs --data-module user --fields "user_id:string,theme:string"
    python3 generate_cache.py --name AppConfig --data-module config --fields "theme_mode:string,notifications_enabled:bool,sync_interval:int32"

Options:
    --name          PascalCase cache name (e.g. Briefing, WeatherForecast)
    --data-module   Data layer module name (e.g. weather, user, config)
    --fields        Comma-separated proto fields: field_name:type (types: string, bool, int32, int64, float, double)
    --domain-module Domain layer module name (default: same as --data-module)
    --root          Project root (default: cwd)
"""

import argparse
import re
import sys
from pathlib import Path


# ---------------------------------------------------------------------------
# HELPERS
# ---------------------------------------------------------------------------

PROTO_TO_KOTLIN = {
    "string": "String",
    "bool": "Boolean",
    "int32": "Int",
    "int64": "Long",
    "float": "Float",
    "double": "Double",
}

PROTO_DEFAULTS = {
    "string": '""',
    "bool": "false",
    "int32": "0",
    "int64": "0L",
    "float": "0f",
    "double": "0.0",
}

VALID_PROTO_TYPES = set(PROTO_TO_KOTLIN.keys())


def snake_to_camel(name: str) -> str:
    parts = name.split("_")
    return parts[0] + "".join(p.capitalize() for p in parts[1:])


def snake_to_pascal(name: str) -> str:
    return "".join(p.capitalize() for p in name.split("_"))


def pascal(name: str) -> str:
    return name[0].upper() + name[1:] if name else name


def to_lower(name: str) -> str:
    # PascalCase → snake_case for file names
    s = re.sub(r"(?<=[a-z0-9])(?=[A-Z])", "_", name)
    return s.lower()


def write(path: Path, content: str, root: Path):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content.lstrip() + "\n")
    print(f"  ✓  {path.relative_to(root)}")


def parse_fields(fields_str: str) -> list[tuple[str, str]]:
    """Parse 'city_name:string,date:string' → [('city_name', 'string'), ...]"""
    result = []
    for part in fields_str.split(","):
        part = part.strip()
        if ":" not in part:
            print(f"Error: field '{part}' must be in 'name:type' format", file=sys.stderr)
            sys.exit(1)
        field_name, field_type = part.split(":", 1)
        field_name = field_name.strip()
        field_type = field_type.strip()
        if field_type not in VALID_PROTO_TYPES:
            print(
                f"Error: unknown type '{field_type}'. Valid: {', '.join(sorted(VALID_PROTO_TYPES))}",
                file=sys.stderr,
            )
            sys.exit(1)
        result.append((field_name, field_type))
    return result


# ---------------------------------------------------------------------------
# GENERATORS
# ---------------------------------------------------------------------------

def gen_proto(name: str, data_module: str, fields: list, base_pkg: str, root: Path):
    file_name = to_lower(name) + "_cache.proto"
    path = root / "data" / data_module / "src" / "main" / "proto" / file_name

    field_lines = "\n".join(
        f"    {ftype} {fname} = {i + 1};"
        for i, (fname, ftype) in enumerate(fields)
    )

    content = f"""
syntax = "proto3";

option java_package = "{base_pkg}.data.{data_module}.persistence";
option java_multiple_files = true;

message {name}CacheData {{
{field_lines}
}}
"""
    write(path, content, root)


def gen_serializer(name: str, data_module: str, base_pkg: str, root: Path):
    pkg = f"{base_pkg}.data.{data_module}.persistence"
    path = root / "data" / data_module / "src" / "main" / "kotlin" / \
           Path(*pkg.split(".")) / f"{name}CacheSerializer.kt"

    content = f"""
package {pkg}

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object {name}CacheSerializer : Serializer<{name}CacheData> {{

  override val defaultValue: {name}CacheData = {name}CacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): {name}CacheData =
    try {{
      {name}CacheData.parseFrom(input)
    }} catch (exception: InvalidProtocolBufferException) {{
      throw CorruptionException("Cannot read proto.", exception)
    }}

  override suspend fun writeTo(t: {name}CacheData, output: OutputStream) =
    t.writeTo(output)
}}
"""
    write(path, content, root)


def gen_qualifier(name: str, data_module: str, base_pkg: str, root: Path):
    pkg = f"{base_pkg}.data.{data_module}.persistence"
    path = root / "data" / data_module / "src" / "main" / "kotlin" / \
           Path(*pkg.split(".")) / f"{name}DataStoreQualifier.kt"

    content = f"""
package {pkg}

import org.koin.core.annotation.Qualifier

@Qualifier
annotation class {name}DataStoreQualifier
"""
    write(path, content, root)


def gen_prefs(name: str, data_module: str, base_pkg: str, root: Path):
    pkg = f"{base_pkg}.data.{data_module}.persistence"
    path = root / "data" / data_module / "src" / "main" / "kotlin" / \
           Path(*pkg.split(".")) / f"{name}DataStorePrefs.kt"

    prop_name = name[0].lower() + name[1:] + "DataStore"
    file_const = to_lower(name) + "_cache_prefs"

    content = f"""
package {pkg}

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore

private val Context.{prop_name}: DataStore<{name}CacheData> by dataStore(
  fileName = {name}DataStorePrefs.FILE_NAME,
  serializer = {name}CacheSerializer,
  corruptionHandler = ReplaceFileCorruptionHandler {{
    {name}CacheData.getDefaultInstance()
  }}
)

internal class {name}DataStorePrefs {{

  fun get(context: Context): DataStore<{name}CacheData> = context.{prop_name}

  companion object {{
    const val FILE_NAME = "{file_const}"
  }}
}}
"""
    write(path, content, root)


def gen_impl(name: str, data_module: str, domain_module: str, fields: list, base_pkg: str, root: Path):
    pkg = f"{base_pkg}.data.{data_module}.persistence"
    domain_cache_pkg = f"{base_pkg}.domain.{domain_module}.cache"
    path = root / "data" / data_module / "src" / "main" / "kotlin" / \
           Path(*pkg.split(".")) / f"Default{name}Cache.kt"

    # Convention: last field = value returned by get(), all others = lookup keys.
    # Single field: get() takes no key params and returns that field.
    key_fields = fields[:-1]  # may be empty
    value_field_name, value_field_type = fields[-1]

    all_params = ", ".join(
        f"{snake_to_camel(fname)}: {PROTO_TO_KOTLIN[ftype]}"
        for fname, ftype in fields
    )
    key_params = ", ".join(
        f"{snake_to_camel(fname)}: {PROTO_TO_KOTLIN[ftype]}"
        for fname, ftype in key_fields
    )
    builder_calls = "\n".join(
        f"        .set{snake_to_pascal(fname)}({snake_to_camel(fname)})"
        for fname, _ in fields
    )
    return_kt_type = PROTO_TO_KOTLIN[value_field_type]
    value_prop = f"data.{snake_to_camel(value_field_name)}"

    # Build key-matching condition for get()
    if key_fields:
        key_checks = " &&\n        ".join(
            f"data.{snake_to_camel(fn)} == {snake_to_camel(fn)}"
            for fn, _ in key_fields
        )
        if value_field_type == "string":
            not_blank = f" &&\n        it.isNotBlank()"
        else:
            not_blank = ""
        take_if_body = f"{key_checks}{not_blank}"
        get_body = f"return {value_prop}.takeIf {{\n      {take_if_body}\n    }}"
    else:
        if value_field_type == "string":
            get_body = f"return {value_prop}.takeIf {{ it.isNotBlank() }}"
        else:
            get_body = f"return {value_prop}"

    content = f"""
package {pkg}

import androidx.datastore.core.DataStore
import {domain_cache_pkg}.{name}Cache
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single(binds = [{name}Cache::class])
internal class Default{name}Cache(
  @param:{name}DataStoreQualifier
  private val dataStore: DataStore<{name}CacheData>
) : {name}Cache {{

  override suspend fun get({key_params}): {return_kt_type}? {{
    val data = dataStore.data.first()
    {get_body}
  }}

  override suspend fun save({all_params}) {{
    dataStore.updateData {{
      it.toBuilder()
{builder_calls}
        .build()
    }}
  }}
}}
"""
    write(path, content, root)


def gen_domain_interface(name: str, domain_module: str, fields: list, base_pkg: str, root: Path):
    pkg = f"{base_pkg}.domain.{domain_module}.cache"
    path = root / "domain" / domain_module / "src" / "main" / "kotlin" / \
           Path(*pkg.split(".")) / f"{name}Cache.kt"

    # Mirror impl convention: last field = value, others = keys
    key_fields = fields[:-1]
    value_field_name, value_field_type = fields[-1]

    key_params = ", ".join(
        f"{snake_to_camel(fname)}: {PROTO_TO_KOTLIN[ftype]}"
        for fname, ftype in key_fields
    )
    all_params = ", ".join(
        f"{snake_to_camel(fname)}: {PROTO_TO_KOTLIN[ftype]}"
        for fname, ftype in fields
    )
    return_type = f"{PROTO_TO_KOTLIN[value_field_type]}?"

    content = f"""
package {pkg}

interface {name}Cache {{
  suspend fun get({key_params}): {return_type}
  suspend fun save({all_params})
}}
"""
    write(path, content, root)


# ---------------------------------------------------------------------------
# DI SNIPPET
# ---------------------------------------------------------------------------

def print_di_snippet(name: str, data_module: str, base_pkg: str):
    pkg = f"{base_pkg}.data.{data_module}.persistence"
    prop_name = name[0].lower() + name[1:] + "DataStore"

    print(f"""
  ┌─ Add to Data{pascal(data_module)}Module (data/{data_module}/di/Data{pascal(data_module)}Module.kt):
  │
  │  @Single
  │  @{name}DataStoreQualifier
  │  fun provide{name}DataStore(context: Context): DataStore<{name}CacheData> =
  │    {name}DataStorePrefs().get(context)
  └─""")


# ---------------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------------

def main():
    parser = argparse.ArgumentParser(description="DataStore Proto Cache Scaffold Generator")
    parser.add_argument("--name", required=True, help="Cache name in PascalCase (e.g., Briefing)")
    parser.add_argument("--data-module", required=True, help="Data layer module (e.g., weather)")
    parser.add_argument(
        "--fields", required=True,
        help="Proto fields: 'field_name:type,...' (types: string, bool, int32, int64, float, double)"
    )
    parser.add_argument("--domain-module", default=None, help="Domain module (default: same as --data-module)")
    parser.add_argument("--root", default=None, help="Project root (default: cwd)")
    args = parser.parse_args()

    name = pascal(args.name)
    data_module = args.data_module
    domain_module = args.domain_module or data_module
    root = Path(args.root).resolve() if args.root else Path.cwd()
    base_pkg = "com.weather.vibe"

    fields = parse_fields(args.fields)
    if not fields:
        print("Error: --fields must not be empty", file=sys.stderr)
        sys.exit(1)

    # Check return type for get() — strings use isNotBlank(), others need different check
    first_type = fields[0][1]
    if first_type not in ("string",):
        print(
            f"  ⚠  get() returns {PROTO_TO_KOTLIN[first_type]}? — update isNotBlank() check in Default{name}Cache.kt",
        )

    print(f"\nScaffolding '{name}Cache' in data/{data_module}  (domain/{domain_module})\n")

    gen_proto(name, data_module, fields, base_pkg, root)
    gen_qualifier(name, data_module, base_pkg, root)
    gen_serializer(name, data_module, base_pkg, root)
    gen_prefs(name, data_module, base_pkg, root)
    gen_domain_interface(name, domain_module, fields, base_pkg, root)
    gen_impl(name, data_module, domain_module, fields, base_pkg, root)

    print(f"\n✅  6 files generated")
    print_di_snippet(name, data_module, base_pkg)
    print("  Next: implement get() validity check in Default{name}Cache, then ./gradlew :data:{data_module}:build\n".format(name=name, data_module=data_module))


if __name__ == "__main__":
    main()
