package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}