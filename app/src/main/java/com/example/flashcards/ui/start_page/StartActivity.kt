package com.example.flashcards.ui.start_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.MainActivity
import com.example.flashcards.R
import com.example.flashcards.ui.auth.AuthActivity

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page)

        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("Success", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }
}
