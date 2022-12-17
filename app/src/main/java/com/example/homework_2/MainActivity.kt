package com.example.homework_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : AppCompatActivity() {
    val state = MutableStateFlow("empty") // flow to update UI (in our case just print to logcat)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    private fun main() {
        runSync()
        runAsync()
        runBlocking {
          state.collect {}
        }
    }
    private fun runSync() {
        println("runSync method.")
        for (i in 1..1000) {
            runBlocking {
                doWork(i.toString())
            }
        }
    }

    private fun runAsync() {
        println("runAsync method.")

        for (i in 1..1000) {
            CoroutineScope(Dispatchers.IO).launch {
                doWork(i.toString())
            }
        }
    }


    private suspend fun doWork(name: String) {
        delay(500)
        state.update { "$name completed." }
        println(state.value)
    }

}