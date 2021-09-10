package com.ricker.qrcodeapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.google.zxing.integration.android.IntentIntegrator
import com.ricker.qrcodeapp.datastore.SettingsDataStore
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.presentation.navigation.Screen
import com.ricker.qrcodeapp.presentation.ui.favorites.FavoriteScreen
import com.ricker.qrcodeapp.presentation.ui.favorites.FavoriteViewModel
import com.ricker.qrcodeapp.presentation.ui.qrdetail.QRDetailScreen
import com.ricker.qrcodeapp.presentation.ui.qrdetail.QRDetailViewModel
import com.ricker.qrcodeapp.presentation.ui.qrmain.QRMainScreen
import com.ricker.qrcodeapp.presentation.ui.qrmain.QRMainState
import com.ricker.qrcodeapp.presentation.ui.qrmain.QRMainViewModel
import com.ricker.qrcodeapp.presentation.util.ConnectivityManager
import com.ricker.qrcodeapp.presentation.util.CreateIdByDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    private lateinit var viewModel: QRMainViewModel

    private lateinit var navController: NavHostController

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.QRMain.route) {
                composable(route = Screen.QRMain.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    viewModel = viewModel("QRMainViewModel", factory)
                    QRMainScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onNavigateToScreen = navController::navigate,
                        viewModel = viewModel,
                        activity = this@MainActivity,
                    )
                }
                composable(
                    route = Screen.QRDetail.route + "/{historyId}",
                    arguments = listOf(navArgument("historyId") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: QRDetailViewModel = viewModel("QRDetailViewModel", factory)
                    QRDetailScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onNavigateToScreen = navController::navigate,
                        historyId = navBackStackEntry.arguments?.getString("historyId"),
                        viewModel = viewModel,
                        activity = this@MainActivity,
                    )
                }
                composable(route = Screen.Favorites.route){ navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: FavoriteViewModel = viewModel("FavoriteViewModel", factory)
                    FavoriteScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onNavigateToScreen = navController::navigate,
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        result?.let { insertHistory(result.contents) }
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    private fun insertHistory(value: String?) {
        if (value != null) {
            val id = CreateIdByDate.create()
            val model = History(
                id = id,
                value = value,
                isFavorite = false,
                scannedDay = "Haha",
            )
            viewModel.onTriggerEvent(QRMainState.InsertHistoryItem(model))
            navController.navigate(Screen.QRDetail.route + "/$id")
        }
    }
}