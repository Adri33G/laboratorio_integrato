package com.example.laboratorio_integrato

import android.util.Log

class SibillaFun {
    companion object{
        private var firsLetter = 0
        private var lastLetter = 0
        private var letters = arrayOf("kqwyx", "huv", "ers", "mt", "lo", "ag", "ijn", "cf", "dz", "pb");
        private var numbers = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        fun selector(questionText: List<String>,name: String, surname: String, hometown:String): IntArray{
            var numberSum = 0;
            var numbersSum = IntArray(3);
            var nameSum = 0
            var surnameSum = 0
            var hometownSum = 0

            for (i in 0..questionText.size-1)
            {
                var singleLetter= questionText[i].first().lowercase();
                var j = 0;
                do {
                    var k = 0;
                    var finded = false;
                    do{
                        Log.d("vet", letters[j].get(k).toString());
                        if(letters[j].get(k).toString() == singleLetter.toString()){
                            numberSum += numbers[j];
                            Log.d("numbers", numbers[j].toString());
                            finded = true;
                            if(i==0){
                                firsLetter = numbers[j]
                                Log.d("first", firsLetter.toString())
                            }
                            if(i == questionText.size-1){
                                lastLetter = numbers[j]
                                Log.d("last", lastLetter.toString())
                            }
                        }
                        else{
                            k++;
                        }
                    }while(k < letters[j].length && !finded);
                    j++;
                }while(j<letters.size && !finded);
            }
            if (numberSum >= 10){
                numberSum = numberSum % 10;
            }
            numbersSum[0] = numberSum
            //numbersSum[0] = numberSum
            var singleLetter= name.first().lowercase();
            nameSum = cicleLetters(singleLetter);
            Log.d("nameSum", nameSum.toString())

            singleLetter= surname.first().lowercase();
            surnameSum = cicleLetters(singleLetter);
            Log.d("surnameSum", surnameSum.toString())

            singleLetter= hometown.first().lowercase();
            hometownSum = cicleLetters(singleLetter);
            Log.d("hometownSum", hometownSum.toString())

            numbersSum[1] = nameSum+surnameSum+hometownSum+lastLetter

            if(numbersSum[1] >= 10){
                numbersSum[1] = numbersSum[1] % 10
            }

            numbersSum[2] = nameSum+surnameSum+firsLetter+lastLetter

            if(numbersSum[2] >= 10){
                numbersSum[2] = numbersSum[2] % 10
            }

            return numbersSum;

//        Log.d("AllVet", "[" + numbersSum[0] + "," + numbersSum[1] + "," + numbersSum[2] + "]" )
//
//        Log.d("AllVet1", "[" + numbersSum[1] + "," + numbersSum[2] + "," + numbersSum[0] + "]" )
//
//        Log.d("AllVet2", "[" + numbersSum[2] + "," + numbersSum[0] + "," + numbersSum[1] + "]" )
        }
        fun cicleLetters(singleLetter:String): Int{
            var numberSum = 0;
            var j = 0;
            do {
                var k = 0;
                var finded = false;
                do{
                    Log.d("vet", letters[j].get(k).toString());
                    if(letters[j].get(k).toString() == singleLetter.toString()){
                        numberSum += numbers[j];
                        Log.d("numbers", numbers[j].toString());
                        finded = true;
                    }
                    else{
                        k++;
                    }
                }while(k < letters[j].length && !finded);
                j++;
            }while(j<letters.size && !finded);
            if (numberSum >= 10){
                numberSum = numberSum % 10;
            }
            return numberSum
        }
    }
}