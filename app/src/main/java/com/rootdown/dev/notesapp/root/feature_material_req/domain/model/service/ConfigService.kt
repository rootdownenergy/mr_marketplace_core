package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service

interface ConfigService {
    suspend fun fetchConfig(): Boolean
    val isShowReqEditBtnConfig: Boolean
}