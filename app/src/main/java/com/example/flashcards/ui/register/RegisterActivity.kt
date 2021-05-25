package com.example.flashcards.ui.register

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.MainActivity
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.model.*
import com.example.flashcards.type.DialogType
import com.example.flashcards.type.ErrorCodeType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private val webClient = WebClient().getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        val nameAndSurname = findViewById<EditText>(R.id.name_surname_field)
        val email = findViewById<EditText>(R.id.e_mail_field)
        val password = findViewById<EditText>(R.id.password_field)
        val confirmPassword = findViewById<EditText>(R.id.confirm_password_field)

        val button = findViewById<Button>(R.id.confirm_register)

        button.setOnClickListener {
            if (nameAndSurname.text.toString().isEmpty()) {
                nameAndSurname.hint = getString(R.string.empty_field_error)
                nameAndSurname.setHintTextColor(Color.RED)
            }

            if (email.text.toString().isEmpty()) {
                email.hint = getString(R.string.empty_field_error)
                email.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isEmpty()) {
                password.hint = getString(R.string.empty_field_error)
                password.setHintTextColor(Color.RED)
            }

            if (confirmPassword.text.toString().isEmpty()) {
                confirmPassword.hint = getString(R.string.empty_field_error)
                confirmPassword.setHintTextColor(Color.RED)
            }

            if (confirmPassword.text.toString() != password.text.toString()) {
                val toast =
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.password_missmatch),
                        Toast.LENGTH_SHORT
                    )
                toast.show()
            }

            if (nameAndSurname.text.toString().isNotEmpty()
                && email.text.toString().isNotEmpty()
                && password.text.toString().isNotEmpty()
                && confirmPassword.text.toString().isNotEmpty()
                && confirmPassword.text.toString() == password.text.toString()
                && checkFormat(nameAndSurname.text.toString(), DialogType.USERNAME)
                && checkFormat(email.text.toString(), DialogType.EMAIL)
            ) {

                val userReg = RegistrationRequest(
                    nameAndSurname.text.toString(),
                    email.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )

                val callReg = webClient.reg(userReg)

                callReg.enqueue(object : Callback<AuthApiResponse> {
                    override fun onResponse(
                        call: Call<AuthApiResponse>,
                        response: Response<AuthApiResponse>
                    ) {
                        val message: String
                        if (response.body()?.status?.success == true) {
                            auth(email.text.toString(), password.text.toString())
                        } else {
                            Log.i("error", response.body().toString())
                            message = when (response.body()?.status?.errorCode) {
                                ErrorCodeType.USER_EXISTS -> response.body()?.status?.message.toString()
                                else -> "Error"
                            }
                            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<AuthApiResponse>, t: Throwable) {

                    }
                })
            } else {
                if (nameAndSurname.text.toString().isNotEmpty()
                    && email.text.toString().isNotEmpty()
                    && password.text.toString().isNotEmpty()
                    && confirmPassword.text.toString().isNotEmpty()
                ) {
                    if (!checkFormat(nameAndSurname.text.toString(), DialogType.USERNAME)) {
                        val toast =
                            Toast.makeText(
                                applicationContext,
                                "Invalid username",
                                Toast.LENGTH_SHORT
                            )
                        toast.show()
                    }
                    if (!checkFormat(email.text.toString(), DialogType.EMAIL)) {
                        val toast =
                            Toast.makeText(
                                applicationContext,
                                "Invalid e-mail",
                                Toast.LENGTH_SHORT
                            )
                        toast.show()
                    }
                }
            }
        }
    }

    fun auth(login: String, password: String) {
        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE)

        val userAuth = AuthRequest(
            Token(null, ""),
            login,
            password
        )

        val callAuth = webClient.auth(userAuth)
        callAuth.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val userToken = response.body()?.token?.token.toString()
                val userId = response.body()?.token?.userId.toString()

                sharedPreferences.edit().putString("UserToken", userToken).apply()
                sharedPreferences.edit().putString("UserId", userId).apply()
                sharedPreferences.edit().putBoolean("Success", true).apply()
                startActivity()
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {

            }
        })
    }

    private fun startActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun checkFormat(edit: String, typeOfDialog: DialogType): Boolean {
        return when (typeOfDialog) {
            DialogType.USERNAME -> edit.matches("[a-zA-Z][a-zA-Z_0-9\\-]+".toRegex())
            DialogType.EMAIL -> edit.matches("[a-zA-Z_0-9\\-]+.[a-zA-Z][a-zA-Z_0-9\\-]+@[a-z]{2,7}\\.[a-zA-Z_0-9\\-]+".toRegex()) && edit.length < 255
            DialogType.PASSWORD -> edit.matches("[a-zA-Z0-9!@#$%&]+".toRegex())
        }
    }
}
