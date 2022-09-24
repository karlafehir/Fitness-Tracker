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

class QuadsWorkoutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_quads_workout, container, false)

        val btn_add_history = v.findViewById<Button>(R.id.btn_add_history)
        val tv_workout_title = v.findViewById<TextView>(R.id.tv_workout_title)
        val tv_workout_calories = v.findViewById<TextView>(R.id.tv_workout_calories)

        val tv_squat_link = v.findViewById<TextView>(R.id.tv_exercise1_link)
        val tv_goblet_link = v.findViewById<TextView>(R.id.tv_exercise2_link)
        val tv_bulgarian_link = v.findViewById<TextView>(R.id.tv_exercise3_link)
        val tv_lunges_link = v.findViewById<TextView>(R.id.tv_exercise4_link)

        tv_squat_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Dy28eq2PjcM"))
            startActivity(i)
        }
        tv_goblet_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=MxsFDhcyFyE"))
            startActivity(i)
        }
        tv_bulgarian_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=2C-uNgKwPLE"))
            startActivity(i)
        }
        tv_lunges_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=3XDriUn0udo"))
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