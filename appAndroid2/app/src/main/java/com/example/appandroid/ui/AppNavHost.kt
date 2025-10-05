package com.example.appandroid.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appandroid.ui.screens.*
import com.example.appandroid.viewmodel.AppViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = modifier
    ) {
        // Экран приветствия
        composable("welcome") {
            WelcomeScreen(
                onContinue = {
                    navController.navigate("apps")
                    appViewModel.loadApps() // загружаем приложения при входе
                }
            )
        }

        // Список приложений
        composable("apps") {
            val appsState = appViewModel.apps.collectAsState()       // реактивно собираем список приложений
            val isLoading = appViewModel.isLoading.collectAsState()  // состояние загрузки
            val errorMessage = appViewModel.errorMessage.collectAsState() // ошибки

            AppListScreen(
                apps = appsState.value,
                onAppClick = { app ->
                    navController.navigate("appDetail/${app.id}")
                },
                onCategoryClick = {
                    navController.navigate("categories")
                },
                isLoading = isLoading.value,
                errorMessage = errorMessage.value
            )
        }

        // Список категорий (пока что моки)
        composable("categories") {
            CategoryListScreen(
                categories = listOf(
                    com.example.appandroid.model.Categories(1, "Аркады"),
                    com.example.appandroid.model.Categories(2, "Финансы"),
                    com.example.appandroid.model.Categories(3, "Игры")
                ),
                onCategoryClick = { id ->
                    navController.navigate("apps") // фильтрацию можно добавить позже
                }
            )
        }

        // Детали приложения
        composable("appDetail/{appId}") { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId")?.toIntOrNull()
            val appsState = appViewModel.apps.collectAsState() // реактивно собираем список приложений
            val app = appsState.value.find { it.id == appId }

            if (app != null) {
                AppDetailScreen(
                    app = app,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

