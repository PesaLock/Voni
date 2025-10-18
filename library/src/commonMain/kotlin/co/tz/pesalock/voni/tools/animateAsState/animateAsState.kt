package co.tz.pesalock.voni.tools.animateAsState

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import kotlin.math.max

@Composable
fun <T, V : AnimationVector> animateAsState(
    targetValue: T,
    typeConverter: TwoWayConverter<T, V>,
    animationSpec: AnimationSpec<T> = tween(),
    visibilityThreshold: T? = null,
    finishedListener: ((T) -> Unit)? = null
): State<T> {
    val animatable = remember { Animatable(targetValue, typeConverter, visibilityThreshold) }

    LaunchedEffect(targetValue) {
        animatable.animateTo(targetValue, animationSpec)
        finishedListener?.invoke(targetValue)
    }

    return animatable.asState()
}

@Composable
fun animateFloatAsState(
    targetValue: Float,
    animationSpec: AnimationSpec<Float> = tween(),
    visibilityThreshold: Float = 0.01f,
    finishedListener: ((Float) -> Unit)? = null
): State<Float> = animateAsState(
    targetValue = targetValue,
    typeConverter = Float.VectorConverter,
    animationSpec = animationSpec,
    visibilityThreshold = visibilityThreshold,
    finishedListener = finishedListener
)

@Composable
fun animateIntAsState(
    targetValue: Int,
    animationSpec: AnimationSpec<Int> = tween(),
    visibilityThreshold: Int = 1,
    finishedListener: ((Int) -> Unit)? = null
): State<Int> = animateAsState(
    targetValue = targetValue,
    typeConverter = Int.VectorConverter,
    animationSpec = animationSpec,
    visibilityThreshold = visibilityThreshold,
    finishedListener = finishedListener
)


@Composable
fun animateDpAsState(
    targetValue: Dp,
    animationSpec: AnimationSpec<Dp> = tween(),
    visibilityThreshold: Dp = Dp(0.1f),
    finishedListener: ((Dp) -> Unit)? = null
): State<Dp> = animateAsState(
    targetValue = targetValue,
    typeConverter = Dp.VectorConverter,
    animationSpec = animationSpec,
    visibilityThreshold = visibilityThreshold,
    finishedListener = finishedListener
)

fun <T> AnimationSpec<T>.withSteps(steps: Int): AnimationSpec<T> {
    val duration = max(1, steps * 6)
    @Suppress("UNCHECKED_CAST")
    return tween<T>(durationMillis = duration) as AnimationSpec<T>
}