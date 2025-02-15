package com.immortalidiot.randomizer.ui.di

import com.immortalidiot.randomizer.ui.range.RangeScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::RangeScreenViewModel)
}
