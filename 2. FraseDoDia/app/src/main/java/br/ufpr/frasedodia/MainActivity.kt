package br.ufpr.frasedodia

import android.os.Bundle
import android.view.View
import android.widget.TextView
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

    fun quote(view: View) {
        val quotes = arrayOf(
            "Emfrente os problemas e leve a melhor!",
            "Levanta, sacode a poeira e dá a volta por cima!",
            "Respire fundo e fique atento às surpresas que a vida prepara!",
            "Nem todos os dias são bons, mas há algo bom em cada dia.",
            "Respeite a sua mente e trate seu corpo com carinho.",
            "Um passo de cada vez. Até meio passo serve. Não há motivo para ter pressa.",
            "Para chegar ao topo, o que importa é começar!",
            "Para hoje, vou usar minha melhor roupa: a fé.",
            "O otimismo é a fé em ação. Nada se pode levar a efeito sem otimismo.",
            "Comece seu dia com um sorriso que venha lá de dentro da alma."
        )

        val index = (0..9).random()

        val quote = quotes[index]
        val textView = findViewById<TextView>(R.id.textViewOutput)

        textView.text = quote
    }
}