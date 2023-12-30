package com.example.tryone

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object List : BottomBarScreen(
        route = "list",
        title= "List",
        icon = Icons.Default.AddCircle
    )
    object Grid : BottomBarScreen(
        route = "grid",
        title= "Grid",
        icon = Icons.Default.AccountCircle
    )
}
