package com.mergeandmunch.market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MarketApp() }
    }
}

@Composable
fun MarketApp() {
    val game = remember { GameViewModel() }
    MaterialTheme {
        Surface(Modifier.fillMaxSize(), color = Color(0xFFEAF9F6)) {
            Column(Modifier.fillMaxSize()) {
                Header(game.state.coins, game.state.stars)
                when (game.state.screen) {
                    "market" -> MarketScreen(game)
                    "shop" -> ShopScreen(game)
                    else -> FarmScreen(game)
                }
                BottomNav(game.state.screen, game::show)
            }
        }
    }
}

@Composable
private fun Header(coins: Int, stars: Int) {
    Row(Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("MERGE\n& MUNCH", fontWeight = FontWeight.Black, color = Color(0xFF268F85), lineHeight = 17.sp)
        Spacer(Modifier.weight(1f))
        Text("🪙 $coins   ⭐ $stars", fontWeight = FontWeight.Black, modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp)).padding(10.dp))
    }
}

@Composable
private fun FarmScreen(game: GameViewModel) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Sunny Farm", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Color(0xFF31566C))
        Text("GROW INGREDIENTS · SERVE THE MARKET", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE89452))
        Card(shape = RoundedCornerShape(22.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("YOUR FIELDS", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF397C62))
                Text("Harvest fresh produce, then merge it into better recipes.", fontSize = 13.sp)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    game.state.plots.forEachIndexed { index, plot ->
                        Column(Modifier.weight(1f).clip(RoundedCornerShape(15.dp)).background(Color(0xFFD9F2D0)).clickable { game.harvest(index) }.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(if (plot.ready) "🌱" else "🪹", fontSize = 30.sp)
                            Text(plot.family.emoji, fontSize = 22.sp)
                            Text(if (plot.ready) "Harvest" else "Growing", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        Text(game.state.message, color = Color(0xFF397C62), fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Button(onClick = { game.show("market") }, modifier = Modifier.fillMaxWidth()) { Text("Open Market Board") }
    }
}

@Composable
private fun MarketScreen(game: GameViewModel) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) { Text("Market Board", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black); Text(game.state.message, fontSize = 12.sp, color = Color(0xFF397C62)) }
            Button(onClick = { game.show("farm") }) { Text("Farm") }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(5), horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth().weight(1f, fill = false)) {
            itemsIndexed(game.state.board) { index, item ->
                Box(Modifier.aspectRatio(1f).clip(RoundedCornerShape(13.dp)).background(if (item == null) Color(0xFFC4EAD8) else Color(0xFF95D6B7)).border(if (game.state.selected == index) 3.dp else 0.dp, Color.Yellow, RoundedCornerShape(13.dp)).clickable { game.tap(index) }, contentAlignment = Alignment.Center) {
                    if (item != null) Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(item.family.emoji, fontSize = 27.sp); Text("Lv. ${item.stage + 1}", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold) }
                }
            }
        }
        Text("ORDERS", fontWeight = FontWeight.Black, color = Color(0xFFE89452))
        if (game.state.orders.isEmpty()) Text("All orders served. Grow more ingredients for the next market day!", fontSize = 13.sp)
        game.state.orders.forEachIndexed { index, order ->
            Row(Modifier.fillMaxWidth().background(Color(0xFFFFF9E9), RoundedCornerShape(15.dp)).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("👩‍🌾  ${order.family.emoji}", fontSize = 22.sp); Spacer(Modifier.width(8.dp)); Column(Modifier.weight(1f)) { Text(order.name, fontWeight = FontWeight.Bold); Text("Level ${order.stage + 1} · +${order.reward} coins", fontSize = 12.sp) }; Button(onClick = { game.serve(index) }) { Text("Serve") }
            }
        }
    }
}

@Composable
private fun ShopScreen(game: GameViewModel) {
    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Market Shop", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Color(0xFF31566C))
        Text("Turn your farm into a warmer place to serve friends.", fontSize = 14.sp)
        Card(shape = RoundedCornerShape(20.dp)) { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Text("🪴", fontSize = 40.sp); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Happy plant", fontWeight = FontWeight.Black); Text("A little more life for Sunny Farm", fontSize = 12.sp) }; Button(onClick = { game.buyDecoration() }) { Text("🪙 180") } } }
        Text(game.state.message, color = Color(0xFF397C62), fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun BottomNav(screen: String, show: (String) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = { show("farm") }) { Text(if (screen == "farm") "🌻 Farm" else "Farm") }
        Button(onClick = { show("market") }) { Text(if (screen == "market") "🧺 Market" else "Market") }
        Button(onClick = { show("shop") }) { Text(if (screen == "shop") "🛍 Shop" else "Shop") }
    }
}
