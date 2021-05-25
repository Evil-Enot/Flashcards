package com.example.flashcards.ui.auth

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.MainActivity
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.model.AuthRequest
import com.example.flashcards.model.AuthResponse
import com.example.flashcards.model.Token
import com.example.flashcards.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {
    private val webClient = WebClient().getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_page)

        val login = findViewById<EditText>(R.id.login)
        val password = findViewById<EditText>(R.id.password)
        val button = findViewById<Button>(R.id.button)
        val register = findViewById<TextView>(R.id.register)

        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE)

        button.setOnClickListener {

            if (login.text.toString().isEmpty()) {
                login.hint = getString(R.string.empty_field_error)
                login.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isEmpty()) {
                password.hint = getString(R.string.empty_field_error)
                password.setHintTextColor(Color.RED)
            }

            val userAuth = AuthRequest(
                Token(null, ""),
                login.text.toString(),
                password.text.toString()
            )

            val callAuth = webClient.auth(userAuth)
            callAuth.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {

                    val userToken = response.body()?.token?.token.toString()
                    val userId = response.body()?.token?.userId.toString()

                    sharedPreferences.edit().putString("UserToken", userToken).apply()
                    sharedPreferences.edit().putString("UserId", userId).apply()
                    sharedPreferences.edit().putBoolean("Success", true).apply()

                    startApp()
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {

                }
            })
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
