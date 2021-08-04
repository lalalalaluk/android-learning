package com.yorukarustudio.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.row_function.view.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val RC_SIGNUP = 200
    private val RC_NICKNAME = 210
    var signup = false
    val auth = FirebaseAuth.getInstance()
    val functions = listOf<String>(
        "Camera",
        "Invite friend",
        "Download coupons",
        "News",
        "Maps"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        auth.addAuthStateListener { auth ->
            authChanged(auth)
        }

        //Spinner
        val colors = arrayOf("red","Green","Blue")
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,colors)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("homepage", "onItemSelected: ${colors[position]}")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()


    }

    inner class FunctionAdapter():RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function,parent,false)
            var holder = FunctionHolder(view)
            return holder
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions.get(position)
            holder.itemView.setOnClickListener{view ->
                functionClicked(holder,position)
            }
        }

        override fun getItemCount(): Int {
            return functions.size
        }

    }

    private fun functionClicked(holder: MainActivity.FunctionHolder, position: Int) {
        Log.d(TAG, "functionClicked: ${position}")
        when(position){
            1 -> startActivity(Intent(this,ContactActivity::class.java))
            2 -> startActivity(Intent(this,ParkingActivity::class.java))
        }
    }

    class FunctionHolder(view: View):RecyclerView.ViewHolder(view){
        var nameText:TextView = view.row_name
    }

    override fun onResume() {
        super.onResume()
        FirebaseDatabase.getInstance()
            .getReference("users")
            .child(auth.currentUser!!.uid)
            .child("nickname")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    nickname.text = snapshot.value as String

                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun authChanged(auth: FirebaseAuth) {
        if(auth.currentUser == null){
            val intent = Intent(this,SignUp::class.java)
            startActivityForResult(intent,RC_SIGNUP) 
        }else{
            Log.d("signup", "authChanged: ${auth.currentUser?.uid}")

            if(getNickname() == ""){
                val intent = Intent(this,NicknameActivity::class.java)
                startActivityForResult(intent,RC_NICKNAME)
            }



        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RC_SIGNUP){
            if(resultCode == RESULT_OK){

            }
        }else if(resultCode == RC_NICKNAME){
            if(resultCode == RESULT_OK){

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}