package com.rootdown.dev.notesapp.root.feature_note.domain.util

fun Int?.isGreaterThan(other: Int?) =
    this != null && other != null && this > other