package com.rootdown.dev.notesapp.root.feature_note.domain.repository

import com.rootdown.dev.notesapp.root.feature_note.data.local.CloudGroupDao
import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CloudGroupRepoImpl @Inject constructor(
    private val dao: CloudGroupDao
): CloudGroupRepo {
    override fun getCloudGroup(): Flow<List<CloudGroup>> {
        return dao.getCloudGroup()
    }

    override suspend fun getCloudGroupById(id: Int): CloudGroup? {
        return dao.getCloudGroupById(id)
    }

    override suspend fun insertCloudGroup(group: CloudGroup) {
        dao.insertCloudGroup(group)
    }

    override suspend fun deleteCloudGroup(group: CloudGroup) {
        dao.deleteCloudGroup(group)
    }
}
