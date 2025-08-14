package com.fahmigutawan.data

import com.fahmigutawan.env
import com.fahmigutawan.logger
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

class Dictionary {
    var words: List<String> = emptyList()
    var data: Map<String, List<String>> = emptyMap()

    init {
        val file = File(env.dict_path)
        val csv = csvReader().readAll(file)
        initDataClasses(csv)
    }

    private fun initDataClasses(items: List<List<String>>) {
        val _data = items.map { list ->
            (list.getOrNull(0) ?: "") to (list.getOrNull(1) ?: "")
        }.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        )

        words = _data.keys.toList()
        data = _data
    }
}