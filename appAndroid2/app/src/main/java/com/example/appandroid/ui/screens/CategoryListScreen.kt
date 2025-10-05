package com.example.appandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appandroid.model.Categories

@Composable
fun CategoryListScreen(
    categories: List<Categories>,
    onCategoryClick: (Int) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
                .align(Alignment.TopCenter)
                .background(
                    color = Color(0xFF007BFF),
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 16.dp, end = 16.dp)
        ) {
            Text("Категории", fontSize = 36.sp, fontWeight = FontWeight.Bold)

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
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFB5C5FC)
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = cat.name,
                                        color = Color(0xFF000000),
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(start = 1.dp)
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
fun CategoryListScreenPreview() {
    val mockCategories = listOf(
        Categories(1, "Аркады"),
        Categories(2, "Финансы"),
        Categories(3, "Игры")
    )

    CategoryListScreen(categories = mockCategories, onCategoryClick = {})
}
