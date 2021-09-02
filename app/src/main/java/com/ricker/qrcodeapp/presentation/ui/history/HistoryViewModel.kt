package com.ricker.qrcodeapp.presentation.ui.history

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.interactors.history.GetHistory
import com.ricker.qrcodeapp.interactors.history.InsertHistory
import com.ricker.qrcodeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

}