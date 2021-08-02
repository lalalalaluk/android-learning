package com.yorukarustudio.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nickname.*

class NicknameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)
    }

    fun setNickName_btn(view: View){
        FirebaseDatabase.getInstance()
            .getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("nickname")
            .setValue(nickname.text.toString())
        setNickname(nickname.text.toString())
        setResult(RESULT_OK)
        finish()
    }
}