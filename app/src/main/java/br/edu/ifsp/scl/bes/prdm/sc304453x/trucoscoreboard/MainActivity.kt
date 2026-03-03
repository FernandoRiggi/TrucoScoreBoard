package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboard

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val target = 12

    private var usScore = 0
    private var themScore = 0

    private var roundValue = RoundValue.ONE
    private var gameState = GameState.NORMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        updateRoundValue()

        activityMainBinding.usPointBt.setOnClickListener {
            if (gameFinished()) return@setOnClickListener

            usScore = addPoints(usScore)
            updateScore(activityMainBinding.usPointsTv, usScore)

            checkWinner()
            checkHandOfEleven()
        }

        activityMainBinding.themPointBt.setOnClickListener {
            if (gameFinished()) return@setOnClickListener

            themScore = addPoints(themScore)
            updateScore(activityMainBinding.themPointsTv, themScore)

            checkWinner()
            checkHandOfEleven()
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

    private fun checkHandOfEleven() {
        if (gameFinished()) return

        if (usScore == 11 && themScore < 11) {
            gameState = GameState.HAND_OF_ELEVEN
            showHandOfElevenDialog(isUs = true)
        }

        if (themScore == 11 && usScore < 11) {
            gameState = GameState.HAND_OF_ELEVEN
            showHandOfElevenDialog(isUs = false)
        }
    }

    private fun showHandOfElevenDialog(isUs: Boolean) {
        AlertDialog.Builder(this)
            .setTitle("Mão de 11")
            .setMessage("Deseja jogar ou correr?")
            .setCancelable(false)
            .setPositiveButton("Jogar") { _, _ ->
                resolveHandOfEleven(play = true)
            }
            .setNegativeButton("Correr") { _, _ ->
                resolveHandOfEleven(play = false)
            }
            .show()
    }

    private fun resolveHandOfEleven(play: Boolean) {
        if (gameState != GameState.HAND_OF_ELEVEN) return

        if (play) {
            roundValue = RoundValue.THREE
            updateRoundValue()
        } else {
            if (usScore == 11) {
                themScore += 1
                updateScore(activityMainBinding.themPointsTv, themScore)
            } else {
                usScore += 1
                updateScore(activityMainBinding.usPointsTv, usScore)
            }

            checkWinner()
            checkHandOfEleven()
        }

        gameState = GameState.HAND_OF_ELEVEN
    }
}