package com.example.tryone

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryone.model.Person
import com.example.tryone.repository.PersonRepository
import com.example.tryone.ui.theme.Typography


@Composable
fun LazyListScreen(items: List<Person>) {
    LazyColumn(contentPadding = PaddingValues(all = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(items = items) { person ->
            CustomItem(person = person)
        }
    }
}


@Composable
fun CustomItem(person: Person) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        val img= painterResource(id = R.drawable.ic_check_balance)
        Column (Modifier.weight(0.5f, fill = true)) {
            Image(painter = img, contentDescription = null, modifier =  Modifier.fillMaxWidth().fillMaxHeight().clip(
                RoundedCornerShape(8.dp)
            ))
        }

        Column (Modifier.weight(1.3f, fill = true)){
            Text(
                text = person.name,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                text = "MRP :" + person.price,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif
            )
        }
        Column (Modifier.weight(1f, fill = true), verticalArrangement = Arrangement.SpaceAround){
            person.extra?.let {
                Text(
                    text = it,
                    color = Color.Black,
                    fontSize =13.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}


@Composable
@Preview
fun CustomItemPreview() {
    CustomItem(
        person = Person(
            name = "item 1",
            price= "100",
            extra="Same Day Shipping",
            image = "https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png"
        )
    )
}