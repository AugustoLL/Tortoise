package com.example.tortoise

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tortoise.apis.skyscanner.ApiRequest
import com.example.tortoise.apis.skyscanner.Flight
import kotlinx.android.synthetic.main.fragment_flight.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class FlightFragment : Fragment(), View.OnClickListener {

    private val TAG_FRAGMENT = "FLIGHT_FRAGMENT"
    private val TAG_COROUTINE = "${TAG_FRAGMENT}_COROUTINE"
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //Make an API Request using coroutines
        apiRequest(this.context)
    }

    override fun onClick(v: View?) {
    }

    /*Make the request from the api, and give the context of the fragment,
    so the third coroutine (setViews()), can inflate as many flight_row.xml,
    as the flights gotten from the API*/
    @SuppressLint("LongLogTag")
    private fun apiRequest(fragmentContext: Context?){


        val startTime = System.currentTimeMillis()

        val parentJob = CoroutineScope(Dispatchers.IO).launch {
            val job1 = async {
                val time = measureTimeMillis {
                    Log.i(TAG_COROUTINE,"Launching job1 in thread: ${Thread.currentThread().name}")
                    getResultFromApi()
                }
                Log.i(TAG_COROUTINE,"Completed job1 in ${time}ms.")
            }
            job1.join()

            val job2 = async {
                val time = measureTimeMillis {
                    Log.i(TAG_COROUTINE,"Launching job2 in thread: ${Thread.currentThread().name}")
                    saveDataInFlightObject()
                }
                Log.i(TAG_COROUTINE,"Completed job2 in ${time}ms.")
            }.await()
        }
        parentJob.invokeOnCompletion {
            Log.i(TAG_COROUTINE,"PARENT JOB 1: Total elapsed time: ${System.currentTimeMillis() - startTime}ms")
        }

        val parentJob2 = CoroutineScope(Dispatchers.Main).launch {
            async {
                val time = measureTimeMillis {
                    Log.i(TAG_COROUTINE, "Launching job3 in thread: ${Thread.currentThread().name}")
                    setViews(fragmentContext)
                }
                Log.i(TAG_COROUTINE,"Completed job3 in ${time}ms.")
            }.await()
        }
        parentJob2.invokeOnCompletion {
            Log.i(TAG_COROUTINE,"PARENT JOB 2: Total elapsed time: ${System.currentTimeMillis() - startTime}ms")
        }
    }

    /*
    * Get the result from the API using the object ApiRequest
    * and with Gson save the JSon response in the dataclasses from the skyScannerAPI package*/
    @SuppressLint("LongLogTag")
    private suspend fun getResultFromApi(){
        withContext(Dispatchers.IO){
            Log.i(TAG_COROUTINE,"Entered Coroutine, suspended function getResultFromApi")
            ApiRequest.callSkyScannerAPI()
        }
    }

    /*
    * Save the data in the from the SkyScanner dataclass in the Flight Object*/
    @SuppressLint("LongLogTag")
    private suspend fun saveDataInFlightObject(){
        withContext(Dispatchers.IO){
            delay(3000)
            Log.i(TAG_COROUTINE,"Entered showData()")
            Flight.setFlightsList()
        }
    }

    @SuppressLint("LongLogTag")
    private suspend fun setViews(fragmentContext: Context?){
        withContext(Dispatchers.Main){
            delay(5000)
            Log.i(TAG_COROUTINE,"Entered SetViews()")

            recyclerView.layoutManager = LinearLayoutManager(fragmentContext)
            recyclerView.adapter = MainAdapter(Flight.flights.size)
        }
    }

}
