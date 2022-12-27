package com.example.laboratorio_integrato

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginOrRegister : AppCompatActivity() {

    lateinit var register: Button
    lateinit var login: Button
    lateinit var guest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        var ospite = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_register)
        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.loginButton)
        guest = findViewById(R.id.guest)
       // guest.isEnabled = true

        register.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent);

        }


        login.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }

        guest.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            ospite = true
            editor.putBoolean("ospite",ospite)
            if(ospiteStatus(ospite)){
                guest.isEnabled = false
            }
        }
    }

    private fun ospiteStatus(ospite: Boolean): Boolean {
        val sharedPref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("ospite", ospite)
    }
}
