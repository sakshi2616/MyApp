package com.example.tryone

data class ApiResponse(
    val status: String,
    val error: String?,
    val data: Data
)

data class Data(
    val items: List<Item>
)

data class Item(
    val name: String,
    val price: String,
    val image: String,
    val extra: String?
)

