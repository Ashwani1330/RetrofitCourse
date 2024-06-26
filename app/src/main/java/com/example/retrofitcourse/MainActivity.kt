package com.example.retrofitcourse

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitcourse.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

       lifecycleScope.launchWhenCreated {
           binding.progressBar.isVisible = true

           val response = try {
               RetrofitInstance.api.getTodos()
           } catch (e: IOException) {
               Log.e(TAG, "IO Exception, you might not have internet connection.")
               binding.progressBar.isVisible = false
               return@launchWhenCreated
           } catch (e: HttpException) {
               Log.e(TAG, "HTTP Exception, unexpected response.")
               binding.progressBar.isVisible = false
               return@launchWhenCreated
           }
           if (response.isSuccessful && response.body() != null) {  // check
               todoAdapter.todos =  response.body()!!
           } else {
               Log.e(TAG, "Response not successful.")
           }

           binding.progressBar.isVisible = false

       }
    }

    private fun setupRecyclerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}