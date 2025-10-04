package com.example.appandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppListScreen(
    apps: List<AppUiModel>,
    onAppClick: (Int) -> Unit,
    onCategoryClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onCategoryClick) {
            Text("Список категорий →")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(apps) { app ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color.LightGray)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Иконка (заглушка)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Gray)
                    )
                    Spacer(Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(app.name, fontWeight = FontWeight.Bold)
                        Text(app.shortDescription ?: "Ваш браузер…")
                        Text(
                            "Категория: ${app.categoryName}",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

data class AppUiModel(
    val id: Int,
    val name: String,
    val shortDescription: String?,
    val categoryName: String?
)

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    val mockApps = listOf(
        AppUiModel(1, "Max: общение", "Ваш браузер…", "Финансы"),
        AppUiModel(2, "Max: общение", "Ваш браузер…", "Финансы")
    )
    AppListScreen(apps = mockApps, onAppClick = {}, onCategoryClick = {})
}
