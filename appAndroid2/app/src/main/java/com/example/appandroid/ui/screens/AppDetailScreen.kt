package com.example.appandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appandroid.R
import com.example.appandroid.model.App
import com.example.appandroid.model.Screenshot

@Composable
fun BackButton(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp) // размер кнопки
            .clickable { onBack() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = "Назад"
        )
    }
}

@Composable
fun AppDetailScreen(app: App, onBack: () -> Unit = {}) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

        // Кнопка "назад" сверху слева
        Row(verticalAlignment = Alignment.CenterVertically) {
            BackButton(onBack = onBack)
            Spacer(Modifier.width(8.dp))
        }

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Gray) // TODO: иконка по app.icon_url через Coil
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(app.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(app.developer)
            }
            Spacer(Modifier.weight(1f))
            Text(app.age_rating, color = Color.Red)
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { /* TODO: установка */ }) {
            Text("Установить")
        }

        Spacer(Modifier.height(16.dp))
        Text("Категория: ${app.category_id}", fontWeight = FontWeight.Medium) // пока ID

        Spacer(Modifier.height(12.dp))
        LazyRow {
            items(app.screenshots) { shot ->
                Box(
                    modifier = Modifier
                        .size(width = 120.dp, height = 240.dp)
                        .background(Color.LightGray)
                        .padding(4.dp)
                )
                // TODO: картинка по shot.url через Coil
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Описание", fontWeight = FontWeight.Bold)
        Text(app.full_description)
    }
}

@Preview(showBackground = true)
@Composable
fun AppDetailScreenPreview() {
    val mockApp = App(
        id = 1,
        name = "Max",
        icon_url = "https://example.com/icon.png",
        short_description = "Ваш браузер…",
        full_description = "Популярные разрешения включают HD (1280×720)...",
        category_id = 2,
        developer = "MEOW GAME",
        age_rating = "18+",
        apk_url = "https://example.com/app.apk",
        screenshots = listOf(
            Screenshot(1, "https://example.com/shot1.png"),
            Screenshot(2, "https://example.com/shot2.png"),
            Screenshot(3, "https://example.com/shot3.png")
        )
    )
    AppDetailScreen(app = mockApp)
}
