package com.example.tryone

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryone.model.Person
import com.example.tryone.repository.PersonRepository
import com.example.tryone.ui.theme.Typography
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun LazyGridScreen(items: List<Person>, personRepository: PersonRepository) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        items(items = items) { person ->
            GridItem(person = person)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun GridItem(person : Person) {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .height(200.dp)
            .width(200.dp)
            .fillMaxSize(), // Make the Column fill the available space
        horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
        verticalArrangement = Arrangement.Center
    ) {
        val img = painterResource(id = R.drawable.ic_check_balance)
        Row () {
            Image(painter = img, contentDescription = null, contentScale = ContentScale.FillBounds, modifier = Modifier.size(width = 100.dp, height = 100.dp).clip(
                RoundedCornerShape(8.dp)
            ))
        }
        Row (){
            Text(text = person.name,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )

        }
        Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Top){
            Text(
                text =  "MRP :" + person.price,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GridItemPreview() {
    GridItem(
        person = Person(
            name = "item 1",
            price= "100",
            extra="Same Day Shipping",
            image = "https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png"
        )
    )
}