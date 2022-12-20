package com.example.laboratorio_integrato


import android.accounts.Account
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentProviderClient
import android.content.ContentValues.TAG
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
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val REQUEST_CODE_SING_IN = 0

class LoginActivity : AppCompatActivity (){

    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var email:EditText
    private lateinit var pwd:EditText
    private lateinit var showButton: Button
    private lateinit var googleLogin: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginFacebookButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        showButton = findViewById(R.id.showButton)
        loginButton = findViewById(R.id.buttonLogin);
        googleLogin = findViewById(R.id.googleLogin)
        firebaseAuth = FirebaseAuth.getInstance()
        loginFacebookButton = findViewById(R.id.login_button_facebook)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleLogin.setOnClickListener(){
            googleLogin()
        }

        loginFacebookButton.setOnClickListener(){
            
        }


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
private fun googleLogin(){

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    val signInIntent = mGoogleSignInClient.signInIntent
    resultLauncher.launch(signInIntent)
   // mGoogleSignInClient.signOut()
}

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val data: Intent? = result.data
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            val idToken = account.idToken
            val email = account.email


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } catch (e: ApiException) {
            Log.e("AuthError", "signInResult:failed code=" + e.statusCode)
        }
    }
}