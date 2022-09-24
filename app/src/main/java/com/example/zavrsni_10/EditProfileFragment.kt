package com.example.zavrsni_10

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EditProfileFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        val currentUserReference = FirebaseAuth.getInstance().uid.toString()
        val currentUser = db.collection("Users").document(currentUserReference)

        val btn_edit_db = v.findViewById<Button>(R.id.btn_edit_db)
        val et_edit_weight = v.findViewById<EditText>(R.id.et_edit_weight)
        val et_edit_height = v.findViewById<EditText>(R.id.et_edit_height)


        val activity: Activity? = activity



        btn_edit_db.setOnClickListener() {
            when {
                TextUtils.isEmpty(et_edit_weight.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(activity, "Molim Vas unesite težinu!", Toast.LENGTH_SHORT)
                        .show()
                }
                TextUtils.isEmpty(et_edit_height.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(activity, "Molim Vas unesite visinu!", Toast.LENGTH_SHORT)
                        .show()
                }
                else ->{
                    var updatedWeight = et_edit_weight.text.toString().toDouble()
                    db.collection("Users").document(currentUserReference).update("weight", updatedWeight)

                    var updatedHeight = et_edit_height.text.toString().toDouble()
                    db.collection("Users").document(currentUserReference).update("height", updatedHeight)

                    var updatedBMI_base = updatedWeight / (updatedHeight.toDouble() * updatedHeight.toDouble() / 10000)
                    val updatedBMI: Double = String.format("%.2f", updatedBMI_base).toDouble()
                    db.collection("Users").document(currentUserReference).update("bmi", updatedBMI)

                    changeFragment(ProfileFragment())
                }
            }
        }
        return v
    }
    private fun changeFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}