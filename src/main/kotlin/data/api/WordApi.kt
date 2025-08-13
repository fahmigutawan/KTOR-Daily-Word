package com.fahmigutawan.data.api

import com.fahmigutawan.data.Dictionary
import com.fahmigutawan.data.table.UsedWordTable
import com.fahmigutawan.model.response_out.word.DailyWordResponse
import com.milli.model.response_out.ApiResponseOut
import com.milli.model.response_out.MetadataOut
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.dbTransaction
import kotlin.random.Random

object WordApi : KoinComponent {
    val usedWordTable by inject<UsedWordTable>()
    val dictionary by inject<Dictionary>()

    private fun inputNewWord(word: String) {
        val todayDateTime = Clock.System.now()
            .toLocalDateTime(TimeZone.UTC)
            .let { today ->
                LocalDateTime(
                    year = today.year,
                    monthNumber = today.monthNumber,
                    dayOfMonth = today.dayOfMonth,
                    hour = 23,
                    minute = 59,
                    second = 59
                ).toInstant(TimeZone.UTC)
            }

        usedWordTable.insert {
            it[usedWordTable.word] = word
            it[usedWordTable.date] = todayDateTime
        }
    }

    private fun getUniqueWord(): String {
        val wordsCount = dictionary.words.size
        val randomIndex = Random.nextInt(wordsCount)
        return dictionary.words.getOrNull(randomIndex) ?: ""
    }

    private fun isExpired(lastWordDate: Instant?): Boolean {
        if (lastWordDate == null) {
            return true
        }

        val nowDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val _lastWordDate = lastWordDate.toLocalDateTime(TimeZone.UTC)

        return nowDate > _lastWordDate
    }

    fun Route.wordRouting() {
        get("/word") {
            val res = dbTransaction(
                onError = { e ->
                    ApiResponseOut(
                        metadata = MetadataOut(
                            status = HttpStatusCode.BadRequest.value,
                            message = "failed"
                        ),
                        data = null
                    )
                }
            ) {
                val lastItem = usedWordTable
                    .selectAll()
                    .orderBy(usedWordTable.date, SortOrder.DESC) // ambil yang terbaru
                    .limit(1)
                    .firstOrNull()
                val lastItemDate = lastItem?.getOrNull(usedWordTable.date)

                if (isExpired(lastItemDate)) {
                    var word = getUniqueWord()
                    while (true) {
                        val sameWordCount = usedWordTable
                            .selectAll()
                            .where { usedWordTable.word.eq(word) }
                            .count()

                        if (sameWordCount > 0) {
                            word = getUniqueWord()
                        } else break
                    }

                    inputNewWord(word)

                    val definitions = dictionary.data[word]

                    ApiResponseOut(
                        metadata = MetadataOut(
                            status = HttpStatusCode.OK.value,
                            message = "success"
                        ),
                        data = DailyWordResponse(
                            word = word,
                            definitions = definitions ?: emptyList()
                        )
                    )
                } else {
                    val word = lastItem?.getOrNull(usedWordTable.word) ?: ""
                    val definitions = dictionary.data[word]

                    ApiResponseOut(
                        metadata = MetadataOut(
                            status = HttpStatusCode.OK.value,
                            message = "success"
                        ),
                        data = DailyWordResponse(
                            word = word,
                            definitions = definitions ?: emptyList()
                        )
                    )
                }
            }

            call.respond(
                status = HttpStatusCode.fromValue(res.metadata.status),
                message = res
            )
        }
    }
}