package com.example.dreamlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var button: Button

    private val dreamViewModel : DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    } // call the one and only repository we created in the Task Application class
    // so not creating multiple instances of the repository in our app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        button = findViewById(R.id.button_add)

        // if want to make item clickable, pass in the context (homework 2)
        val adapter = DreamListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        dreamViewModel.allDreams.observe(this, Observer {
            // it - the thing trying to observer
            // update the cashed copy of tasks in the adapter to if
            tasks -> tasks?.let{
                adapter.submitList(it)
            }
        })

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }
    }
}