package com.example.rsmtemp

import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileItem(
    val id: String,
    val title: String,
    val value: String,
    val contentDescription: String,
    val icon: ImageVector,
    val buttonText: String
)