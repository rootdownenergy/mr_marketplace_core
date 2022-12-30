package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl

import com.google.firebase.ktx.Firebase
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.LogService
import com.google.firebase.crashlytics.ktx.crashlytics
import javax.inject.Inject

class LogServiceImpl @Inject constructor() : LogService {
    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)
}