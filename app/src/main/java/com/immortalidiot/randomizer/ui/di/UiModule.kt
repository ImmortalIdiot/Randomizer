package com.immortalidiot.randomizer.ui.di

import android.content.Context
import com.immortalidiot.randomizer.ui.list.ListScreenViewModel
import com.immortalidiot.randomizer.ui.range.RangeScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    single<Context> { androidApplication() }

    viewModel { RangeScreenViewModel(get()) }
    viewModel { ListScreenViewModel(get(), get()) }
}
