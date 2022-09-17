package com.dntrindade.viradadesorte

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)


        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)


        if (result != null) {
            txtResult.text = "Ultima aposta: $result"
        }


        btnGenerate.setOnClickListener {

            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 10", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt()

        if (qtd < 6 || qtd > 10) {
            Toast.makeText(this, "Informe um número entre 6 e 10", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(99)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()
        }

    }

}