package com.weather.vibe.feature.viberating.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class NoteEditorPreview : PreviewParameterProvider<NoteEditorPreviewParams> {

  private val collapsed: NoteEditorPreviewParams =
    NoteEditorPreviewParams(
      expanded = false,
      enabled = true,
      note = ""
    )

  private val expandedEmpty: NoteEditorPreviewParams =
    NoteEditorPreviewParams(
      expanded = true,
      enabled = true,
      note = ""
    )

  private val expandedWithNote: NoteEditorPreviewParams =
    NoteEditorPreviewParams(
      expanded = true,
      enabled = true,
      note = "Świetna kawa, słońce w oczach"
    )

  private val disabled: NoteEditorPreviewParams =
    NoteEditorPreviewParams(
      expanded = true,
      enabled = false,
      note = "Zapisuję ten dzień"
    )

  override val values: Sequence<NoteEditorPreviewParams> =
    sequenceOf(collapsed, expandedEmpty, expandedWithNote, disabled)
}
