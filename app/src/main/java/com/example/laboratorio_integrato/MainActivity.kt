package com.example.laboratorio_integrato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var question: EditText
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var hometown: EditText
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var letters = arrayOf("kqwyx", "huv", "ers", "mt", "lo", "ag", "ijn", "cf", "dz", "pb");
        var numbers = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        var numbersSum = 0;

        question = findViewById(R.id.question)
        name = findViewById(R.id.name)
        surname = findViewById(R.id.surname)
        hometown = findViewById(R.id.hometown)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            var questionText = question.text.toString().split(" ")


            for (i in 0..questionText.size-1) {
                var singleLetter= questionText[i].first();
                var j = 0;
                do {
                    var k = 0;
                    var finded = false;
                    do{
                        Log.d("vet", letters[j].get(k).toString());
                        if(letters[j].get(k).toString() == singleLetter.toString()){
                            numbersSum += numbers[j];
                            Log.d("numbers", numbers[j].toString());
                            finded = true
                        }
                        else{
                            k++;
                        }
                    }while(k < letters[j].length && !finded);
                    j++;
                }while(j<letters.size && !finded);
            }
            Log.d("ciao", numbersSum.toString());

        }
    }
}

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