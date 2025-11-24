package br.ufpr.churrascometro

import android.os.Bundle
import android.widget.SeekBar
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

        val manSeekBar = findViewById<SeekBar>(R.id.manSeekBar)
        val manQuantity = findViewById<TextView>(R.id.manValue)

        val womanSeekBar = findViewById<SeekBar>(R.id.womanSeekBar)
        val womanQuantity = findViewById<TextView>(R.id.womanValue)

        manSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                manQuantity.text = progress.toString()
                calculate(progress, womanSeekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        womanSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                womanQuantity.text = progress.toString()
                calculate(manSeekBar.progress, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    fun calculate(manQuantity: Int, womanQuantity:Int){
        val meatQuantityComponent = findViewById<TextView>(R.id.carneValue)
        val sausageQuantityComponent = findViewById<TextView>(R.id.linguicaValue)

        val meatQuantity = manQuantity * .25 + womanQuantity * .15
        val sausageQuantity = manQuantity * .45 + womanQuantity * .3

        meatQuantityComponent.text = "${String.format("%.2f", meatQuantity)} kg"
        sausageQuantityComponent.text = "${String.format("%.2f", sausageQuantity)} kg"
    }
}