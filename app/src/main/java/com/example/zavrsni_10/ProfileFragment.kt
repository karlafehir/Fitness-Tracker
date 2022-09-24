package com.example.zavrsni_10


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.type.DateTime
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDate.now


class ProfileFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

//    private lateinit var recyclerView : RecyclerView
//    private lateinit var historyArrayList: ArrayList<String>
//    private lateinit var historyAdapter: HistoryAdapter



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val currentUserName = view.findViewById<TextView>(R.id.tv_name)
        val currentUserInjury = view.findViewById<TextView>(R.id.tv_injury)
        val currentUserWeight = view.findViewById<TextView>(R.id.tv_weight_input)
        val currentUserHeight = view.findViewById<TextView>(R.id.tv_height_input)
        val currentUserAge = view.findViewById<TextView>(R.id.tv_age_input)

        val currentUserReference = FirebaseAuth.getInstance().uid.toString()
        val currentUser = db.collection("Users").document(currentUserReference)

        currentUser.get().addOnSuccessListener { result ->
            currentUserName.text = result.data?.get("name").toString()
            currentUserInjury.text = result.data?.get("injury").toString()
            currentUserWeight.text = result.data?.get("weight").toString()
            currentUserHeight.text = result.data?.get("height").toString()
            currentUserAge.text = result.data?.get("age").toString()
        }

        val workoutHistory = view.findViewById<TextView>(R.id.tv_workout_history)
        val workoutDate = view.findViewById<TextView>(R.id.tv_workout_date)
        val workoutCalories = view.findViewById<TextView>(R.id.tv_workout_kcal)

        val db = FirebaseFirestore.getInstance()
        db.collection("Users").document(currentUserReference).collection("History")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener {

                val title: StringBuffer = StringBuffer()
                val date: StringBuffer = StringBuffer()
                val calories: StringBuffer = StringBuffer()

                if(it.isSuccessful) {
                    for(document in it.result!!) {

                        title.append(document.data.getValue("workoutTitle" )).append("\n\n")
                        date.append(document.data.getValue("date" ).toString()).append("\n\n")
                        calories.append(document.data.getValue("calories" ).toString()).append("\n\n")
                    }
                    workoutHistory.setText(title)
                    workoutDate.setText(date)
                    workoutCalories.setText(calories)
                }
            }

        val btn_edit = view.findViewById<Button>(R.id.btn_edit)
        btn_edit.setOnClickListener(){
           changeFragment(EditProfileFragment())
        }
        return view
    }
    private fun changeFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}








