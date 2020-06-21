package com.example.tortoise

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), View.OnClickListener {

    private val TAG_FRAGMENT = "MAIN_FRAGMENT"
    private val TAG_COROUTINE = "${TAG_FRAGMENT}_COROUTINE"
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        getFlightsButton.setOnClickListener {
            Log.i(TAG_FRAGMENT,"Clicked Flights Button, going to Flight Fragment...")
            navController.navigate(R.id.action_mainFragment_to_flightFragment)
        }
    }

    override fun onClick(v: View?) {
    }



}