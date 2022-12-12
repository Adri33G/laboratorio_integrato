package com.example.laboratorio_integrato

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private lateinit var auth: FirebaseAuth
private lateinit var emailToString: String
private lateinit var passwordToString: String
private lateinit var passwordControlToString: String

class RegisterActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var passwordControl: EditText
    lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        passwordControl = findViewById(R.id.passwordControl)
        button = findViewById(R.id.button)
        emailToString = ""
        passwordToString =""
        passwordControlToString = ""
        //val db = Firebase.firestore


        val actionbar = supportActionBar

        actionbar!!.title = "Back"

        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        button.setOnClickListener(){
            if(email.text.isNotEmpty()
                || passwordControl.text.isNotEmpty()
                || password.text.isNotEmpty()){

                emailToString = email.text.toString()
                passwordToString = password.text.toString()
                passwordControlToString = passwordControl.text.toString()
                if (passwordToString == passwordControlToString) {
                    auth.createUserWithEmailAndPassword(emailToString, passwordToString)

                        .addOnCompleteListener{
                            val intent= Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText( this,"Correct creation", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Password wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}