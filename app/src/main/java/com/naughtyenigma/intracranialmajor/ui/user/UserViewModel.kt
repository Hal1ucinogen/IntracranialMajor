package com.naughtyenigma.intracranialmajor.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naughtyenigma.intracranialmajor.api.Result
import com.naughtyenigma.intracranialmajor.data.MatchRepository
import com.naughtyenigma.intracranialmajor.data.UserRepository
import com.naughtyenigma.intracranialmajor.model.Match
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val userRepository by lazy { UserRepository() }
    private val matchRepository by lazy { MatchRepository() }

    private val _state = MutableStateFlow(UserUiState())
    val state: StateFlow<UserUiState>
        get() = _state

    init {
        getUserPageInfo()
    }

    private fun getUserPageInfo() {
        viewModelScope.launch {
            val userRequest = async { userRepository.getUserInfo() }
            val matchesRequest = async { matchRepository.getMatchList() }
            val userRes = userRequest.await()
            val matchesRes = matchesRequest.await()
            if (userRes is Result.Success && matchesRes is Result.Success) {
                val user = userRes.data
                val matches = matchesRes.data.list
                _state.value = UserUiState(
                    user.nickName, user.avatarUrl, user.integral, user.rank,
                    matches.take(10)
                )
            }
        }
    }

    private fun getMatches() {
        viewModelScope.launch {
            val response = matchRepository.getMatchList()
            if (response is Result.Success) {
                _state.value = UserUiState(recentMatches = response.data.list)
            }
        }
    }
}

data class UserUiState(
    val name: String = "",
    val avatar: String = "",
    val score: Int = 0,
    val rank: Int = 0,
    val recentMatches: List<Match> = emptyList()
)