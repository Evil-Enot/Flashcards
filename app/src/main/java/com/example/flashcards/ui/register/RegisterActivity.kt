package com.example.flashcards.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.flashcards.MainActivity
import com.example.flashcards.R
import com.example.flashcards.api.WebClient
import com.example.flashcards.model.*
import com.example.flashcards.model.auth.AuthApiResponse
import com.example.flashcards.model.auth.AuthRequest
import com.example.flashcards.model.auth.AuthResponse
import com.example.flashcards.model.registration.RegistrationRequest
import com.example.flashcards.type.DialogType
import com.example.flashcards.type.ErrorCodeType
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private val webClient = WebClient().getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = findViewById<TextInputEditText>(R.id.name_field)
        val email = findViewById<TextInputEditText>(R.id.e_mail_field)
        val password = findViewById<TextInputEditText>(R.id.password_field)
        val confirmPassword = findViewById<TextInputEditText>(R.id.confirm_password_field)

        val tilName = findViewById<TextInputLayout>(R.id.til_name_field)
        val tilEmail = findViewById<TextInputLayout>(R.id.til_e_mail_field)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password_field)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.til_confirm_password_field)

        val button = findViewById<Button>(R.id.confirm_register)

        name.addTextChangedListener {
            tilName?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }

        email.addTextChangedListener {
            tilEmail?.let {
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

        confirmPassword.addTextChangedListener {
            tilConfirmPassword?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }

        button.setOnClickListener {
            if (name.text.toString().isEmpty()) {
                tilName?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.empty_field_error)
                }
            }

            if (email.text.toString().isEmpty()) {
                tilEmail?.let {
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

            if (confirmPassword.text.toString().isEmpty()) {
                tilConfirmPassword?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.empty_field_error)
                }
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

            if (name.text.toString().isNotEmpty()
                && email.text.toString().isNotEmpty()
                && password.text.toString().isNotEmpty()
                && confirmPassword.text.toString().isNotEmpty()
                && confirmPassword.text.toString() == password.text.toString()
                && checkFormat(name.text.toString(), DialogType.USERNAME)
                && checkFormat(email.text.toString(), DialogType.EMAIL)
            ) {

                val userReg = RegistrationRequest(
                    name.text.toString(),
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
                if (name.text.toString().isNotEmpty()
                    && email.text.toString().isNotEmpty()
                    && password.text.toString().isNotEmpty()
                    && confirmPassword.text.toString().isNotEmpty()
                ) {
                    if (!checkFormat(name.text.toString(), DialogType.USERNAME)) {
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
        val auth = getSharedPreferences("AUTH", Context.MODE_PRIVATE)
        val userIdSave = getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userTokenSave = getSharedPreferences("UserToken", Context.MODE_PRIVATE)

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

                userTokenSave.edit().putString("UserToken", userToken).apply()
                userIdSave.edit().putString("UserId", userId).apply()
                auth.edit().putBoolean("Success", true).apply()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
