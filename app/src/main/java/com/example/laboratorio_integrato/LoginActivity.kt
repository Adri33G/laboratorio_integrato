package com.example.laboratorio_integrato


import android.accounts.Account
import android.annotation.SuppressLint
import android.content.ContentProviderClient
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
import com.example.laboratorio_integrato.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity (){

    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var email:EditText
    private lateinit var pwd:EditText
    private lateinit var showButton: Button
    private lateinit var googleLogin: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
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
        googleLogin = findViewById(R.id.googleLogin)
        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener{
            Log.d("email", email.text.toString())
            Log.d("password", pwd.text.toString())
            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(this, "Accesso", Toast.LENGTH_SHORT).show()
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