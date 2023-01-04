package com.example.laboratorio_integrato.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.laboratorio_integrato.LoginActivity
import com.example.laboratorio_integrato.LoginOrRegister
import com.example.laboratorio_integrato.MainActivity
import com.example.laboratorio_integrato.R
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    private lateinit var loginFacebookButton: LoginButton
    private lateinit var auth:FirebaseAuth
    private lateinit var logOut:Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var googleLogin: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth= FirebaseAuth.getInstance()
        loginFacebookButton = view.findViewById(R.id.login_button_facebook)
        logOut = view.findViewById(R.id.LogOut)
        googleLogin = view.findViewById(R.id.googleLogin)
        logOut.setOnClickListener {
            auth.signOut()

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val mGoogleSignInClient = activity?.let { it1 -> GoogleSignIn.getClient(it1, gso) }

            mGoogleSignInClient?.signOut()

            LoginManager.getInstance().logOut()
            val intent = Intent(activity, LoginOrRegister::class.java)
            startActivity(intent)
            activity?.finish()
        }
        callbackManager = CallbackManager.Factory.create()
        loginFacebookButton.setPermissions(Arrays.asList("email","public_profile"))
        loginFacebookButton.registerCallback(callbackManager, object:
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }
            override fun onCancel() {
                Log.d("FacciaLibro", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("FacciaLibro", "facebook:onError", error)
            }
        })
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!

        googleLogin.setOnClickListener(){
            googleLogin()
        }
    }
    private fun handleFacebookAccessToken(token: AccessToken){
        val credential = FacebookAuthProvider.getCredential(token.token)
        val currentUser = auth.currentUser
        currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(activity, "Facebook Linkato", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun googleLogin(){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }

        val signInIntent = mGoogleSignInClient?.signInIntent
        resultLauncher.launch(signInIntent)
        //mGoogleSignInClient.signOut()
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val data: Intent? = result.data
        Log.d("funz",result.resultCode.toString())
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            Toast.makeText(activity, result.resultCode.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            val currentUser = auth.currentUser
            currentUser!!.linkWithCredential(credential)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(activity, "Google Linkato", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            val idToken = account.idToken
            val email = account.email

        } catch (e: ApiException) {
            Log.e("AuthError", "signInResult:failed code=" + e.statusCode)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}