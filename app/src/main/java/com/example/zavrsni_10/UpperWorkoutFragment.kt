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

class UpperWorkoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        val v = inflater.inflate(R.layout.fragment_upper_workout, container, false)

        val btn_add_history = v.findViewById<Button>(R.id.btn_add_history)
        val tv_workout_title = v.findViewById<TextView>(R.id.tv_workout_title)
        val tv_workout_calories = v.findViewById<TextView>(R.id.tv_workout_calories)

        val tv_barbellrow_link = v.findViewById<TextView>(R.id.tv_exercise1_link)
        val tv_backrow_link = v.findViewById<TextView>(R.id.tv_exercise2_link)
        val tv_chestpress_link = v.findViewById<TextView>(R.id.tv_exercise3_link)
        val tv_pushup_link = v.findViewById<TextView>(R.id.tv_exercise4_link)
        val tv_bicepcurl_link = v.findViewById<TextView>(R.id.tv_exercise5_link)
        val tv_shoulderpress_link = v.findViewById<TextView>(R.id.tv_exercise6_link)

        tv_barbellrow_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=FWJR5Ve8bnQ"))
            startActivity(i)
        }
        tv_backrow_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=roCP6wCXPqo"))
            startActivity(i)
        }
        tv_chestpress_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=KjYak5vZO9s"))
            startActivity(i)
        }
        tv_pushup_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=i9sTjhN4Z3M"))
            startActivity(i)
        }
        tv_bicepcurl_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=av7-8igSXTs"))
            startActivity(i)
        }
        tv_shoulderpress_link.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=B-aVuyhvLHU"))
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