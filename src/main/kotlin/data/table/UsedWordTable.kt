package com.fahmigutawan.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object UsedWordTable : Table("used_word") {
    val id = integer("id").autoIncrement()
    val word = varchar("word", 256).nullable()
    val date = timestamp("date").nullable()
}
