package com.example.zavrsni_10

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class BackWorkoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_back_workout, container, false)

        val btn_add_history = v.findViewById<Button>(R.id.btn_add_history)
        val tv_workout_title = v.findViewById<TextView>(R.id.tv_workout_title)
        val tv_workout_calories = v.findViewById<TextView>(R.id.tv_workout_calories)

        val tv_barbellrow_link = v.findViewById<TextView>(R.id.tv_exercise1_link)
        val tv_reversefly_link = v.findViewById<TextView>(R.id.tv_exercise2_link)
        val tv_biecp_link = v.findViewById<TextView>(R.id.tv_exercise3_link)
        val tv_hammer_link = v.findViewById<TextView>(R.id.tv_exercise4_link)

        tv_barbellrow_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=FWJR5Ve8bnQ"))
            startActivity(i)
        }
        tv_reversefly_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=arw9w79rUPI"))
            startActivity(i)
        }
        tv_biecp_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=av7-8igSXTs"))
            startActivity(i)
        }
        tv_hammer_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=EdWCF9-ZAJI"))
            startActivity(i)
        }

        btn_add_history.setOnClickListener(){
            val workoutTitle = tv_workout_title.text.toString()
            val workoutCalories = tv_workout_calories.text.toString()
            saveFirestore(workoutTitle, workoutCalories)
            Toast.makeText(context,"Added to history", Toast.LENGTH_SHORT).show()
        }
        return v
    }

    fun saveFirestore(workoutTitle: String, workoutCalories: String){
        val currentUserReference = FirebaseAuth.getInstance().uid.toString()
        val db = FirebaseFirestore.getInstance()

        val df = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        val dateformat = df.format(cal.time)

        val workout: MutableMap<String, Any> = HashMap()
        workout["workoutTitle"] = workoutTitle
        workout["date"] = dateformat
        workout["calories"] = workoutCalories

        db.collection("Users").document(currentUserReference).collection("History").add(workout)
    }
}