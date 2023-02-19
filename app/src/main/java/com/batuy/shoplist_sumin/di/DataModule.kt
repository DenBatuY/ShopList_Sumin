package com.batuy.shoplist_sumin.di

import android.app.Application
import com.batuy.shoplist_sumin.data.AppDataBase
import com.batuy.shoplist_sumin.data.ShopListDao
import com.batuy.shoplist_sumin.data.ShopListRepositoryImpl
import com.batuy.shoplist_sumin.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDataBase.getInstance(application).shopListDao()
        }
    }
}