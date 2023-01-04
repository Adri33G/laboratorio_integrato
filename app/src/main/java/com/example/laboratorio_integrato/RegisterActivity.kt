package com.example.laboratorio_integrato

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var passwordControl: EditText
    lateinit var button: Button
    lateinit var name: EditText
    lateinit var surname: EditText
    lateinit var hometown: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var emailToString: String
    private lateinit var passwordToString: String
    private lateinit var passwordControlToString: String
    private lateinit var nameToString: String
    private lateinit var surnameToString: String
    private lateinit var hometownToString: String
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        email = findViewById(R.id.emailRegister)
        password = findViewById(R.id.passwordRegister)
        passwordControl = findViewById(R.id.passwordControl)
        button = findViewById(R.id.button)
        name = findViewById(R.id.name)
        surname = findViewById(R.id.surname)
        hometown = findViewById(R.id.hometown)
        emailToString = ""
        nameToString = ""
        hometownToString = ""
        surnameToString = ""
        passwordToString =""
        passwordControlToString = ""
        db = FirebaseFirestore.getInstance()


        val actionbar = supportActionBar

        actionbar!!.title = "Back"

        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        button.setOnClickListener(){
            if(email.text.isNotEmpty()
                || passwordControl.text.isNotEmpty()
                || password.text.isNotEmpty()
                || name.text.isNotEmpty()
                || surname.text.isNotEmpty()
                || hometown.text.isNotEmpty()
            ){

                emailToString = email.text.toString()
                nameToString = name.text.toString()
                surnameToString = surname.text.toString()
                passwordToString = password.text.toString()
                hometownToString = hometown.text.toString()
                passwordControlToString = passwordControl.text.toString()



                if (passwordToString == passwordControlToString && isEmailValid(emailToString)) {

                    auth.createUserWithEmailAndPassword(emailToString, passwordToString)
                        .addOnCompleteListener (this){
                            val currentUser = auth.currentUser
                            val userHashMap = hashMapOf(
                                "name" to name.text.toString(),
                                "surname" to surname.text.toString(),
                                "hometown" to hometown.text.toString()
                            )

                            if (currentUser != null) {
                                Toast.makeText(this,"ho creato qualcoas", Toast.LENGTH_SHORT)
                                db.collection("users").document(currentUser.uid).set(userHashMap)
                                    .addOnCompleteListener {

                                        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        LoginOrRegister.closeActivity.finish()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    .addOnFailureListener{
                                        Toast.makeText(this,"dio cane", Toast.LENGTH_LONG)
                                    }
                            }


//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                            Toast.makeText( this,"Correct creation", Toast.LENGTH_SHORT).show()

                }
            }else {
                Toast.makeText(this, "Password or email wrong", Toast.LENGTH_SHORT).show()
            }

        } else {
                Toast.makeText(this, "Empty parameters", Toast.LENGTH_SHORT).show()
            }
    }


}
    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}