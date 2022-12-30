package com.rootdown.dev.notesapp.root.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl.AccountServiceImpl
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl.ConfigServiceImpl
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl.LogServiceImpl
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.StorageMaterialReqService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl.StorageMaterialReqServiceImpl
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.ConfigService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.LogService
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
    fun provideStorageMaterialReqService(
        firestore: FirebaseFirestore,
        auth: AccountService
    ): StorageMaterialReqService {
        return StorageMaterialReqServiceImpl(firestore, auth)
    }

    @Provides
    @Singleton
    fun provideAccountService(
        auth: FirebaseAuth
    ): AccountService {
        return AccountServiceImpl(auth)
    }

    @Provides
    @Singleton
    fun provideConfigService(): ConfigService {
        return ConfigServiceImpl()
    }
    @Provides
    @Singleton
    fun provideLogService(): LogService {
        return LogServiceImpl()
    }
}
