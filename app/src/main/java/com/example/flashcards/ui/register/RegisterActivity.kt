package com.example.flashcards.ui.register

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.MainActivity
import com.example.flashcards.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        val nameAndSurname = findViewById<EditText>(R.id.name_surname_field)
        val email = findViewById<EditText>(R.id.e_mail_field)
        val password = findViewById<EditText>(R.id.password_field)
        val confirmPassword = findViewById<EditText>(R.id.confirm_password_field)

        val button = findViewById<Button>(R.id.confirm_register)

        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE)

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
            ) {
                sharedPreferences.edit().putBoolean("Success", true).apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
