package co.tz.pesalock.voni.theme

/**
 * Represents the user's desired theme preference, which can be
 * an explicit choice (Light/Dark) or to follow the system setting.
 */
sealed interface AppTheme
object Light : AppTheme
object Dark : AppTheme
object System : AppTheme
