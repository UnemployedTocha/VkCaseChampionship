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

@Composable
fun CategoryListScreen(
    categories: List<CategoryUiModel>,
    onCategoryClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Категории", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(categories) { cat ->
                Button(
                    onClick = { onCategoryClick(cat.id) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text("${cat.name} — ${cat.appCount} приложений →")
                }
            }
        }
    }
}

data class CategoryUiModel(val id: Int, val name: String, val appCount: Int)

@Preview(showBackground = true)
@Composable
fun CategoryListScreenPreview() {
    val mockCats = listOf(
        CategoryUiModel(1, "Аркады", 26),
        CategoryUiModel(2, "Финансы", 100),
        CategoryUiModel(3, "Игры", 50)
    )
    CategoryListScreen(categories = mockCats, onCategoryClick = {})
}
