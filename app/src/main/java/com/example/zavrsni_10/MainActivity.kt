package com.example.zavrsni_10

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(HomeFragment())

        val btn_profile=findViewById<ImageView>(R.id.btn_profile)
        val btn_home=findViewById<ImageView>(R.id.btn_home)
        val btn_logout = findViewById<ImageView>(R.id.btn_logout)

        btn_profile.setOnClickListener(){
           setCurrentFragment(ProfileFragment())
        }
        btn_home.setOnClickListener(){
            setCurrentFragment(HomeFragment())
        }
        btn_logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}