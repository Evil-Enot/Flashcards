package com.example.flashcards.ui.start_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.MainActivity
import com.example.flashcards.R
import com.example.flashcards.ui.auth.AuthActivity

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val auth = getSharedPreferences("AUTH", Context.MODE_PRIVATE)
//        auth.edit().clear().apply()

        if (auth.getBoolean("Success", false)) {
            Log.i("Testing", auth.getString("UserId", "").toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }
}
