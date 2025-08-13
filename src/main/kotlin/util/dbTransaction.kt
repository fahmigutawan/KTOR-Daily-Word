package util

import com.fahmigutawan.logger
import io.ktor.server.routing.RoutingContext
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

fun <T> dbTransaction(
    onError: (e: Exception) -> T,
    transactions: Transaction.() -> T
): T {
    return transaction {
        try {
            transactions()
        } catch (e: Exception) {
            rollback()
            logger.error(e.message.toString())
            onError(e)
        }
    }
}