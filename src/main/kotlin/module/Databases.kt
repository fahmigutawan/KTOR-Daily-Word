package com.fahmigutawan.module

import com.fahmigutawan.env
import org.jetbrains.exposed.sql.Database

fun configureDatabase(){
    Database.connect(
        url = "jdbc:mysql://${env.db_host}:${env.db_port}/${env.db_databasename}",
        user = env.db_username,
        driver = "com.mysql.cj.jdbc.Driver",
        password = env.db_password,
    )
}