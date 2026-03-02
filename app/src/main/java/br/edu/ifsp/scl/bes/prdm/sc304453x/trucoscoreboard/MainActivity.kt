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

    private var roundValue = RoundValue.ONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        activityMainBinding.usPointBt.setOnClickListener {
            if (usScore < target) {
                usScore = addPoints(usScore)
                updateScore(activityMainBinding.usPointsTv, usScore)
            }
        } }

    private fun addPoints(currentScore: Int): Int {
        return currentScore + roundValue.points
    }
    private fun updateScore(textView: TextView, score: Int) {
        textView.text = score.toString()
    }
}