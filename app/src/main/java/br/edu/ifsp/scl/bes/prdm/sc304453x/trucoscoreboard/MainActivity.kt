package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboard

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val target = 12

    private var usScore = 0
    private var themScore = 0

    private var roundValue = RoundValue.ONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)


        updateRoundValue()

        activityMainBinding.usPointBt.setOnClickListener {
            if (gameFinished()) return@setOnClickListener

            usScore = addPoints(usScore)
            updateScore(activityMainBinding.usPointsTv, usScore)
            checkWinner()
        }

        activityMainBinding.themPointBt.setOnClickListener {
            if (gameFinished()) return@setOnClickListener

            themScore = addPoints(themScore)
            updateScore(activityMainBinding.themPointsTv, themScore)
            checkWinner()
        }

        activityMainBinding.trucoBt.setOnClickListener {
            if (gameFinished()) return@setOnClickListener

            roundValue = roundValue.next()
            updateRoundValue()
        }
    }

    private fun addPoints(currentScore: Int): Int {
        val newScore = currentScore + roundValue.points
        roundValue = RoundValue.ONE
        updateRoundValue()
        return newScore
    }
    private fun updateScore(textView: TextView, score: Int) {
        textView.text = score.toString()
    }

    private fun updateRoundValue() {
        activityMainBinding.roundValueTv.text = buildString {
            append("Valendo: ")
            append(roundValue.points)
            append(" pontos")
        }
    }

    private fun gameFinished(): Boolean {
        return usScore >= target || themScore >= target
    }

    private fun disableGameButtons() {
        activityMainBinding.usPointBt.isEnabled = false
        activityMainBinding.themPointBt.isEnabled = false
        activityMainBinding.trucoBt.isEnabled = false
    }

    private fun checkWinner() {
        when {
            usScore >= target -> {
                activityMainBinding.roundValueTv.text = "Nós vencemos!"
                disableGameButtons()
            }
            themScore >= target -> {
                activityMainBinding.roundValueTv.text = "Eles venceram!"
                disableGameButtons()
            }
        }
    }
}