package com.example.tryone

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Search
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColor = Color(0xffe6eaf6)
                , // Set background color to white
                //contentColor = Color.Blue, // Set content (text and icon) color to blue
                //elevation = 4.dp, // Set elevation for a shadow
                contentPadding = PaddingValues(16.dp) // Adjust content padding

            ) {
                // Add search bar below "EXPLORE" and "FILTER"
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(8.dp)
                        //.background(Color.White)
                ) {
                    // Row containing "EXPLORE" and "FILTER"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Explore",
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Left,
                            color = Color.Black // Set text color to blue
                        )
                        Text(
                            text = "Filter",
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Right,
                            color = Color.Green // Set text color to blue
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    // Search bar
                    // Search bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 1.dp) // Add padding around the box
                    ) {
                        OutlinedTextField(
                            value = searchQuery.value,
                            onValueChange = { searchQuery.value = it },
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(16.dp),
                            textStyle = LocalTextStyle.current.copy(color = Color.Blue),
                            label = {
                                Text("Search", color = Color.Gray, textAlign = TextAlign.Center) // Set label text and color
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = Color.White, // Set background color to white
                                focusedBorderColor = Color.Transparent, // Make the border transparent when focused
                                unfocusedBorderColor = Color.Transparent // Make the border transparent when not focused
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {})
                        )
                    }


                }
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ){ innerPadding ->
        // Use Column with weight modifier to layer the search bar above the Box containing BottomNavGraph
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // Box containing BottomNavGraph
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f) // Set weight to 1 to take available space
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
        BottomBarScreen.Nothing,
        BottomBarScreen.Nothin,
        BottomBarScreen.Not
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