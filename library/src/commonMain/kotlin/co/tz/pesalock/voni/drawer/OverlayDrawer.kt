package co.tz.pesalock.voni.drawer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun OverlayDrawer(
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    scrimOpacity: Float = .6f,
    animationDurationMs: Int = 300,
    enableGestures: Boolean = true,
    drawerBackground: Color = Color.White,
    drawerShape: RoundedCornerShape = when (drawerState.direction) {
        is Left -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
        is Right -> RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        is Top -> RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
        is Bottom -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    },
    content: @Composable () -> Unit
) {
    val navigationBarPadding = WindowInsets.systemBars
        .asPaddingValues()
        .calculateBottomPadding()
    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var dragOffset by remember { mutableStateOf(0f) }

    LaunchedEffect(drawerState.isOpen, drawerState.direction) {
        dragOffset = 0f
    }

    val drawerSizePx = with(density) { drawerState.size.toDp().toPx() }
    val targetOffset = if (drawerState.isOpen) 0f else when (drawerState.direction) {
        is Left -> 0f - drawerSizePx
        is Right -> drawerSizePx
        is Top -> 0f - drawerSizePx
        is Bottom -> drawerSizePx
    }

    val animatedOffset by animateFloatAsState(
        targetValue = targetOffset + dragOffset,
        animationSpec = tween(animationDurationMs),
        label = "drawer_offset"
    )

    val scrimAlpha by animateFloatAsState(
        targetValue = if (drawerState.isOpen) scrimOpacity else 0f,
        animationSpec = tween(animationDurationMs),
        label = "scrim_alpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { containerSize = it }
    ) {
        // Main content
        Box(
            modifier = Modifier
                .padding(bottom = navigationBarPadding)
                .fillMaxSize()
        ) {
            content()
        }

        // Scrim overlay
        if (scrimAlpha > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = scrimAlpha))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { drawerState.close() }
            )
        }

        // Drawer content
        Box(
            modifier = Modifier
                .then(
                    when (drawerState.direction) {
                        is Left -> Modifier
                            .padding(bottom = navigationBarPadding)
                            .width(with(density) { drawerState.size.toDp() })
                            .fillMaxHeight()
                            .offset { IntOffset(animatedOffset.roundToInt(), 0) }

                        is Right -> Modifier
                            .padding(bottom = navigationBarPadding)
                            .width(with(density) { drawerState.size.toDp() })
                            .fillMaxHeight()
                            .offset {
                                IntOffset(
                                    (containerSize.width - drawerSizePx + animatedOffset).roundToInt(),
                                    0
                                )
                            }

                        is Top -> Modifier
                            .height(with(density) { drawerState.size.toDp() })
                            .fillMaxWidth()
                            .offset { IntOffset(0, animatedOffset.roundToInt()) }

                        is Bottom -> Modifier
                            .height(with(density) { drawerState.size.toDp() })
                            .fillMaxWidth()
                            .offset {
                                IntOffset(
                                    0,
                                    (containerSize.height - drawerSizePx + animatedOffset).roundToInt()
                                )
                            }

                    }
                )
                .shadow(8.dp)
                .background(drawerBackground, drawerShape)
                .let { drawerModifier ->
                    if (enableGestures) {
                        drawerModifier.pointerInput(drawerState.direction) {
                            detectDragGestures(
                                onDragEnd = {
                                    val threshold = drawerSizePx * 0.3f
                                    val shouldClose = when (drawerState.direction) {
                                        is Left -> dragOffset < -threshold
                                        is Right -> dragOffset > threshold
                                        is Top -> dragOffset < -threshold
                                        is Bottom -> dragOffset > threshold
                                    }
                                    if (shouldClose) drawerState.close() else drawerState.open()
                                    dragOffset = 0f
                                }
                            ) { _, dragAmount ->
                                dragOffset = when (drawerState.direction) {
                                    is Left -> (dragOffset + dragAmount.x).coerceAtMost(0f)
                                    is Right -> (dragOffset + dragAmount.x).coerceAtLeast(0f)
                                    is Top -> (dragOffset + dragAmount.y).coerceAtMost(0f)
                                    is Bottom -> (dragOffset + dragAmount.y).coerceAtLeast(0f)
                                }
                            }
                        }
                    } else drawerModifier
                }
        ) {
            drawerContent()
        }
    }
}