package com.example.bookshelfapp

import kotlinx.coroutines.delay
import retrofit2.HttpException

class BooksRepository(private val apiService: BooksApiService) {
    suspend fun searchBooks(query: String): List<Book> {
        var attempts = 0
        while (true) {
            try {
                val response = apiService.searchBooks(query)
                return response.items.map {
                    Book(
                        id = it.id,
                        title = it.volumeInfo.title,
                        thumbnail = it.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")
                    )
                }
            } catch (e: HttpException) {
                if (e.code() == 429 && attempts < MAX_RETRIES) {
                    attempts++
                    delay(RETRY_DELAY_MS * attempts) // Exponential backoff
                } else {
                    throw e
                }
            }
        }
    }

    companion object {
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY_MS = 1000L // Initial delay of 1 second
    }
}
