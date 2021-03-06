package com.example.flashcards.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.flashcards.MainActivity
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.model.Token
import com.example.flashcards.model.auth.AuthRequest
import com.example.flashcards.model.auth.AuthResponse
import com.example.flashcards.type.ErrorCodeType
import com.example.flashcards.ui.register.RegisterActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {
    private val webClient = WebClient().getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val login = findViewById<TextInputEditText>(R.id.login)
        val password = findViewById<TextInputEditText>(R.id.password)
        val button = findViewById<Button>(R.id.authorize_btn)
        val register = findViewById<TextView>(R.id.register)

        val tilLogin = findViewById<TextInputLayout>(R.id.til_login)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)

        val auth = getSharedPreferences("AUTH", Context.MODE_PRIVATE)
        val userIdSave = getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userTokenSave = getSharedPreferences("UserToken", Context.MODE_PRIVATE)

        login.addTextChangedListener {
            tilLogin?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }

        password.addTextChangedListener {
            tilPassword?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }

        button.setOnClickListener {

            if (login.text.toString().isEmpty()) {
                tilLogin?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.empty_field_error)
                }
            }

            if (password.text.toString().isEmpty()) {
                tilPassword?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.empty_field_error)
                }
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
                    if (response.body()?.status?.success == true) {

                        val userToken = response.body()?.token?.token.toString()
                        val userId = response.body()?.token?.userId.toString()

                        Log.i("test", "success " + response.body().toString())

                        userTokenSave.edit().putString("UserToken", userToken).apply()
                        userIdSave.edit().putString("UserId", userId).apply()
                        auth.edit().putBoolean("Success", true).apply()

                        startApp()
                    } else {
                        val message: String = when (response.body()?.status?.errorCode) {
                            ErrorCodeType.LOGIN_PASSWORD_INCORRECT -> response.body()?.status?.message.toString()
                            else -> "Error"
                        }
                        Toast.makeText(this@AuthActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }
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
