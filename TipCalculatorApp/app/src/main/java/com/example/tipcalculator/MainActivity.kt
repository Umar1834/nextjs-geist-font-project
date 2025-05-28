package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.NumberFormatException
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextBase: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var textViewTipPercent: TextView
    private lateinit var textViewTipAmount: TextView
    private lateinit var textViewTotal: TextView

    private val decimalFormat = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        editTextBase = findViewById(R.id.editTextBase)
        seekBarTip = findViewById(R.id.seekBarTip)
        textViewTipPercent = findViewById(R.id.textViewTipPercent)
        textViewTipAmount = findViewById(R.id.textViewTipAmount)
        textViewTotal = findViewById(R.id.textViewTotal)

        // Set initial tip percent text
        textViewTipPercent.text = "${seekBarTip.progress}%"

        // SeekBar change listener
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewTipPercent.text = "$progress%"
                calculateAndDisplayTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No action needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No action needed
            }
        })

        // EditText change listener
        editTextBase.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateAndDisplayTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed
            }
        })
    }

    private fun calculateAndDisplayTotal() {
        val baseAmount = try {
            editTextBase.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }

        val tipPercent = seekBarTip.progress.toDouble()
        val tipAmount = baseAmount * tipPercent / 100.0
        val totalAmount = baseAmount + tipAmount

        textViewTipAmount.text = decimalFormat.format(tipAmount)
        textViewTotal.text = decimalFormat.format(totalAmount)
    }
}
