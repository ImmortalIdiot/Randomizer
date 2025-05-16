package com.immortalidiot.randomizer.ui.di

import com.immortalidiot.randomizer.core.ResourceProvider
import com.immortalidiot.randomizer.core.ResourceProviderImpl
import com.immortalidiot.randomizer.ui.dice.DiceScreenViewModel
import com.immortalidiot.randomizer.ui.history.HistoryScreenViewModel
import com.immortalidiot.randomizer.ui.list.ListScreenViewModel
import com.immortalidiot.randomizer.ui.range.RangeScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    single<ResourceProvider> { ResourceProviderImpl(androidApplication()) }

    viewModel { RangeScreenViewModel(get(), get()) }
    viewModel { ListScreenViewModel(get(), get()) }
    viewModelOf(::HistoryScreenViewModel)
    viewModelOf(::DiceScreenViewModel)
}
