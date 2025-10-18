// ScreenOrientation.kt
package co.tz.pesalock.voni.orientation

sealed interface ScreenOrientation

data object Portrait : ScreenOrientation

data object Landscape : ScreenOrientation