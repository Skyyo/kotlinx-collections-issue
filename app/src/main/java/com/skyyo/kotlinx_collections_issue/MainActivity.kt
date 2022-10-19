package com.skyyo.kotlinx_collections_issue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skyyo.kotlinx_collections_issue.ui.theme.KotlinxcollectionsissueTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinxcollectionsissueTheme {
                TestScreen()
            }
        }
    }
}

@Composable
fun TestScreen() {
    var count by remember { mutableStateOf(0) }
    val listViaPersistentListBuilder = remember { persistentListOf(1, 2, 3) }
    val listViaCasting = remember { listOf(1, 2, 3).toImmutableList() }

    Column {
        Texts(listViaPersistentListBuilder) // will cause recompositions
        Texts(listViaPersistentListBuilder as ImmutableList<Int>)  // casting fixes the issue
        Texts(listViaCasting) // works as expected
        Button(modifier = Modifier.size(108.dp), onClick = { count++ }) { Text(text = "count++") }
        CounterText(count)
    }
}


@Composable
fun Texts(items: ImmutableList<Int>) {
    for (item in items) {
        Text(text = "Item $item ")
    }
}

@Composable
fun CounterText(count: Int) {
    Text(text = "Count: $count")
}