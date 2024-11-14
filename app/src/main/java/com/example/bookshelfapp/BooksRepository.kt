package com.example.bookshelfapp

class BooksRepository(private val apiService: BooksApiService) {
    suspend fun searchBooks(query: String): List<Book> {
        val response = apiService.searchBooks(query)
        return response.items.map {
            Book(
                id = it.id,
                title = it.volumeInfo.title,
                thumbnail = it.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")
            )
        }
    }
}
