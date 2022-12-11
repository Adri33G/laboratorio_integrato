package com.example.laboratorio_integrato

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginOrRegister : AppCompatActivity() {

    lateinit var register: Button
    lateinit var login: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_register)

        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.loginButton)

        register.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent);

        }


        login.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);

        }


    }
}
