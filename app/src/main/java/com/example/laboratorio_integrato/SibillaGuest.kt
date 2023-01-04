package com.example.laboratorio_integrato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.laboratorio_integrato.fragments.HomeFragment
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

private lateinit var question: EditText
private lateinit var name: EditText
private lateinit var surname: EditText
private lateinit var hometown: EditText
private lateinit var button: Button
private lateinit var tripleView: TextView
private lateinit var resultView: TextView
private var firsLetter = 0
private var lastLetter = 0
private var letters = arrayOf("kqwyx", "huv", "ers", "mt", "lo", "ag", "ijn", "cf", "dz", "pb");
private var numbers = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

class SibillaLogged : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sibilla_guest)
        question = findViewById(R.id.question)
        name = findViewById(R.id.name)
        surname = findViewById(R.id.surname)
        hometown = findViewById(R.id.hometown)
        button = findViewById(R.id.button)
        tripleView = findViewById(R.id.tripleView)
        resultView = findViewById(R.id.resultView)

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
                var jsonString = assets.open("database.json").bufferedReader().use {
                    it.readText()
                }
                val sType = object : TypeToken<List<HomeFragment.Sibilla>>() {}.type
                val otherList: List<HomeFragment.Sibilla> = gson.fromJson(jsonString, sType)

                Log.d("json", "${otherList[0].id}")

                positionTaker(numbersSum, otherList)
                resultView.text = positionTaker(numbersSum, otherList);

            } else {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

        }
    }

    fun positionTaker(numberSum: IntArray, otherList: List<HomeFragment.Sibilla>): String{

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


}