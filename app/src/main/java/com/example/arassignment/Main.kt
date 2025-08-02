package com.example.arassignment


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.ar.core.Anchor
import com.google.ar.core.ArCoreApk
import com.google.ar.core.HitResult
import com.google.ar.core.Plane

import com.google.ar.sceneform.AnchorNode

import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment


class ARActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient) {

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
                anchorNode.renderable = cube
            }
    }
}



