package com.fahmigutawan.module

import com.fahmigutawan.env
import com.fahmigutawan.util.env

fun configureEnv(){
    env = env(
        dbUrl = System.getenv("DB_URL")
    )
}