package com.example.arassignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.ar.core.Anchor
import com.google.ar.core.ArCoreApk
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.exceptions.UnavailableException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment


@Composable
fun ARApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "drill_selection") {
        composable("drill_selection") { DrillSelectionScreen(navController) }
        composable("drill_detail/{drill}") { backStackEntry ->
            val drill = backStackEntry.arguments?.getString("drill") ?: ""
            DrillDetailScreen(drill, navController)
        }
    }
}


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


class ARActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient) {
            // Try again later
            Handler(Looper.getMainLooper()).postDelayed({ recreate() }, 200)
            return
        }

        if (!availability.isSupported) {
            Toast.makeText(this, "AR not supported or ARCore not installed", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setContentView(R.layout.activity_ar)

        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, _ ->
            if (plane.type != Plane.Type.HORIZONTAL_UPWARD_FACING) return@setOnTapArPlaneListener
            placeGreenBox(hitResult)
        }
    }


    private fun placeGreenBox(hitResult: HitResult) {
        val anchor: Anchor = hitResult.createAnchor()
        MaterialFactory.makeOpaqueWithColor(this, com.google.ar.sceneform.rendering.Color(android.graphics.Color.RED))
            .thenAccept { material ->
                val cube = ShapeFactory.makeCube(Vector3(0.1f, 0.1f, 0.1f), Vector3.zero(), material)
                val cone = ShapeFactory.makeSphere(0.1f, Vector3.zero(), material)
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment.arSceneView.scene)
                anchorNode.renderable = cone
            }
    }
}



