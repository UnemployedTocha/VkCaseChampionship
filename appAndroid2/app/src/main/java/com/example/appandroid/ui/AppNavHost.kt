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
    // Подписываемся на состояния
    val appsState = appViewModel.apps.collectAsState()
    val categoriesState = appViewModel.categories.collectAsState()
    val isLoadingApps = appViewModel.isLoading.collectAsState()
    val errorMessageApps = appViewModel.errorMessage.collectAsState()
    val isLoadingCategories = appViewModel.isLoadingCategories.collectAsState()
    val errorMessageCategories = appViewModel.categoriesError.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = modifier
    ) {
        composable("welcome") {
            WelcomeScreen(
                onContinue = {
                    navController.navigate("apps")
                    appViewModel.loadApps()
                    appViewModel.loadCategories()
                }
            )
        }

        composable("apps") {
            AppListScreen(
                apps = appsState.value,
                categories = categoriesState.value,
                onAppClick = { app ->
                    navController.navigate("appDetail/${app.id}")
                },
                onCategoryClick = {
                    navController.navigate("categories")
                },
                isLoading = isLoadingApps.value || isLoadingCategories.value,
                errorMessage = errorMessageApps.value ?: errorMessageCategories.value
            )
        }

        composable("categories") {
            CategoryListScreen(
                categories = categoriesState.value,
                onCategoryClick = { categoryId ->
                    navController.navigate("apps") // можно позже фильтровать по categoryId
                }
            )
        }

        composable("appDetail/{appId}") { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId")?.toIntOrNull()
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



