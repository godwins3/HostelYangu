package dex.alpha.hostelyangu.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import dex.alpha.hostelyangu.R
import dex.alpha.hostelyangu.student.StudentDashboard

class SeaterFragment : Fragment() {

    lateinit var viewSeater: View
    lateinit var rgrp : RadioGroup
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewSeater =  inflater.inflate(R.layout.fragment_seater, container, false)

        rgrp = viewSeater.findViewById(R.id.rgrp)

        nextButton = viewSeater.findViewById(R.id.next_button)
        nextButton.setOnClickListener(){
            var selectId = rgrp.checkedRadioButtonId
            if (selectId == -1){
                Toast.makeText(requireContext(),"Please select any one!!", Toast.LENGTH_SHORT).show()
            } else {
                val rB = rgrp.findViewById<RadioButton>(selectId)
                StudentDashboard.seater = rB.text.toString()

                // change fragment
                val basicDetails = BasicDetails()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,basicDetails).commit()

            }
        }


        return viewSeater
    }

}