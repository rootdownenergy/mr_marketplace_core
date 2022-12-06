package com.rootdown.dev.notesapp.root.di

import com.google.firebase.firestore.FirebaseFirestore
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.StorageMaterialReqService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.StorageMaterialReqServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideStorageMaterialReqService(firestore: FirebaseFirestore,auth: AccountService): StorageMaterialReqService {
        return StorageMaterialReqServiceImpl(firestore,auth)
    }

}