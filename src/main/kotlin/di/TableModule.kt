package com.fahmigutawan.di

import com.fahmigutawan.data.table.UsedWordTable
import org.koin.dsl.module

fun tableModule() = module {
    single { UsedWordTable }
}