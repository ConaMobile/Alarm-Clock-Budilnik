package com.conamobile.alarmclockbudilnik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_notify.*

class NotifyFragment : Fragment() {
lateinit var root:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_notify, container, false)

        button.setOnClickListener {
            Toast.makeText(context, "Turn Off", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}