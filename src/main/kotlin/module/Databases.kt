package com.fahmigutawan.module

import com.fahmigutawan.env
import org.jetbrains.exposed.sql.Database

fun configureDatabase(){
    Database.connect(
        url = env.dbUrl,
        driver = "com.mysql.cj.jdbc.Driver",
    )
}