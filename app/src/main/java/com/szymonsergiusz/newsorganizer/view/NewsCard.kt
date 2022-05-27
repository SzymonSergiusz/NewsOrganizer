package com.szymonsergiusz.newsorganizer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.szymonsergiusz.newsorganizer.model.News

@Composable
fun NewsCard(it: News) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = it.title, fontWeight = FontWeight.Bold)

        Text(
            maxLines = 2,
            text = it.description
        )


    }
}