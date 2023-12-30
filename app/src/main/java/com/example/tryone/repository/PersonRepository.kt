package com.example.tryone.repository

import com.example.tryone.ApiResponse
import com.example.tryone.model.Person
import retrofit2.http.GET

interface ApiService {
    @GET("v3/7181ed68-194b-4509-9889-7513e29345d4")
    suspend fun getItems(): ApiResponse
}

class PersonRepository(private val apiService: ApiService) {

    suspend fun getAllData(): List<Person> {
        val response = apiService.getItems()
        return response.data.items.map { item ->
            Person(
                name = item.name,
                price = item.price,
                image = item.image,
                extra = item.extra
            )
        }
    }
}
