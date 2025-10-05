package com.example.appandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appandroid.model.App
import com.example.appandroid.model.Screenshot

@Composable
fun AppListScreen(
    apps: List<App>,
    onAppClick: (App) -> Unit,
    onCategoryClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок
        Text(
            text = "Приложения",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = onCategoryClick) {
            Text("Список категорий →")
        }

        Spacer(Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                // >>> ВОТ ТУТ ОБОРАЧИВАЕМ СПИСОК В СЕРЫЙ БЛОК
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFE7E7E7)) // светло-серый фон
                        .padding(8.dp)
                ) {
                    LazyColumn {
                        items(apps) { app ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .background(Color(0xFFE7E7E7)) // каждый элемент — белая карточка
                                    .padding(8.dp)
                                    .clickable { onAppClick(app) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(Color.Gray)
                                )
                                Spacer(Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(app.name, fontWeight = FontWeight.Bold)
                                    Text(app.short_description)
                                    Text(
                                        "Категория: ${app.category_id}",
                                        fontSize = 12.sp,
                                        color = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    val mockApps = listOf(
        App(
            id = 1,
            name = "Max: общение",
            icon_url = "https://example.com/icon.png",
            short_description = "Ваш браузер…",
            full_description = "Подробное описание приложения...",
            category_id = 2,
            developer = "Meow Game",
            age_rating = "18+",
            apk_url = "https://example.com/app.apk",
            screenshots = listOf(Screenshot(1, "https://example.com/shot1.png"))
        ),
        App(
            id = 2,
            name = "Max: браузер",
            icon_url = "https://example.com/icon2.png",
            short_description = "Лучший браузер",
            full_description = "Длинное описание второго приложения...",
            category_id = 3,
            developer = "Max Devs",
            age_rating = "12+",
            apk_url = "https://example.com/app2.apk",
            screenshots = emptyList()
        )
    )

    AppListScreen(apps = mockApps, onAppClick = {}, onCategoryClick = {})
}
