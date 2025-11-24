package br.ufpr.numberguess

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var sortedNumber: Int = (1..100).random()
    private var tries = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sortedNumber = (1..100).random()
        tries = 0

        println("SORTED NUMBER $sortedNumber")

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun guess(view: View) {
        val guessComponent =findViewById<EditText>(R.id.editTextInput)
        val tipComponent = findViewById<TextView>(R.id.textViewOutput)
        val resetButton = findViewById<Button>(R.id.button)

        if(guessComponent.length() == 0) {
            Toast.makeText(this, "Digite um número", Toast.LENGTH_SHORT).show()
            return
        }

        val guess = guessComponent.text.toString().toInt()
        var tip:String = "O número sorteado é maior!"

        if(sortedNumber == guess) {
            tip = "Parabéns!!\nvocê acertou em $tries tentativas!"

            view.isEnabled = false
            view.setBackgroundColor(Color.GRAY)

            resetButton.isEnabled = true
            resetButton.setBackgroundColor(Color.parseColor("#E91E63"))

        }else if(sortedNumber < guess) {
            tip = "O número sorteado é menor!"
        }

        tipComponent.text = tip
        guessComponent.text = null
        tries++
    }

    fun reset(view: View) {
        val resetButton = view
        val guessButton = findViewById<Button>(R.id.button2)
        val tipComponent = findViewById<TextView>(R.id.textViewOutput)
        val guessComponent =findViewById<EditText>(R.id.editTextInput)

        sortedNumber = (1..100).random()
        tries = 0

        resetButton.isEnabled = false
        resetButton.setBackgroundColor(Color.GRAY)

        guessButton.isEnabled = true
        guessButton.setBackgroundColor(Color.parseColor("#E91E63"))

        tipComponent.text = ""
        guessComponent.setText("")
    }
}