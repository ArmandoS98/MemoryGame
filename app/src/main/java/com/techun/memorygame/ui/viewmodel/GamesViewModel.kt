package com.techun.memorygame.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.domain.usecases.games.GetGamesUseCase
import com.techun.memorygame.domain.usecases.games.SaveGameUseCase
import com.techun.memorygame.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getAllGamesUseCase: GetGamesUseCase,
    private val saveGameUseCase: SaveGameUseCase
) : ViewModel() {
    private val _getGamesState: MutableLiveData<DataState<List<GameModel>>> = MutableLiveData()
    val getGameState: LiveData<DataState<List<GameModel>>>
        get() = _getGamesState

    private val _saveGamesState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val saveGameState: LiveData<DataState<Boolean>>
        get() = _saveGamesState

    fun getAllGames() = viewModelScope.launch {
        getAllGamesUseCase().onEach { dataStates ->
            _getGamesState.value = dataStates
        }.launchIn(viewModelScope)
    }

    fun saveGamed(game: GameModel) = viewModelScope.launch {
        saveGameUseCase(game).onEach { dataStates ->
            _saveGamesState.value = dataStates
        }.launchIn(viewModelScope)
    }
}