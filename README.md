# ☄️AR Assignment - Android App with Sceneform and ARCore

This project demonstrates a simple ARCore-based Android application using Sceneform SDK for placing a green cube on a detected plane.

## 📱 Requirements
- A real Android device with ARCore support (e.g. Realme 9 Pro 5G or similar)
- ARCore Services installed from the Play Store: https://play.google.com/store/apps/details?id=com.google.ar.core
- Android Studio Hedgehog or later

## 🧩 Dependencies (build.gradle)
```
implementation "com.google.ar:core:1.33.0"
implementation "com.gorisse.thomas.sceneform:core:1.19.6"
implementation "com.gorisse.thomas.sceneform:ux:1.19.6"
```

## 📄 Manifest Configuration (AndroidManifest.xml)
Ensure the following inside `<application>`:
```
<meta-data
    android:name="com.google.ar.core"
    android:value="required" />
```

Also add required permissions and features:
```
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" android:required="true" />
<uses-feature android:name="android.hardware.camera.ar" android:required="true" />
```

## 🏗 Layout (activity_ar.xml)
Place a fragment tag for the ARFragment:
```
<fragment
    android:id="@+id/ar_fragment"
    android:name="com.google.ar.sceneform.ux.ArFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## 🧠 ARActivity.kt Highlights
- Uses `ArFragment` to detect horizontal planes.
- Places a green cube using Sceneform when the user taps on a plane.
- Handles ARCore availability exceptions.

## 🛠 Known Issues & Fixes
- If the app crashes due to `NoSuchMethodError` for `acquireEnvironmentalHdrCubeMap()`, use **Sceneform 1.19.6** with **ARCore 1.33.0**.
- Ensure `<meta-data>` is **inside** the `<application>` block in the manifest.

## ✅ Notes
- Only works on physical AR-supported devices.
- Jetpack Compose is used for other UI elements (optional).

---

Created for AR Assignment (2025)
