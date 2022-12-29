package com.example.laboratorio_integrato.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.laboratorio_integrato.R
//import com.example.laboratorio_integrato.Sibilla
import com.example.laboratorio_integrato.SibillaFun
//import com.example.laboratorio_integrato.positionTaker
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import android.annotation.SuppressLint
import android.system.Os.open
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import com.example.laboratorio_integrato.fragments.ConsultsFragment
import com.example.laboratorio_integrato.fragments.HomeFragment
import com.example.laboratorio_integrato.fragments.ProfileFragment
import com.example.laboratorio_integrato.fragments.ShopFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.common.collect.Range.open
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import org.json.JSONTokener
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.DatagramChannel.open
import java.nio.channels.Selector.open
import java.util.Objects
import kotlin.math.log



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private lateinit var question: EditText
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var hometown: EditText
    private lateinit var button: Button
    private lateinit var tripleView: TextView
    private lateinit var resultView: TextView



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question = view.findViewById(R.id.question)
        name = view.findViewById(R.id.name)
        surname = view.findViewById(R.id.surname)
        hometown = view.findViewById(R.id.hometown)
        button = view.findViewById(R.id.button)
        tripleView = view.findViewById(R.id.tripleView)
        resultView = view.findViewById(R.id.resultView)

        button.setOnClickListener {
            if (question.text.isNotEmpty() &&
                name.text.isNotEmpty() &&
                surname.text.isNotEmpty() &&
                hometown.text.isNotEmpty()
            ) {
                var questionText = question.text.toString().split(" ")
                var numbersSum = SibillaFun.selector(
                    questionText,
                    name.text.toString(),
                    surname.text.toString(),
                    hometown.text.toString()
                );

                tripleView.text =
                    "[" + numbersSum[0].toString() + "," + numbersSum[1].toString() + "," + numbersSum[2].toString() + "]" + "\n" +
                            "[" + numbersSum[1].toString() + "," + numbersSum[2].toString() + "," + numbersSum[0].toString() + "]" + "\n" +
                            "[" + numbersSum[2].toString() + "," + numbersSum[0].toString() + "," + numbersSum[1].toString() + "]";
                Log.d("ciao", numbersSum.toString());

                val gson = GsonBuilder().create()
                var jsonString = activity?.assets?.open("database.json")?.bufferedReader().use {
                    it?.readText()
                }
                val sType = object : TypeToken<List<Sibilla>>() {}.type
                val otherList: List<Sibilla> = gson.fromJson(jsonString, sType)

                Log.d("json", "${otherList[0].id}")

                positionTaker(numbersSum, otherList)
                resultView.text = positionTaker(numbersSum, otherList);

            } else {
                Toast.makeText(activity, "Fill in all fields", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

        }


    }


    fun positionTaker(numberSum: IntArray, otherList: List<Sibilla>): String{

        var j = 0;
        var firstRow1: String? = "";
        var firstRow2: String? = "";
        var secondRow1: String? = "";
        var secondRow2: String? = "";
        var thirdRow1: String? = "";
        var thirdRow2: String? = "";
        var response: String? = "";
        do{
            if(otherList[j].pos_tripla == "1"){
                if(otherList[j].pos1 == numberSum[0].toString()
                    && otherList[j].pos2 == numberSum[1].toString()
                    && otherList[j].pos3 == numberSum[2].toString()){
                    firstRow1 = otherList[j].stringa1;
                    firstRow2 = otherList[j].stringa2;
                    Log.d("result1", firstRow1 +firstRow2)
                }
            }

            if(otherList[j].pos_tripla == "2"){
                if(otherList[j].pos1 == numberSum[1].toString()
                    && otherList[j].pos2 == numberSum[2].toString()
                    && otherList[j].pos3 == numberSum[0].toString()){
                    secondRow1 = otherList[j].stringa1;
                    secondRow2 = otherList[j].stringa2;

                    Log.d("result2", secondRow1 +secondRow2)


                }
            }

            if(otherList[j].pos_tripla == "3"){
                if(otherList[j].pos1 == numberSum[2].toString()
                    && otherList[j].pos2 == numberSum[0].toString()
                    && otherList[j].pos3 == numberSum[1].toString()){
                    thirdRow1 = otherList[j].stringa1;
                    thirdRow2 = otherList[j].stringa2;

                    Log.d("result3", thirdRow1 +thirdRow2)
                }
            }

            response = firstRow1 + secondRow1 + thirdRow1 + firstRow2 + secondRow2 + thirdRow2;
            Log.d("Response", response)
            j++;

        }while(j<otherList.size);
        return response.toString();
    }

    data class Sibilla(
        var id: String? = null,
        var pos1: String? = null,
        var pos2: String? = null,
        var pos3: String? = null,
        var pos_tripla: String? = null,
        var stringa1: String? = null,
        var stringa2: String? = null,


        )

    companion object {

        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}