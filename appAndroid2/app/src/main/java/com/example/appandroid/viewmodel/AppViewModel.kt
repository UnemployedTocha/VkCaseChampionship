package com.example.appandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appandroid.api.createApiService
import com.example.appandroid.model.App
import com.example.appandroid.model.Categories
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
    private val _categories = MutableStateFlow<List<Categories>>(emptyList())
    val categories: StateFlow<List<Categories>> = _categories

    private val _isLoadingCategories = MutableStateFlow(false)
    val isLoadingCategories: StateFlow<Boolean> = _isLoadingCategories

    private val _categoriesError = MutableStateFlow<String?>(null)
    val categoriesError: StateFlow<String?> = _categoriesError

    fun loadCategories() {
        viewModelScope.launch {
            _isLoadingCategories.value = true
            _categoriesError.value = null
            try {
                _categories.value = api.getCategories()
            } catch (e: Exception) {
                _categoriesError.value = "Ошибка загрузки категорий: ${e.message}"
                _categories.value = emptyList()
            } finally {
                _isLoadingCategories.value = false
            }
        }
    }

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
