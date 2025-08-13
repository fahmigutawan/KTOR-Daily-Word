package com.fahmigutawan.di

import com.fahmigutawan.data.Dictionary
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

fun mainModule() = module {
    single {
        Dictionary()
    }
}