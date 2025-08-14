package com.fahmigutawan.module

import com.fahmigutawan.env
import com.fahmigutawan.util.env

fun configureEnv(){
    env = env(
        db_host = System.getenv("DB_HOST"),
        db_port = System.getenv("DB_PORT"),
        db_username = System.getenv("DB_USERNAME"),
        db_password = System.getenv("DB_PASSWORD"),
        db_databasename = System.getenv("DB_DATABASENAME")
    )
}