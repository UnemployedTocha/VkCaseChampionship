package com.example.appandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
fun AppDetailScreen(app: AppDetailUiModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Gray) // иконка-заглушка
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(app.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(app.developer)
            }
            Spacer(Modifier.weight(1f))
            Text(app.ageRating, color = Color.Red)
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { }) {
            Text("Установить")
        }

        Spacer(Modifier.height(16.dp))
        Text("Категория: ${app.categoryName}", fontWeight = FontWeight.Medium)

        Spacer(Modifier.height(12.dp))
        LazyRow {
            items(app.screenshots) { _ ->
                Box(
                    modifier = Modifier
                        .size(width = 120.dp, height = 240.dp)
                        .background(Color.LightGray)
                        .padding(4.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Описание", fontWeight = FontWeight.Bold)
        Text(app.fullDescription ?: "Нет описания")
    }
}

data class AppDetailUiModel(
    val name: String,
    val developer: String,
    val ageRating: String,
    val categoryName: String,
    val screenshots: List<String>,
    val fullDescription: String?
)

@Preview(showBackground = true)
@Composable
fun AppDetailScreenPreview() {
    val mockApp = AppDetailUiModel(
        name = "Max",
        developer = "MEOW GAME",
        ageRating = "18+",
        categoryName = "Финансы",
        screenshots = listOf("url1", "url2", "url3"),
        fullDescription = "Популярные разрешения включают HD (1280×720)..."
    )
    AppDetailScreen(app = mockApp)
}
