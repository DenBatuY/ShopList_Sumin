package com.batuy.shoplist_sumin.di

import android.app.Activity
import android.app.Application
import com.batuy.shoplist_sumin.presentation.MainActivity
import com.batuy.shoplist_sumin.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment:ShopItemFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}