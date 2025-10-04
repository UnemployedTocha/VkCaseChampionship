package com.example.appandroid.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appandroid.model.App
import com.example.appandroid.model.Categories
import com.example.appandroid.model.Screenshot
import com.example.appandroid.ui.screens.*

@Composable
fun AppNavHost(navController: androidx.navigation.NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        // Экран приветствия
        composable("welcome") {
            WelcomeScreen(
                onContinue = { navController.navigate("apps") }
            )
        }

        // Список приложений
        composable("apps") {
            // временные данные-заглушки, потом подгрузим из ViewModel
            val mockApps = listOf(
                App(
                    id = 1,
                    name = "Max: общение",
                    icon_url = "",
                    short_description = "Ваш браузер…",
                    full_description = "Описание...",
                    category_id = 2,
                    developer = "Meow Game",
                    age_rating = "18+",
                    apk_url = "",
                    screenshots = listOf(Screenshot(1, ""))
                ),
                App(
                    id = 2,
                    name = "Max: браузер",
                    icon_url = "",
                    short_description = "Лучший браузер",
                    full_description = "Описание...",
                    category_id = 3,
                    developer = "Max Devs",
                    age_rating = "12+",
                    apk_url = "",
                    screenshots = emptyList()
                )
            )

            AppListScreen(
                apps = mockApps,
                onAppClick = { appId ->
                    navController.navigate("appDetail/$appId")
                },
                onCategoryClick = {
                    navController.navigate("categories")
                }
            )
        }

        // Экран деталей приложения
        composable(
            route = "appDetail/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.IntType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getInt("appId") ?: 0
            val mockApp = App(
                id = appId,
                name = "Приложение $appId",
                icon_url = "",
                short_description = "Краткое описание",
                full_description = "Длинное описание",
                category_id = 1,
                developer = "Dev Studio",
                age_rating = "16+",
                apk_url = "",
                screenshots = listOf(Screenshot(1, ""))
            )
            AppDetailScreen(app = mockApp)
        }

        // Список категорий
        composable("categories") {
            val mockCategories = listOf(
                Categories(1, "Аркады"),
                Categories(2, "Финансы"),
                Categories(3, "Игры")
            )
            CategoryListScreen(
                categories = mockCategories,
                onCategoryClick = { categoryId ->
                    // TODO: фильтр по категории, пока возврат на список приложений
                    navController.navigate("apps")
                }
            )
        }
    }
}
