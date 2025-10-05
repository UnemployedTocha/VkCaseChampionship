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
            val appsState =
                appViewModel.apps.collectAsState()       // реактивно собираем список приложений
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
            val categoriesState = appViewModel.categories.collectAsState()
            val isLoading = appViewModel.isLoadingCategories.collectAsState()
            val errorMessage = appViewModel.categoriesError.collectAsState()

            // Запускаем загрузку, если список пуст
            if (categoriesState.value.isEmpty() && !isLoading.value) {
                appViewModel.loadCategories()
            }

            CategoryListScreen(
                categories = categoriesState.value,
                onCategoryClick = { categoryId ->
                    // Например, можно фильтровать приложения по categoryId
                    navController.navigate("apps")
                },
                isLoading = isLoading.value,
                errorMessage = errorMessage.value
            )
        }


        composable("appDetail/{appId}") { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId")?.toIntOrNull()
            if (appId != null) {
                val appsState = appViewModel.apps.collectAsState()
                val app = appsState.value.find { app -> app.id == appId }
                if (app != null) {
                    AppDetailScreen(
                        app = app,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}


