package com.example.dicegame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DiceGame()
            }
        }
    }
}

@Composable
fun DiceGame() {
    var diceRolls by remember { mutableStateOf(List(6) { Random.nextInt(1, 7) }) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = result, style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            diceRolls.forEach { die ->
                DiceImage(value = die)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(

            modifier = Modifier.padding(16.dp),
            onClick = {
                diceRolls = List(6) { Random.nextInt(1, 7) }
                result = evaluateDice(diceRolls)
            },
        ) {
            Text("Roll Dice")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = result, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun TestButton(){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }

}

@Composable
fun DiceImage(value: Int) {
    Image(
        painter = painterResource(id = getDiceImage(value)),
        contentDescription = "Dice showing $value",
        modifier = Modifier.size(80.dp)
    )
}

fun getDiceImage(value: Int): Int {
    return when (value) {
        1 -> R.drawable.die_1
        2 -> R.drawable.die_2
        3 -> R.drawable.die_3
        4 -> R.drawable.die_4
        5 -> R.drawable.die_5
        6 -> R.drawable.die_6
        else -> R.drawable.die_1 // Default case
    }
}

fun evaluateDice(dice: List<Int>): String {
    val counts = dice.groupingBy { it }.eachCount()
    val uniqueCounts = counts.values.toSet()

    return when {
        uniqueCounts.contains(6) -> "Six of a Kind"
        uniqueCounts.contains(5) -> "Five of a Kind"
        uniqueCounts.contains(4) -> "Four of a Kind"
        counts.size == 2 && uniqueCounts.contains(3) -> "Full House"
        uniqueCounts.contains(3) -> "Three of a Kind"
        counts.size == 3 && uniqueCounts.count { it == 2 } == 2 -> "Two Pairs"
        counts.size == 4 -> "One Pair"
        uniqueCounts.contains(1) -> "Straight"
        else -> "No Matches"
    }
}
