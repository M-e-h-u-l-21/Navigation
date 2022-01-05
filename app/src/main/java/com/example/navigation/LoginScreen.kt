package com.example.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

 const val REQUEST_CODE_SIGN_IN=0


class LoginScreen:AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginscreen)
        auth= FirebaseAuth.getInstance()
        val bto=findViewById<Button>(R.id.btnregister)
        val btn=findViewById<Button>(R.id.login)
        val bts=findViewById<Button>(R.id.btnsigningoogle)
        bto.setOnClickListener {
            val intent=Intent(this,Registration::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener {
            loginUser()
        }
        bts.setOnClickListener {
            val options=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient_id))
                .requestEmail()
                .build()
            val signInClient=GoogleSignIn.getClient(this,options)
            signInClient.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE_SIGN_IN)
            }


        }



    }
    private fun loginUser(){
        val email:String= findViewById<EditText>(R.id.etemailid).text.toString()
        val password=findViewById<EditText>(R.id.etpassword).text.toString()

        if(email.isNotEmpty()&& password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        checkLoggedInState()
                    }

                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginScreen,e.message, Toast.LENGTH_SHORT ).show()
                    }
                }

            }
        }




    }
    private fun checkLoggedInState(){
        if (auth.currentUser==null){
            Toast.makeText(this,"Login Unsuccessful, Try Again!!",Toast.LENGTH_SHORT).show()
        }
        else{
            val int=Intent(this,MainActivity::class.java)
            startActivity(int)
        }
    }
    private fun googleAuthForFirebase(account:GoogleSignInAccount){
        val credentials=GoogleAuthProvider.getCredential(account.idToken,null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginScreen,"Login Successful",Toast.LENGTH_SHORT).show()
                }
                val intent=Intent(this@LoginScreen,MainActivity::class.java)
                startActivity(intent)

            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginScreen,e.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== REQUEST_CODE_SIGN_IN){
            val account=GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthForFirebase(it)
                val intent=Intent(this@LoginScreen,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
