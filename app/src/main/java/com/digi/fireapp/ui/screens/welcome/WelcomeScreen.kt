package com.digi.fireapp.ui.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.digi.fireapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun WelcomeScreen(
    state: WelcomeState,
    onNavigationToLogin: () -> Unit = {},
    onNavigationToHome: () -> Unit = {},
) {
    val hamsterComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.hamster)
    )

    val scope = rememberCoroutineScope()
    var isLogoVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        scope.launch {
            delay(300)
            isLogoVisible = true
        }
    }
    LaunchedEffect(key1 = true) {
        scope.launch {
            delay(3000)
            when (state.authStatus) {
                AuthStatus.AUTHENTICATED -> onNavigationToHome()
                AuthStatus.UNAUTHENTICATED -> onNavigationToLogin()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onPrimary,
                    )
                )
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isLogoVisible,
                enter = slideInVertically { fullHeight -> fullHeight },
                modifier = Modifier.size(300.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 5.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.offset { IntOffset(0, 50) },
                    )
                    LottieAnimation(
                        composition = hamsterComposition,
                        modifier = Modifier.size(250.dp),
                        iterations = 5,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomeScreenPreview() {
    WelcomeScreen(
        state = WelcomeState()
    )
}
