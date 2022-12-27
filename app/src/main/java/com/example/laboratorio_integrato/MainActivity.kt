package com.example.laboratorio_integrato

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.json.JSONTokener
import java.util.Objects
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var question: EditText
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var hometown: EditText
    private lateinit var button: Button
    private lateinit var tripleView: TextView
    private lateinit var resultView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)



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
                val sType = object : TypeToken<List<Sibilla>>() {}.type
                val otherList: List<Sibilla> = gson.fromJson(jsonString, sType)

                Log.d("json", "${otherList[0].id}")

                positionTaker(numbersSum, otherList)
                resultView.text = positionTaker(numbersSum, otherList);

            } else {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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

//Sarò fortunato in amore nel 2023? Federico Gervasi Rovereto
//1. si prendono le prime iniziali di tutta la domanda compresi nome cognome e luogo
// lettere che corrispondo ad un valore (facciamo con array) S = 2; F = 7; I = 6; A = 5; N = 6; F = 7; G = 5; R = 2;


//2. PRIMA TRIPLETTTA :si sommano i numeri della domanda ( S = 2; F = 7; I = 6; A = 5; N = 6; ) = 26
//si considera solo l'unità quindi il 6
//il 6 sarà il primo valore di tripletta

//3. SECONDA TRIPLETTA: si sommano i numeri del nome, cognome e ultima parola domanda 7+5+2+6 (nel) = 20
//si considera sempre l'unità quindi 0
//lo 0 sarà il secondo valore della tripletta

//4. TERZA TRIPLETTA: si sommano i numeri del nome, cognome, prima lettera della prima ed ultima parola 7+5+2+6 = 20
//si considera l'unità sempre quindi 0
//lo 0 sarà il terzo valore della tripletta

//si devono fromare altre 2 triplette con la permutazione della tripletta trovata (6 / 0 / 0)
//1 / 2 / 3 ===== 6 / 0 / 0
//2 / 3 / 1 ===== 0 / 0 / 6
//3 / 1 / 2 ===== 0 / 6 / 0