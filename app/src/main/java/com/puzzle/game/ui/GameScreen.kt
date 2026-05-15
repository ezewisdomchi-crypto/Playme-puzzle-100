package com.puzzle.game.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.ads.*

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val puzzle by viewModel.puzzle.collectAsState()
    val level by viewModel.currentLevel.collectAsState()
    val score by viewModel.score.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        MobileAds.initialize(context)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Level $level / 100", style = MaterialTheme.typography.titleMedium)
            Text("Score: $score", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(Modifier.height(24.dp))

        puzzle?.let { p ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("What comes next?", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))
                Text(p.question, style = MaterialTheme.typography.displaySmall)
                Spacer(Modifier.height(32.dp))

                p.options.chunked(2).forEach { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        row.forEach { option ->
                            Button(
                                onClick = { viewModel.submitAnswer(option) },
                                modifier = Modifier.width(120.dp).padding(8.dp)
                            ) {
                                Text(option.toString())
                            }
                        }
                    }
                }

                if(level >= 100) {
                    Spacer(Modifier.height(24.dp))
                    Text("You completed all 100 levels!", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }

        // Test banner ad
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            factory = { ctx ->
                AdView(ctx).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
