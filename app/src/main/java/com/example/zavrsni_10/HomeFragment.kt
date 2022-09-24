package com.example.zavrsni_10


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class HomeFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val currentUserReference = FirebaseAuth.getInstance().uid.toString()
        val currentUser = db.collection("Users").document(currentUserReference)

        val currentUserBMI = view.findViewById<TextView>(R.id.tv_bmi_num)
        val currentUserBMIstatus = view.findViewById<TextView>(R.id.tv_bmi_status)

        val tv_workout_title = view.findViewById<TextView>(R.id.tv_workout_title)
        val tv_workout_description = view.findViewById<TextView>(R.id.tv_workout_description)
        val btn_start_workout = view.findViewById<Button>(R.id.btn_start_workout)

        val tv_workout_title2 = view.findViewById<TextView>(R.id.tv_workout_title2)
        val tv_workout_description2 = view.findViewById<TextView>(R.id.tv_workout_description2)
        val btn_start_workout2 = view.findViewById<Button>(R.id.btn_start_workout2)

        val tv_workout_title3 = view.findViewById<TextView>(R.id.tv_workout_title3)
        val tv_workout_description3 = view.findViewById<TextView>(R.id.tv_workout_description3)
        val btn_start_workout3 = view.findViewById<Button>(R.id.btn_start_workout3)

        currentUser.get().addOnSuccessListener { result ->
            currentUserBMI.text = result.data?.get("bmi").toString()
            when(result.data?.get("bmi") as Double) {
                in 0.0..17.0 -> currentUserBMIstatus.text = "Vaš BMI je prenizak"
                in 17.0..19.0 -> currentUserBMIstatus.text = "Vaš BMI je nizak"
                in 19.0..25.0 -> currentUserBMIstatus.text = "Vaš BMI je normalan"
                in 25.0..30.0 -> currentUserBMIstatus.text = "Vaš BMI je visok"
                else -> currentUserBMIstatus.text = "Vaš BMI je previsok"
            }

            when(result.data?.get("injury")) {
                "Dijabetes" -> {
                    tv_workout_title.text = "Program za dijabetes";
                    tv_workout_title2.text = "Program za rastezanje";
                    tv_workout_title3.text = "Program za kardio";
                }
                "Invaliditet" -> {
                    tv_workout_title.text = "Program za leđa i biceps";
                    tv_workout_title2.text = "Program za ramena";
                    tv_workout_title3.text = "Program za prsa i triceps";
                }
                "Ozljeda koljena" -> {
                    tv_workout_title.text = "Program za leđa i biceps";
                    tv_workout_title2.text = "Program za ramena";
                    tv_workout_title3.text = "Program za prsa i triceps";
                }
                "Ozljeda leđa" -> {
                    tv_workout_title.text = "Program za kvadriceps";
                    tv_workout_title2.text = "Program za zadnju ložu";
                    tv_workout_title3.text = "Program za kardio";
                }
                "Nema" -> {
                    tv_workout_title.text = "Program za donji dio";
                    tv_workout_title2.text = "Program za gornji dio";
                    tv_workout_title3.text = "Program za kardio";
                }
                else -> {
                    tv_workout_title.text = "Program za donji dio";
                    tv_workout_title2.text = "Program za gornji dio";
                    tv_workout_title3.text = "Program za kardio";
                }
            }

            when(result.data?.get("injury")) {
                "Dijabetes" -> {
                    tv_workout_title.text = "Program za dijabetes";
                    tv_workout_title2.text = "Program za istezanje";
                    tv_workout_title3.text = "Program za kardio";
                }
                "Invaliditet" -> {
                    tv_workout_title.text = "Program za leđa i biceps";
                    tv_workout_title2.text = "Program za ramena";
                    tv_workout_title3.text = "Program za prsa i triceps";
                }
                "Ozljeda koljena" -> {
                    tv_workout_title.text = "Program za leđa i biceps";
                    tv_workout_title2.text = "Program za ramena";
                    tv_workout_title3.text = "Program za prsa i triceps";
                }
                "Ozljeda leđa" -> {
                    tv_workout_title.text = "Program za kvadriceps";
                    tv_workout_title2.text = "Program za zadnju ložu";
                    tv_workout_title3.text = "Program za kardio";
                }
                "Nema" -> {
                    tv_workout_title.text = "Program za donji dio tijela";
                    tv_workout_title2.text = "Program za gornji dio tijela";
                    tv_workout_title3.text = "Program za kardio";
                }
                else -> {
                    tv_workout_title.text = "Program za donji dio tijela";
                    tv_workout_title2.text = "Program za gornji dio tijela";
                    tv_workout_title3.text = "Program za kardio";
                    tv_workout_description.text = "6 vježbi | 40 min"
                    tv_workout_description2.text = "6 vježbi | 40 min"
                    tv_workout_description3.text = "4 vježbe | 10 min + 30 min"
                }
            }
                when(result.data?.get("injury")) {
                    "Invaliditet" -> {
                        btn_start_workout.setOnClickListener() { selectWorkoutFragment(BackWorkoutFragment()) }
                        btn_start_workout2.setOnClickListener() { selectWorkoutFragment(ShoulderWorkoutFragment()) }
                        btn_start_workout3.setOnClickListener() { selectWorkoutFragment(ChestWorkoutFragment()) }
                    }
                    "Dijabetes" -> {
                        btn_start_workout.setOnClickListener() { selectWorkoutFragment(DiabetesWorkoutFragment()) }
                        btn_start_workout2.setOnClickListener() { selectWorkoutFragment(StrechingWorkoutFragment()) }   //
                        btn_start_workout3.setOnClickListener() { selectWorkoutFragment(CardioWorkoutFragment()) }      //
                    }
                    "Ozljeda koljena" -> {
                        btn_start_workout.setOnClickListener() { selectWorkoutFragment(BackWorkoutFragment()) }         //
                        btn_start_workout2.setOnClickListener() { selectWorkoutFragment(ShoulderWorkoutFragment()) }    //
                        btn_start_workout3.setOnClickListener() { selectWorkoutFragment(ChestWorkoutFragment()) }       //
                    }
                    "Ozljeda leđa" -> {
                        btn_start_workout.setOnClickListener() { selectWorkoutFragment(QuadsWorkoutFragment()) }        //
                        btn_start_workout2.setOnClickListener() { selectWorkoutFragment(HamstringWorkoutFragment()) }   //
                        btn_start_workout3.setOnClickListener() { selectWorkoutFragment(CardioWorkoutFragment()) }      //
                    }
                    "Nema" -> {
                        btn_start_workout.setOnClickListener() { selectWorkoutFragment(LowerWorkoutFragment()) }        //
                        btn_start_workout2.setOnClickListener() { selectWorkoutFragment(UpperWorkoutFragment()) }       //
                        btn_start_workout3.setOnClickListener() { selectWorkoutFragment(CardioWorkoutFragment()) }      //
                    }
                    else -> selectWorkoutFragment(LowerWorkoutFragment())
                }
            }
        return view
    }
    private fun selectWorkoutFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}

