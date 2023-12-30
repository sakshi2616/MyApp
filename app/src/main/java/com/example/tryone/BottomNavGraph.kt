package com.example.tryone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tryone.model.Person
import com.example.tryone.repository.PersonRepository

@Composable
fun BottomNavGraph(navController: NavHostController, personRepository: PersonRepository) {
    var items by remember { mutableStateOf(emptyList<Person>()) }

    NavHost(navController = navController, startDestination = BottomBarScreen.List.route) {
        composable(route = BottomBarScreen.List.route) {
            // Use LaunchedEffect to call the suspend function within a coroutine
            LaunchedEffect(personRepository) {
                val data = personRepository.getAllData()
                items = data
            }
            LazyListScreen(items = items)
        }
        composable(route = BottomBarScreen.Grid.route) {
            // Use LaunchedEffect to call the suspend function within a coroutine
            LaunchedEffect(personRepository) {
                val data = personRepository.getAllData()
                items = data
            }
            LazyGridScreen(items = items, personRepository = personRepository)
        }
    }
}
