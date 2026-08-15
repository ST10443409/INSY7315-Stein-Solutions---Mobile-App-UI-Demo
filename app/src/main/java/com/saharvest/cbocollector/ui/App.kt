package com.saharvest.cbocollector.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saharvest.cbocollector.data.ADMIN_BOTTOM_NAV
import com.saharvest.cbocollector.data.ADMIN_SCREENS_WITH_BOTTOM_NAV
import com.saharvest.cbocollector.data.BOTTOM_NAV
import com.saharvest.cbocollector.data.SCREENS_WITH_BOTTOM_NAV
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.data.VO_BOTTOM_NAV
import com.saharvest.cbocollector.data.VO_SCREENS_WITH_BOTTOM_NAV
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.BottomNavBar
import com.saharvest.cbocollector.ui.screens.AddProductSheet
import com.saharvest.cbocollector.ui.screens.AdminApprovalsScreen
import com.saharvest.cbocollector.ui.screens.AdminDetailScreen
import com.saharvest.cbocollector.ui.screens.AdminDoneScreen
import com.saharvest.cbocollector.ui.screens.AdminHomeScreen
import com.saharvest.cbocollector.ui.screens.AdminLoginScreen
import com.saharvest.cbocollector.ui.screens.AdminRegisterScreen
import com.saharvest.cbocollector.ui.screens.AdminReportsScreen
import com.saharvest.cbocollector.ui.screens.AdminTeamScreen
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
import com.saharvest.cbocollector.ui.screens.VoDoneScreen
import com.saharvest.cbocollector.ui.screens.VoFormScreen
import com.saharvest.cbocollector.ui.screens.VoHomeScreen
import com.saharvest.cbocollector.ui.screens.VoLoginScreen
import com.saharvest.cbocollector.ui.screens.VoPhotosScreen
import com.saharvest.cbocollector.ui.screens.VoReviewScreen
import com.saharvest.cbocollector.ui.screens.VoSyncScreen
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
    Screen.VoLogin -> Screen.RoleSelection
    Screen.VoHome -> null
    Screen.VoForm -> Screen.VoHome
    Screen.VoReview -> Screen.VoForm
    Screen.VoPhotos -> Screen.VoForm
    Screen.VoSync -> Screen.VoHome
    Screen.VoDone -> Screen.VoHome
    Screen.AdminLogin -> Screen.RoleSelection
    Screen.AdminHome -> null
    Screen.AdminApprovals -> Screen.AdminHome
    Screen.AdminDetail -> Screen.AdminApprovals
    Screen.AdminDone -> Screen.AdminHome
    Screen.AdminRegister -> Screen.AdminHome
    Screen.AdminTeam -> Screen.AdminHome
    Screen.AdminReports -> Screen.AdminHome
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
                        Screen.VoLogin -> VoLoginScreen(state)
                        Screen.VoHome -> VoHomeScreen(state)
                        Screen.VoForm -> VoFormScreen(state)
                        Screen.VoReview -> VoReviewScreen(state)
                        Screen.VoPhotos -> VoPhotosScreen(state)
                        Screen.VoSync -> VoSyncScreen(state)
                        Screen.VoDone -> VoDoneScreen(state)
                        Screen.AdminLogin -> AdminLoginScreen(state)
                        Screen.AdminHome -> AdminHomeScreen(state)
                        Screen.AdminApprovals -> AdminApprovalsScreen(state)
                        Screen.AdminDetail -> AdminDetailScreen(state)
                        Screen.AdminDone -> AdminDoneScreen(state)
                        Screen.AdminRegister -> AdminRegisterScreen(state)
                        Screen.AdminTeam -> AdminTeamScreen()
                        Screen.AdminReports -> AdminReportsScreen(state)
                    }
                }
                if (state.screen in SCREENS_WITH_BOTTOM_NAV) {
                    BottomNavBar(items = BOTTOM_NAV, current = state.screen, onNavigate = { state.go(it) })
                } else if (state.screen in VO_SCREENS_WITH_BOTTOM_NAV) {
                    BottomNavBar(items = VO_BOTTOM_NAV, current = state.screen, onNavigate = { state.go(it) })
                } else if (state.screen in ADMIN_SCREENS_WITH_BOTTOM_NAV) {
                    BottomNavBar(items = ADMIN_BOTTOM_NAV, current = state.screen, onNavigate = { state.go(it) })
                }
            }

            if (state.screen == Screen.Collect && state.addOpen) {
                AddProductSheet(state)
            }
        }
    }
}
