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

//        actionbar
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleLogin.setOnClickListener(){
//            singInGoogle()

            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.webclient_id))
                .requestEmail()
                .build()

            val signInClient = GoogleSignIn.getClient(this,options)
            signInClient.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE_SING_IN)
            }

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

    private fun singInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->

        if(result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }

    }

    private fun handleResults(task: Task<GoogleSignInAccount>){

        if(task.isSuccessful){
            val account : GoogleSignInAccount? =task.result

            if(account != null){
                updateUI(account)
            }
        }else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                val intent : Intent = Intent(this, MainActivity::class.java )
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credential).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginActivity, "Successo", Toast.LENGTH_SHORT).show()

                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val intent : Intent = Intent(this, MainActivity::class.java )
        startActivity(intent)

        if(resultCode == REQUEST_CODE_SING_IN){
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent).result
            account?.let {
                googleAuthForFirebase(it)
            }
        }
    }
}