package com.example.zavrsni_10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private val db:FirebaseFirestore= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn_register = findViewById<Button>(R.id.btn_register)
        val et_register_email = findViewById<EditText>(R.id.et_register_email)
        val et_register_password = findViewById<EditText>(R.id.et_register_password)
        val et_register_name = findViewById<EditText>(R.id.et_register_name)
        val et_register_age = findViewById<EditText>(R.id.et_register_age)
        val et_register_weight = findViewById<EditText>(R.id.et_register_weight)
        val et_register_height = findViewById<EditText>(R.id.et_register_height)
        val et_register_injury = findViewById<AutoCompleteTextView>(R.id.et_register_injury)

        val injuries = resources.getStringArray(R.array.injuries)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, injuries)
        et_register_injury.setAdapter(arrayAdapter)
        val tv_login = findViewById<TextView>(R.id.tv_login)

        tv_login.setOnClickListener {
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
        }
        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Molim Vas unesite email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' ' })->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Molim Vas unesite lozinku!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_name.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Molim Vas unesite ime!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_age.text.toString().trim { it <= ' ' })->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Molim Vas unesite dob!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_weight.text.toString().trim { it <= ' ' })->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Molim Vas, unesite težinu!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_height.text.toString().trim { it <= ' ' })->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Molim Vas, unesite visinu!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email:String = et_register_email.text.toString().trim{it <= ' '}
                    val password:String = et_register_password.text.toString().trim{it <= ' '}

                    val name:String = et_register_name.text.toString().trim{it <= ' '}
                    val age=et_register_age.text.toString().trim { it<=' ' }.toInt()
                    val weight=et_register_weight.text.toString().trim { it<=' ' }.toDouble()
                    val height=et_register_height.text.toString().trim { it<=' ' }.toInt()
                    val injury:String = et_register_injury.text.toString().trim{it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult>{task ->
                                if(task.isSuccessful){
                                    val firebaseUser: FirebaseUser =task.result!!.user!!
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You are registered successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    var bmi_base = weight/(height.toDouble()*height.toDouble()/10000)
                                    val bmi:Double = String.format("%.2f", bmi_base).toDouble()
                                    CreateUser(firebaseUser.uid,email, password, name, age, weight, height, bmi, injury)

                                    val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id",firebaseUser.uid)
                                    intent.putExtra("email_id",email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                }
            }
        }
    }
    private fun InsertUser(user: User,userID: String){
        val userReference=db.collection("Users")

        userReference.document(userID).set(user).addOnCompleteListener{
            when{
                it.isSuccessful->{
                    Toast.makeText(this,"User created",Toast.LENGTH_SHORT).show()
                }
                else ->{
                    Toast.makeText(this,"User creation failed",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun CreateUser(userID:String,email:String,password:String, name:String, age:Int,weight:Double,height:Int, bmi: Double, injury:String){
        val user=User(userID,email, password, name, age, weight, height,  bmi, injury)

        InsertUser(user,userID)
    }
}