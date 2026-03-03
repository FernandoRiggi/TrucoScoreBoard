package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboard

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        updateUI()

        activityMainBinding.usPointBt.setOnClickListener {

            if (game.state == GameState.HAND_OF_ELEVEN) {
                showHandOfElevenDialog()
                return@setOnClickListener
            }

            game.addPoint(Team.US)
            updateUI()
        }

        activityMainBinding.themPointBt.setOnClickListener {

            if (game.state == GameState.HAND_OF_ELEVEN) {
                showHandOfElevenDialog()
                return@setOnClickListener
            }

            game.addPoint(Team.THEM)
            updateUI()
        }

        activityMainBinding.trucoBt.setOnClickListener {
            game.callTruco()
            updateUI()
        }

        activityMainBinding.resetBt.setOnClickListener {
            game.resetGame()
            enableGameButtons()
            updateUI()
        }
    }

    private fun updateUI() {
        activityMainBinding.usPointsTv.text = game.usScore.toString()
        activityMainBinding.themPointsTv.text = game.themScore.toString()
        activityMainBinding.usPointBt.text = "+${game.roundValue.points}"
        activityMainBinding.themPointBt.text = "+${game.roundValue.points}"

        activityMainBinding.roundValueTv.text =
            "Valendo: ${game.roundValue.points} pontos"

        if (game.state == GameState.FINISHED) {
            disableGameButtons()
        }

        if (game.state == GameState.HAND_OF_ELEVEN) {
            showHandOfElevenDialog()
            activityMainBinding.trucoBt.isEnabled = false
        }
    }

    private fun disableGameButtons() {
        activityMainBinding.usPointBt.isEnabled = false
        activityMainBinding.themPointBt.isEnabled = false
        activityMainBinding.trucoBt.isEnabled = false
    }

    private fun enableGameButtons() {
        activityMainBinding.usPointBt.isEnabled = true
        activityMainBinding.themPointBt.isEnabled = true
        activityMainBinding.trucoBt.isEnabled = true
    }
    private fun showHandOfElevenDialog() {
        AlertDialog.Builder(this)
            .setTitle("Mão de 11")
            .setMessage("Deseja jogar ou correr?")
            .setCancelable(false)
            .setPositiveButton("Jogar") { _, _ ->
                game.resolveHandOfEleven(true)
                updateUI()
            }
            .setNegativeButton("Correr") { _, _ ->
                game.resolveHandOfEleven(false)
                updateUI()
            }
            .show()
    }
}