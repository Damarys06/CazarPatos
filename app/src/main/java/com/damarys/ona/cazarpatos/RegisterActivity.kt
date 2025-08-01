package com.damarys.ona.cazarpatos

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var backToLoginButton: Button

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword)
        registerButton = findViewById(R.id.buttonRegister)
        backToLoginButton = findViewById(R.id.buttonBackToLogin)

        registerButton.setOnClickListener {
            if (validateFields()) {
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                registerUser(email, password)
            }
        }

        backToLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        val email = emailEditText.text.toString().trim()
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Por favor, ingrese un correo electrónico válido"
            isValid = false
        } else {
            emailEditText.error = null
        }

        val password = passwordEditText.text.toString().trim()
        if (password.length < 8) {
            passwordEditText.error = "La contraseña debe tener al menos 8 caracteres"
            isValid = false
        } else {
            passwordEditText.error = null
        }

        val confirmPassword = confirmPasswordEditText.text.toString().trim()
        if (confirmPassword != password) {
            confirmPasswordEditText.error = "Las contraseñas no coinciden"
            isValid = false
        } else {
            confirmPasswordEditText.error = null
        }

        return isValid
    }

    // Firebase Authentication
    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = task.exception?.message ?: "Error desconocido"
                    Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }
    }
}