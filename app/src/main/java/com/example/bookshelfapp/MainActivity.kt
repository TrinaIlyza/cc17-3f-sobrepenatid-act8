package com.example.bookshelfapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel with custom factory
        val factory = BooksViewModelFactory(BooksRepository(RetrofitInstance.api))
        viewModel = ViewModelProvider(this, factory)[BooksViewModel::class.java]

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe LiveData from ViewModel
        viewModel.books.observe(this) { books ->
            val adapter = BookAdapter(books)
            recyclerView.adapter = adapter
        }

        // Trigger a search
        viewModel.searchBooks("jazz history")
    }
}
