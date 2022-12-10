package com.example.laboratorio_integrato

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var passwordControl: EditText
    lateinit var button: Button
    lateinit var backButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        passwordControl = findViewById(R.id.passwordControl)
        button = findViewById(R.id.button)

        val actionbar = supportActionBar

        actionbar!!.title = "Back"

        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}