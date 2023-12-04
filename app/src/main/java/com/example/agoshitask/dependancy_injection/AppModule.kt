package com.example.agoshitask.dependancy_injection

import android.content.Context
import com.example.agoshitask.common.Constants
import com.example.agoshitask.data.local.BeerDao
import com.example.agoshitask.data.local.BeerDatabase
import com.example.agoshitask.data.remote.BeerApi
import com.example.agoshitask.data.repository.BeerRepositoryImplementation
import com.example.agoshitask.domain.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerApi(): BeerApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): BeerDatabase {
        return BeerDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideBeerDao(beerDatabase: BeerDatabase): BeerDao {
        return beerDatabase.dao
    }

    @Provides
    @Singleton
    fun provideBeerRepository(api: BeerApi, beerDao: BeerDao): BeerRepository {
        return BeerRepositoryImplementation(api, beerDao)
    }
}