package com.example.flashcards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class StartPageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page)

        Log.i("LifeCycle", "StartPage onCreate")

        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("Success auth", false)) {
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, AuthPageActivity::class.java)
            startActivity(intent)
        }

    }
}