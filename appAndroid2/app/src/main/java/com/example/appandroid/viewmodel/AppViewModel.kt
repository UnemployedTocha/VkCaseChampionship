package com.example.appandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appandroid.api.createApiService
import com.example.appandroid.model.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    // Приватное состояние списка приложений
    private val _apps = MutableStateFlow<List<App>>(emptyList())
    // Публичное состояние для подписки в Composable
    val apps: StateFlow<List<App>> = _apps

    // Состояние загрузки (опционально)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Состояние ошибок (опционально)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Экземпляр ApiService
    private val api = createApiService()

    // Функция для загрузки данных с бэка
    fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = api.getApps()
                _apps.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки данных: ${e.message}"
                _apps.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
