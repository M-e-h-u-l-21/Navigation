package com.example.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Registration:AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        auth= FirebaseAuth.getInstance()
        auth.signOut()
        val btn=findViewById<Button>(R.id.btnregisterfirst)
        btn.setOnClickListener {
            registerUser()

        }

    }
    private fun registerUser(){
        val email:String= findViewById<EditText>(R.id.etname).text.toString()
        val password=findViewById<EditText>(R.id.etpasswordreg).text.toString()
        val username=findViewById<TextView>(R.id.tvusername)
        if(email.isNotEmpty()&& password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        checkLoggedInState()
                    }

                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Registration,"Registration was not successful",Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }




    }
    private fun checkLoggedInState(){
        if (auth.currentUser==null){
            Toast.makeText(this,"Registration Unsuccessful, Try Again!!",Toast.LENGTH_SHORT).show()
        }
        else{
            val int=Intent(this,MainActivity::class.java)
            startActivity(int)
        }
    }
}


