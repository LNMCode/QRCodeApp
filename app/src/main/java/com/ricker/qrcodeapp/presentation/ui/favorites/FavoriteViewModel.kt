package com.ricker.qrcodeapp.presentation.ui.favorites

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.interactors.history.GetFavorite
import com.ricker.qrcodeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(
    private val getFavorite: GetFavorite,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    val favorites: MutableState<List<History>> = mutableStateOf(ArrayList())

    init {
        onTriggerEvent(FavoriteState.GetFavorite(true))
    }

    fun onTriggerEvent(event: FavoriteState){
        viewModelScope.launch {
            try {
                when(event){
                    is FavoriteState.GetFavorite -> {
                        getFavorite(event.isFavorite)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun getFavorite(isFavorite: Boolean) {
        favorites.value = getFavorite.getAll(isFavorite)
    }

}