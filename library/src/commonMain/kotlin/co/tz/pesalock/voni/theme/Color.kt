package co.tz.pesalock.voni.theme

import androidx.compose.ui.graphics.Color

object LightColors {
    val heavyBlue = Color(0xFF273E4C)
    val heavyWhite = Color(0xFFE4E4E4)
    val heavyGreen = Color(0xFF158B2A)

    val lighterBlue = heavyBlue.copy(alpha = 0.7f)
    val lighterWhite = heavyWhite.copy(alpha = 0.7f)
    val lighterGreen = heavyGreen.copy(alpha = 0.7f)
}

object DarkColors {
    val heavyWhite = Color(0xFFE1E1E1)
    val heavyDark = Color(0xFF020403)
    val heavyGreen = Color(0xFF4CAF50)

    val lighterWhite = Color(0xFFB8B8B8)
    val lighterDark = Color(0xFF2A2A2A)
    val lighterGreen = Color(0xFF4CAF50).copy(.25f)
}