package br.com.suacifra.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
//            syncUserData()
            _isLoading.value = false
        }
    }

    private fun syncUserData(): Boolean {
        if (isUserLogged())
            loadUserOnlineData()
        else
            loadUserOfflineData()
        TODO(
            "Iniciar a verificação de dados do usuário, para fazer a chamada das funções" +
                    " 'isUserLogged', que chamará por sua vez" +
                    " 'loadUserOnlineData' ou 'loadUserOfflineData' " +
                    "que dependerá da resposta de 'isUserLogged' "
        )
    }

    private fun isUserLogged(): Boolean {
        TODO("Verificar se o usuário está conectado")
    }

    private fun loadUserOnlineData(): Boolean {
        TODO("Dentro de 'isUserLogged', caso seja verdade, devo carregar os dados online")
    }

    private fun loadUserOfflineData(): Boolean {
        TODO("Dentro de 'isUserLogged', caso seja falso, devo carregar os dados offline")
    }
}