package com.example.arassignment

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/*
@Composable
fun DrillSelectionScreen(navController: NavController) {
    val drills = listOf("Drill 1", "Drill 2", "Drill 3")
    var selectedDrill by remember { mutableStateOf(drills[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Drill")
        DropdownMenuDrills(drills, selectedDrill) {
            selectedDrill = it
        }

        Button(
            onClick = { navController.navigate("drill_detail/$selectedDrill") },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Start AR Drill")
        }
    }
}



@Composable
fun DropdownMenuDrills(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = selected,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { expanded = true }
                .padding(12.dp)
                .border(1.dp, Color.Gray)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { label ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onSelect(label)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun DrillDetailScreen(drill: String, navController: NavController) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Drill: $drill", fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        Text("Description: This is a dummy description for $drill")
        Text("Tips: Use in a well-lit area for better AR tracking.")
        Spacer(Modifier.height(20.dp))
        Button(onClick = {

            context.startActivity(Intent(context, ARActivity::class.java))
        }) {
            Text("Open AR View")
        }
    }
}
*/



@Composable
fun DrillSelectionScreen(navController: NavController) {
    val drills = listOf("Drill 1", "Drill 2", "Drill 3")
    var selectedDrill by rememberSaveable { mutableStateOf(drills[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2F), Color(0xFF15151F))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.1f)
            ),
            elevation = CardDefaults.cardElevation(12.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Choose Your Drill",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(20.dp))

                DropdownMenuDrills(drills, selectedDrill) {
                    selectedDrill = it
                }

                Spacer(Modifier.height(28.dp))

                Button(
                    onClick = { navController.navigate("drill_detail/$selectedDrill") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C9A7)
                    )
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Start AR Drill", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DropdownMenuDrills(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { expanded = true }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = selected, color = Color.White)

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1C1C2B))
        ) {
            options.forEach { label ->
                DropdownMenuItem(
                    text = { Text(label, color = Color.White) },
                    onClick = {
                        onSelect(label)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DrillDetailScreen(drill: String, navController: NavController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F0F1C), Color(0xFF1B1B2F))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.08f)
            ),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Drill: $drill",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Description:\nA detailed session to master AR placement.",
                    color = Color.LightGray,
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Tip:\nUse in bright lighting and flat surfaces.",
                    color = Color.LightGray,
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(28.dp))

                Button(
                    onClick = {
                        context.startActivity(Intent(context, ARActivity::class.java))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6C63FF)
                    )
                ) {
                    Icon(painterResource(R.drawable.camera), contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Open AR View", color = Color.White)
                }
            }
        }
    }
}
