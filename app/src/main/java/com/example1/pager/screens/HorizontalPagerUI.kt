package com.example1.pager.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example1.pager.R
import com.example1.pager.data.Food
import com.example1.pager.ui.theme.Orange
import kotlinx.coroutines.launch
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HorizontalPagerUI(
    modifier: Modifier = Modifier,

) {

    val foodList = listOf(
        Food(R.drawable.hbread,"Bread","Bread a staple food is made from flour water yeast and salt with numerous varieties and cultural significance worldwide"),
        Food(R.drawable.hburger,"Burger","A burger consists of a cooked patty typically beef placed inside a bun often with toppings like lettuce tomato and cheese"),
        Food(R.drawable.hcoffee,"Coffee","Coffee is a brewed drink made from roasted coffee beans known for its stimulating caffeine content and enjoyed worldwide in various forms"),
        Food(R.drawable.hdonalt,"Donalt","A donut is a sweet fried pastry typically ring-shaped or filled often glazed or topped with sugar enjoyed as a snack"),
        Food(R.drawable.hicecream,"Ice-Cream","Ice cream is a frozen dessert made from cream sugar and flavorings enjoyed in various flavors and often served in cones"),
        Food(R.drawable.hmac,"Macaroni","Macaroni is a type of dry pasta shaped like narrow tubes commonly used in dishes like macaroni and cheese or pasta salads"),
        Food(R.drawable.hmilk,"Milk","Milk is a nutritious liquid produced by mammals rich in calcium and protein commonly consumed as a beverage or used in cooking"),
        Food(R.drawable.hpasta,"Pasta","Pasta is an Italian staple made from wheat flour and water formed into shapes and cooked by boiling often with sauces"),
        Food(R.drawable.hpizza,"Pizza","Pizza is a popular Italian dish with a baked dough base topped with tomato sauce cheese and various ingredients"),
        Food(R.drawable.hsnadwich,"Sandwich","A sandwich consists of two slices of bread with fillings like meat cheese vegetables or spreads a versatile and portable meal"),
    )

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Horizontal pager",
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        }

    ) { innerPadding ->

        val pager = rememberPagerState {
            10
        }
        var buttonName by rememberSaveable {
            mutableStateOf("Next")
        }
        val scope = rememberCoroutineScope()
        var visibleSkip by remember{ mutableStateOf(true) }
        


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            HorizontalPager(
                state = pager,
                modifier = Modifier.weight(.2f),
                pageSize = PageSize.Fill,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pager,
                    pagerSnapDistance = PagerSnapDistance.atMost(200),
                    snapAnimationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )
            ) {page ->
               FoodItem(
                   modifier,
                   foodList[page],
                   onPageSelected = {
                       scope.launch {
                           pager.animateScrollToPage(page)
                       }
                   }
               )
            }
            Row (
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxWidth()
                    .padding(10.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.Center
            ){
                repeat(pager.pageCount){iteration ->
                    val color = if (pager.currentPage == iteration) Orange else Color.LightGray
                    Box (
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                            .clickable {
                                if (pager.currentPage == 0 || pager.currentPage < 9) {
                                    scope.launch {
                                        pager.animateScrollToPage(
                                            pager.currentPage + 1
                                        )
                                    }
                                }
                            }
                    )
                }
            }
            PreviewButtons(title = "Next") {
                if (pager.currentPage ==0 || pager.currentPage<9) {
                    scope.launch {
                        pager.animateScrollToPage(
                            pager.currentPage + 1
                        )
                    }
                }
            }
            PreviewButtons(title = "Previous") {
                if (pager.currentPage ==0 || pager.currentPage<=9) {
                    scope.launch {
                        pager.animateScrollToPage(
                            pager.targetPage - 1
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodItem(
    modifier: Modifier = Modifier,
    item: Food,
    onPageSelected : (Food) -> Unit

) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp,)
            .clickable {
                onPageSelected(item)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = item.foodImage),
            contentDescription = item.foodName ,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp)
                .width(24.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = item.foodName,
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = item.foodDescription,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

@Composable
fun PreviewButtons(
    title:String,
    modifier: Modifier = Modifier,
    onClick:()->Unit,

){

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = modifier
                .width(280.dp)
                .height(70.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(14),
            colors = ButtonDefaults.buttonColors(Orange),
            elevation = ButtonDefaults.buttonElevation(10.dp),
            onClick = {
                onClick()
            }) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = colorResource(id = R.color.white)
            )
        }
    }
}






















