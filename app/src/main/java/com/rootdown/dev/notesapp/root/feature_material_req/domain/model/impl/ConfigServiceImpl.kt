package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.rootdown.dev.notesapp.BuildConfig
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.ConfigService
import kotlinx.coroutines.tasks.await
import com.rootdown.dev.notesapp.R.xml as AppConfig

class ConfigServiceImpl: ConfigService {
    private val remoteConfig
        get() = Firebase.remoteConfig

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override suspend fun fetchConfig(): Boolean {
        return remoteConfig.fetchAndActivate().await()
    }

    override val isShowReqEditBtnConfig: Boolean
        get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()


    companion object {
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}