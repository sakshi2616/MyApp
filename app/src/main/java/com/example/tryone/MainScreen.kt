package com.example.tryone

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.tryone.model.Person
import com.example.tryone.repository.PersonRepository
import kotlinx.coroutines.launch
import kotlin.text.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val searchQuery = remember { mutableStateOf("") }

    val apiService = RetrofitInstance.api
    val personRepository = PersonRepository(apiService)

    val coroutineScope = rememberCoroutineScope()

    var items by remember { mutableStateOf(emptyList<Person>()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            items = personRepository.getAllData()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White, // Set background color to white
                contentColor = Color.Blue // Set content (text and icon) color to blue
            ) {
                // Add search bar below "EXPLORE" and "FILTER"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color.White)

                ) {
                    Column(modifier = Modifier.weight(1f)){
                        Text(
                            text = "EXPLORE",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            color = Color.Blue // Set text color to blue
                        )
                    }
                    Column (modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End){
                        Text(
                            text = "FILTER",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Right,
                            color = Color.Green // Set text color to blue
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        // Use Column with weight modifier to layer the search bar above the Box containing BottomNavGraph
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search bar with weight modifier
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color.White)
                    .weight(0.12f), // Set weight to 1 to take available space
                shape = RoundedCornerShape(16.dp), // Set rounded corner shape
                textStyle = LocalTextStyle.current.copy(color = Color.Blue), // Set text color to blue
                label = { Text("Search") }, // Set label text
                leadingIcon = null, // No leading icon
                trailingIcon = null, // No trailing icon
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Perform action when "Done" is clicked on the keyboard
                    }
                )
            )

            // Box containing BottomNavGraph
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Set weight to 1 to take available space
            ) {
                BottomNavGraph(navController = navController, personRepository = personRepository)
            }
        }
    }
}





@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.List,
        BottomBarScreen.Grid,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation (backgroundColor = Color.White) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}