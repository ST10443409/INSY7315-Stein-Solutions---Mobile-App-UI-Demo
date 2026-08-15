package com.saharvest.cbocollector.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saharvest.cbocollector.data.BOTTOM_NAV
import com.saharvest.cbocollector.data.SCREENS_WITH_BOTTOM_NAV
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.BottomNavBar
import com.saharvest.cbocollector.ui.screens.AddProductSheet
import com.saharvest.cbocollector.ui.screens.CbosScreen
import com.saharvest.cbocollector.ui.screens.CollectScreen
import com.saharvest.cbocollector.ui.screens.DoneScreen
import com.saharvest.cbocollector.ui.screens.HistoryScreen
import com.saharvest.cbocollector.ui.screens.HomeScreen
import com.saharvest.cbocollector.ui.screens.LoginScreen
import com.saharvest.cbocollector.ui.screens.PhotosScreen
import com.saharvest.cbocollector.ui.screens.ReviewScreen
import com.saharvest.cbocollector.ui.screens.RoleSelectionScreen
import com.saharvest.cbocollector.ui.screens.SignScreen
import com.saharvest.cbocollector.ui.screens.SplashScreen
import com.saharvest.cbocollector.ui.screens.SyncScreen
import com.saharvest.cbocollector.ui.screens.VettingScreen
import com.saharvest.cbocollector.ui.theme.CBOCollectorTheme

private fun backTargetFor(screen: Screen): Screen? = when (screen) {
    Screen.RoleSelection -> Screen.Splash
    Screen.Login -> Screen.RoleSelection
    Screen.Cbos -> Screen.Home
    Screen.Vetting -> Screen.Cbos
    Screen.Review -> Screen.Vetting
    Screen.Collect -> Screen.Home
    Screen.Sign -> Screen.Collect
    Screen.Photos -> Screen.Collect
    Screen.Sync -> Screen.Home
    Screen.History -> Screen.Home
    Screen.Done -> Screen.Home
    Screen.Home -> null
    Screen.Splash -> null
}

@Composable
fun CBOCollectorApp(state: AppState = viewModel()) {
    CBOCollectorTheme {
        val backTarget = backTargetFor(state.screen)
        BackHandler(enabled = backTarget != null) {
            state.go(backTarget!!)
        }

        Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    when (state.screen) {
                        Screen.Splash -> SplashScreen(onContinue = { state.go(Screen.RoleSelection) })
                        Screen.RoleSelection -> RoleSelectionScreen(state)
                        Screen.Login -> LoginScreen(state)
                        Screen.Home -> HomeScreen(state)
                        Screen.Cbos -> CbosScreen(state)
                        Screen.Vetting -> VettingScreen(state)
                        Screen.Review -> ReviewScreen(state)
                        Screen.Collect -> CollectScreen(state)
                        Screen.Sign -> SignScreen(state)
                        Screen.Photos -> PhotosScreen(state)
                        Screen.Sync -> SyncScreen(state)
                        Screen.Done -> DoneScreen(state)
                        Screen.History -> HistoryScreen()
                    }
                }
                if (state.screen in SCREENS_WITH_BOTTOM_NAV) {
                    BottomNavBar(items = BOTTOM_NAV, current = state.screen, onNavigate = { state.go(it) })
                }
            }

            if (state.screen == Screen.Collect && state.addOpen) {
                AddProductSheet(state)
            }
        }
    }
}
