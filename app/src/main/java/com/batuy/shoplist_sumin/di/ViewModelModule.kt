package com.batuy.shoplist_sumin.di

import androidx.lifecycle.ViewModel
import com.batuy.shoplist_sumin.presentation.MainViewModel
import com.batuy.shoplist_sumin.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(viewModel: ShopItemViewModel): ViewModel
}