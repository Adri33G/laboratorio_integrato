package com.example.laboratorio_integrato

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity (){

    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var email:EditText
    private lateinit var pwd:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        email = findViewById(R.id.email)
        pwd = findViewById(R.id.password)
        loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener{
            Log.d("email", email.text.toString())
            Log.d("password", pwd.text.toString())
            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnSuccessListener {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent);
                    finish();
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Email o Password errate", Toast.LENGTH_SHORT).show()
                }
        }
    }
}