package com.ricker.qrcodeapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.ricker.qrcodeapp.presentation.ui.create.CreateScreen
import com.ricker.qrcodeapp.presentation.ui.create.CreateViewModel
import com.ricker.qrcodeapp.presentation.ui.favorites.FavoriteScreen
import com.ricker.qrcodeapp.presentation.ui.favorites.FavoriteViewModel
import com.ricker.qrcodeapp.presentation.ui.qrdetail.QRDetailScreen
import com.ricker.qrcodeapp.presentation.ui.qrdetail.QRDetailViewModel
import com.ricker.qrcodeapp.presentation.ui.qrmain.QRMainScreen
import com.ricker.qrcodeapp.presentation.ui.qrmain.QRMainState
import com.ricker.qrcodeapp.presentation.ui.qrmain.QRMainViewModel
import com.ricker.qrcodeapp.presentation.util.ConnectivityManager
import com.ricker.qrcodeapp.presentation.util.CreateIdByDate
import com.ricker.qrcodeapp.presentation.util.Image
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    private lateinit var viewModel: QRMainViewModel

    private lateinit var navController: NavHostController

    private val permissions = listOf("android.permission.WRITE_EXTERNAL_STORAGE")

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            navController.popBackStack()
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
                        onBackStack = {
                            navController.popBackStack(
                                navController.getBackStackEntry(Screen.QRMain.route).destination.id,
                                false
                            )
                        },
                        historyId = navBackStackEntry.arguments?.getString("historyId"),
                        viewModel = viewModel,
                        activity = this@MainActivity,
                    )
                }
                composable(route = Screen.Favorites.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: FavoriteViewModel = viewModel("FavoriteViewModel", factory)
                    FavoriteScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onNavigateToScreen = navController::navigate,
                        onBackStack = navController::popBackStack,
                        viewModel = viewModel,
                    )
                }
                composable(route = Screen.Create.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: CreateViewModel = viewModel("CreateViewModel", factory)
                    CreateScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        viewModel = viewModel,
                        onSaveImageBitmap = { bitmap ->
                            saveImageBitmap(bitmap, this@MainActivity)
                        },
                        onBackStack = navController::popBackStack
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted! Click button again to save", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed!!!", Toast.LENGTH_LONG).show()
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

    @SuppressLint("SimpleDateFormat")
    private fun insertHistory(value: String?) {
        if (value != null) {
            val id = CreateIdByDate.create()
            val dateScanned = SimpleDateFormat("dd/MM/yyyy").format(Date())
            val model = History(
                id = id,
                value = value,
                isFavorite = false,
                scannedDay = dateScanned,
            )
            viewModel.onTriggerEvent(QRMainState.InsertHistoryItem(model))
            navController.navigate(Screen.QRDetail.route + "/$id")
        }
    }

    private fun saveImageBitmap(bitmap: Bitmap, context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val outputFile = Image.saveImageFromBitmap(bitmap)
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(outputFile.toString()),
                    arrayOf(outputFile.name),
                    null
                )
                Toast.makeText(context, "Saved!!!", Toast.LENGTH_LONG).show()
            } else {
                requestPermissions(permissions.toTypedArray(),
                    REQUEST_PERMISSIONS_CODE
                )
            }
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 8977
    }
}