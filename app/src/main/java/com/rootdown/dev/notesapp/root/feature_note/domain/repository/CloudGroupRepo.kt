package com.rootdown.dev.notesapp.root.feature_note.domain.repository

import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import kotlinx.coroutines.flow.Flow

interface CloudGroupRepo {

    // construct coroutine flow
    fun getCloudGroup(): Flow<List<CloudGroup>>
    // standard crud get
    suspend fun getCloudGroupById(id: Int): CloudGroup?
    // standard crud insert
    suspend fun insertCloudGroup(group: CloudGroup)
    // standard crud delete
    suspend fun deleteCloudGroup(group: CloudGroup)
}