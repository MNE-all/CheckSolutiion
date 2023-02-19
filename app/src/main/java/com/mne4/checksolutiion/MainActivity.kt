package com.mne4.checksolutiion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.mne4.checksolutiion.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    var rightCount = 0
    var loseCount = 0
    var totalCount = 0
    var percents = 0
    var timeMin = 0f
    var timeMax = 0f
    var timeAvg = 0f

    var timeAnswers =  mutableListOf<Float>()

    var count = 0

    lateinit var timer: CountDownTimer

    private val signs = arrayOf("+", "-", "*", "/")
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonFalse.isEnabled = false
        binding.buttonRight.isEnabled = false



    }


    fun nextButtonClick(view: View) {
        timer = object : CountDownTimer(60000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                count++
            }
            override fun onFinish() {
            }
        }
        timer.start()


        totalCount += 1
        binding.buttonFalse.isEnabled = true
        binding.buttonRight.isEnabled = true

        binding.buttonNext.isEnabled = false

        randomize()


        if (arrayOf(true, false).random()){
            when (binding.textViewSign.text) {
                "+" -> {
                    binding.textViewAnswer.text = solution().toInt().toString()
                }
                "-" -> {
                    binding.textViewAnswer.text = solution().toInt().toString()
                }
                "*" -> {
                    binding.textViewAnswer.text = solution().toInt().toString()
                }
                "/" -> {
                    binding.textViewAnswer.text = solution().toString()
                }
            }
        }
        else {
            var result = 0f
            do {
                result = ((solution() - 50).toInt()until (solution() + 50).toInt()).random().toFloat()
            } while (result == solution())
            binding.textViewAnswer.text = result.toString()
        }
    }

    private fun randomize() {
        binding.textViewSign.text = signs.random()
        if (binding.textViewSign.text == "/") {
            val operand = (1..99).random()
            binding.textViewLeftOperand.text = (operand  * (1..99).random()).toString()
            binding.textViewRightOperand.text = operand.toString()
        }
        else {
            binding.textViewLeftOperand.text = (1..99).random().toString()
            binding.textViewRightOperand.text = (1..99).random().toString()
        }
    }


    private fun solution(): Float {
        var result = 0f
        when (binding.textViewSign.text) {
            "+" -> {
                result = (binding.textViewLeftOperand.text.toString().toInt() + binding.textViewRightOperand.text.toString().toInt()).toFloat()
            }
            "-" -> {
                result = (binding.textViewLeftOperand.text.toString().toInt() - binding.textViewRightOperand.text.toString().toInt()).toFloat()
            }
            "*" -> {
                result = (binding.textViewLeftOperand.text.toString().toInt() * binding.textViewRightOperand.text.toString().toInt()).toFloat()
            }
            "/" -> {
                result = (binding.textViewLeftOperand.text.toString().toFloat() / binding.textViewRightOperand.text.toString().toFloat())

            }
        }
        return result
    }

    fun rightButtonClick(view: View) {
        if (solution() == binding.textViewAnswer.text.toString().toFloat()){
            rightCount += 1
        }
        else {
            loseCount += 1
        }
        bindValues()
    }

    fun falseButtonClick(view: View) {
        if (solution() == binding.textViewAnswer.text.toString().toFloat()){
            loseCount += 1
        }
        else {
            rightCount += 1
        }
        bindValues()
    }


    private fun bindValues() {
        timer.cancel()
        val times = count / 1000f
        count = 0

        timeAnswers.add(times)

        timeMin = timeAnswers.min()
        timeMax = timeAnswers.max()
        timeAvg = timeAnswers.average().toFloat()




        binding.buttonFalse.isEnabled = false
        binding.buttonRight.isEnabled = false

        binding.buttonNext.isEnabled = true

        binding.textViewTotalValue.text = totalCount.toString()
        binding.textViewRightValue.text = rightCount.toString()
        binding.textViewLoseValue.text = loseCount.toString()

        binding.textViewPercents.text = "${String.format("%.2f", (100f / totalCount.toFloat() * rightCount.toFloat()))}%"

        binding.textViewMinValue.text = String.format("%.2f", timeMin)
        binding.textViewMaxValue.text = timeMax.toString()
        binding.textViewAvgValue.text = String.format("%.2f", timeAvg)

    }


}