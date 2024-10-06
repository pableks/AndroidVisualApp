package com.example.myapplication.pantallas

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectRecognitionScreen(navController: NavController, viewModel: TextSizeViewModel) {
    var textSizeMultiplier by remember { mutableFloatStateOf(viewModel.textSizeMultiplier.value) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var detectedObjects by remember { mutableStateOf<List<String>>(emptyList()) }
    val context = LocalContext.current

    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                capturedImageUri = uri
                detectObjects(uri, context) { labels ->
                    detectedObjects = labels
                }
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(uri)
            } else {
                // Handle permission denied
            }
        }
    )

    LaunchedEffect(viewModel.textSizeMultiplier.value) {
        textSizeMultiplier = viewModel.textSizeMultiplier.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reconocimiento de Objetos", fontSize = 18.sp * textSizeMultiplier) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextSizeIcon(
                        text = "A",
                        onClick = { viewModel.decreaseTextSize() },
                        fontSize = 14
                    )
                    TextSizeIcon(
                        text = "A",
                        onClick = { viewModel.increaseTextSize() },
                        fontSize = 18
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AddCircle, contentDescription = "Camera")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tomar Foto", fontSize = 16.sp * textSizeMultiplier)
            }

            capturedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Captured image",
                    modifier = Modifier
                        .size(200.dp)
                        .aspectRatio(1f)
                )
            }

            if (detectedObjects.isNotEmpty()) {
                Text("Objetos Detectados:", fontSize = 18.sp * textSizeMultiplier)
                detectedObjects.forEach { detectedObject ->
                    Text(text = detectedObject, fontSize = 16.sp * textSizeMultiplier)
                }
            }
        }
    }
}

fun android.content.Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}

fun detectObjects(imageUri: Uri, context: android.content.Context, onDetectionComplete: (List<String>) -> Unit) {
    val image = InputImage.fromFilePath(context, imageUri)
    val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    labeler.process(image)
        .addOnSuccessListener { labels ->
            val detectedLabels = labels.map { it.text }
            onDetectionComplete(detectedLabels)
        }
        .addOnFailureListener { e ->
            Log.e("ObjectRecognition", "Error detecting objects", e)
            onDetectionComplete(emptyList())
        }
}