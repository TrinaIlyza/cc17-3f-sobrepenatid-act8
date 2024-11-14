package com.example.bookshelfapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class BooksViewModel(private val repository: BooksRepository) : ViewModel() {
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    fun searchBooks(query: String) {
        viewModelScope.launch {
            val bookList = repository.searchBooks(query)
            _books.postValue(bookList)
        }
    }
}
