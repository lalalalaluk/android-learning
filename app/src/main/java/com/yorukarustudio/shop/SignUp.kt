package com.yorukarustudio.shop

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun signup(view: View){
        val email_input = email_input.text.toString()
        val password_input = password_input.text.toString()
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email_input,password_input)
            .addOnCompleteListener {
                if (it.isSuccessful()){
                    AlertDialog.Builder(this)
                        .setTitle("Sign Up")
                        .setMessage("Account success")
                        .setPositiveButton("OK",{dialog,which ->
                            setResult(Activity.RESULT_OK)
                            finish()
                        }).show()
                }else{
                    AlertDialog.Builder(this)
                        .setTitle("Sign Up")
                        .setMessage(it.exception?.message)
                        .setPositiveButton("OK",null)
                        .show()
                }
            }
    }
}