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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

class LoginActivity : AppCompatActivity (){

    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var email:EditText
    private lateinit var pwd:EditText
    private lateinit var showButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        showButton = findViewById(R.id.showButton)
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

            showButton.setOnClickListener {
                if(showButton.text.toString() == "Show"){
                    pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    showButton.text = "Hide"
                } else{
                    pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    showButton.text = "Show"
                }

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}