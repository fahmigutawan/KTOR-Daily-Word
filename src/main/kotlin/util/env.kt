package com.fahmigutawan.util

data class env(
    val db_host: String = "",
    val db_port:String = "",
    val db_username:String = "",
    val db_password:String = "",
    val db_databasename: String = "",
    val dict_path:String = "dict.csv"
)
