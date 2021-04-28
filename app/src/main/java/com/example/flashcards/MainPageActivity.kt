package com.example.flashcards

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("LifeCycle", "MainPage onCreate")
    }
}