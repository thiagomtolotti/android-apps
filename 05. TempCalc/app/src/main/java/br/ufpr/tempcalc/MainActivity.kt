package br.ufpr.tempcalc

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun convert(view: View) {
        val textInput = findViewById<EditText>(R.id.editTextInput)
        val textOutput = findViewById<TextView>(R.id.textViewOutput)
        val toCelsius = findViewById<RadioButton>(R.id.toCelsius).isChecked

        if(textInput.length() == 0) {
            Toast.makeText(this, "Forneça a temperatura", Toast.LENGTH_SHORT).show()
            textOutput.text = "Resultado: 0.0 ºC"

            return
        }

        val inputValue = textInput.text.toString().toDouble()
        var result:Double = 0.0

        if(toCelsius) {
            result = (inputValue - 32) / 1.8
            textOutput.text = "Resultado: ${String.format("%.2f",result)} ºC"
        }else{
            result = (inputValue * 1.8) + 32
            textOutput.text = "Resultado  ${String.format("%.2f",result)} ºF"
        }
    }
}