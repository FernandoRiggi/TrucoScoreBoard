package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboard

enum class RoundValue(val points: Int) {
    ONE(1),
    THREE(3),
    SIX(6),
    NINE(9),
    TWELVE(12);

    fun next(): RoundValue {
        return when (this) {
            ONE -> THREE
            THREE -> SIX
            SIX -> NINE
            NINE -> TWELVE
            TWELVE -> TWELVE
        }
    }
}