package com.example.laboratorio_integrato

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginOrRegister : AppCompatActivity() {

    companion object{
        lateinit var closeActivity: Activity
    }

    lateinit var register: Button
    lateinit var login: Button
    lateinit var guest: Button
    lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_register)
        closeActivity = this
        val sharedPref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        var ospite = sharedPref.getBoolean("dio",false)
        register = findViewById(R.id.registerButton)
        login = findViewById(R.id.loginButton)
        guest = findViewById(R.id.guest)
        auth = FirebaseAuth.getInstance()

        if(ospite){
            guest.isEnabled = false
        }
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
            val intent = Intent(this, SibillaLogged::class.java)
            startActivity(intent)

            if(!ospite){
                editor.putBoolean("dio",true)
                editor.apply()
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

