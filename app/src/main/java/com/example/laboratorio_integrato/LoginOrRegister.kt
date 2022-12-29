package com.example.laboratorio_integrato

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginOrRegister : AppCompatActivity() {

    lateinit var register: Button
    lateinit var login: Button
    lateinit var guest: Button
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        var ospite = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_register)
        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.loginButton)
        guest = findViewById(R.id.guest)
        auth = FirebaseAuth.getInstance()
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
                Toast.makeText(this,"If you want more consult, SUBSCRIBE!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun ospiteStatus(ospite: Boolean): Boolean {
        val sharedPref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("ospite", ospite)
    }
    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if(currentUser != null)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

