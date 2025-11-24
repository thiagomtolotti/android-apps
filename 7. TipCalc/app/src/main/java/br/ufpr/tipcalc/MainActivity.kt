package br.ufpr.tipcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var currentBillTotal: Double = 0.0
    var currentCustomPercent: Int = 18

    lateinit var tip10EditText: EditText
    lateinit var tip15EditText: EditText
    lateinit var tip20EditText: EditText

    lateinit var total10EditText: EditText
    lateinit var total15EditText: EditText
    lateinit var total20EditText: EditText

    lateinit var billEditText: EditText

    lateinit var tipCustomEditText: EditText
    lateinit var totalCustomEditText: EditText

    lateinit var customTipTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tableLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tip10EditText = findViewById<EditText>(R.id.tip10EditText)
        tip15EditText = findViewById<EditText>(R.id.tip15EditText)
        tip20EditText = findViewById<EditText>(R.id.tip20EditText)

        total10EditText = findViewById<EditText>(R.id.total10EditText)
        total15EditText = findViewById<EditText>(R.id.total15EditText)
        total20EditText = findViewById<EditText>(R.id.total20EditText)

        billEditText = findViewById<EditText>(R.id.billTotal)

        tipCustomEditText = findViewById<EditText>(R.id.tipCustomEditText)
        totalCustomEditText = findViewById<EditText>(R.id.totalCustomEditText)

        customTipTextView = findViewById<TextView>(R.id.customTipTextView)

        val customSeekBar = findViewById<SeekBar>(R.id.customTipSeekBar)
        currentCustomPercent = customSeekBar.progress

        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener)
        billEditText.addTextChangedListener(billTextWatcher)
    }

    fun updateStandard() {
        val tip10 = (currentBillTotal * 0.1)
        val tip15 = (currentBillTotal * 0.15)
        val tip20 = (currentBillTotal * 0.2)

        tip10EditText.setText(String.format("%.2f",tip10))
        tip15EditText.setText(String.format("%.2f",tip15))
        tip20EditText.setText(String.format("%.2f",tip20))

        total10EditText.setText(String.format("%.2f", currentBillTotal + tip10))
        total15EditText.setText(String.format("%.2f", currentBillTotal + tip15))
        total20EditText.setText(String.format("%.2f", currentBillTotal + tip20))
    }

    fun updateCustom() {
        val customTip =  currentBillTotal * currentCustomPercent / 100

        customTipTextView.text = ("$currentCustomPercent %")

        tipCustomEditText.setText(String.format("%.2f", customTip))
        totalCustomEditText.setText(String.format("%.2f", currentBillTotal + customTip))
    }

    private val customSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar?,
            progress: Int,
            fromUser: Boolean
        ) {
            currentCustomPercent = progress

            updateCustom()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }

    private val billTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {}

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            try {
                currentBillTotal = s.toString().toDouble()
             }catch (e: NumberFormatException){
                currentBillTotal = 0.00
             }

            updateStandard()
            updateCustom()
        }

    }
}