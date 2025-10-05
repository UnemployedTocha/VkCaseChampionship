package com.example.appandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appandroid.model.Categories
import androidx.compose.ui.graphics.Color

//количества приложений тут пока что нет

@Composable
fun CategoryListScreen(
    categories: List<Categories>,
    onCategoryClick: (Int) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Категории", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Text("Загрузка категорий...", color = Color.Gray)
            }
            errorMessage != null -> {
                Text("Ошибка: $errorMessage", color = Color.Red)
            }
            else -> {
                LazyColumn {
                    items(categories) { cat ->
                        Button(
                            onClick = { onCategoryClick(cat.id) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(cat.name)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryListScreenPreview() {
    val mockCategories = listOf(
        Categories(1, "Аркады"),
        Categories(2, "Финансы"),
        Categories(3, "Игры")
    )

    CategoryListScreen(categories = mockCategories, onCategoryClick = {})
}
