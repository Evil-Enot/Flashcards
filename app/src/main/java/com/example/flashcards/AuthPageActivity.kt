package com.example.flashcards

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AuthPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.i("LifeCycle", "AuthPage onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_page)

        val login = findViewById<EditText>(R.id.login)
        val password = findViewById<EditText>(R.id.password)
        val button = findViewById<Button>(R.id.button)
        val register = findViewById<TextView>(R.id.register)

        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE)

        button.setOnClickListener {
            if (login.text.toString().isEmpty()) {
                login.hint = getString(R.string.hint)
                login.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isEmpty()) {
                password.hint = getString(R.string.hint)
                password.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isNotEmpty() && login.text.toString().isNotEmpty()) {
                if (password.text.toString() == "12345" && login.text.toString() == "admin") {
                    sharedPreferences.edit().putBoolean("Success auth", true).apply()
                    val intent = Intent(this, MainPageActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterPageActivity::class.java)
            startActivity(intent)
        }
    }

}