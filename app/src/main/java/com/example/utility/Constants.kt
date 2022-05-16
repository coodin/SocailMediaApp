package com.example.utility

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.presentation.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val TAG = "Firebase"
const val EXCEPTION = "AuthException"
const val MyError = "AndroidError"
val DEFAULTDOMAIN = "loginsignupcompose.page.link"


val COLOR_NORMAL = Color(0xffEDEFF4)
val COLOR_SELECTED = Color(0xff496DE2)
val ICON_SIZE = 24.dp


fun Uri.getImageFromMediaStore(context: Context): Bitmap? {
    val resolver = context.contentResolver
    resolver.openInputStream(this).use { image ->
        try {
            if (image != null) {
                val size = image.available()
                val byte = ByteArray(size)
                image.read(byte)
                return BitmapFactory.decodeByteArray(byte, 0, size)
            }

        } catch (e: IOException) {
            Log.e(EXCEPTION, "Exception has been occurred ${e.message}")
            Toast.makeText(context, "Unintended error happened ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    //ImageDecoder.createSource(resolver,this).decodeBitmap{info,source -> }
    return null
}


fun createNewImageFile(context: Context): ImageCapture.OutputFileOptions {
    val resolver = context.contentResolver
    val filename = "${System.currentTimeMillis()}.jpg"
    // On API <= 28, use VOLUME_EXTERNAL instead.
    //var imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    val imageCollection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
    //val volumeNames = MediaStore.getExternalVolumeNames(context)
    val newImageDetails = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        // put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.getRootDirectory())
    }
    //return Uri of newly created file
//    if (imageCollection != null) {
//        return resolver.insert(imageCollection, newImageDetails)
//    }
    Log.d(TAG, "Uri of the created picture: $imageCollection")
    return ImageCapture.OutputFileOptions.Builder(resolver, imageCollection, newImageDetails)
        .build()
}

//fun deleteMediaFile(fileName: String) {
//    val fileInfo = getFileInfoFromName(fileName) //this function returns Kotlin Pair<Long, Uri>
//    val id = fileInfo.first
//    val uri = fileInfo.second
//    val selection = "${MediaStore.Images.Media._ID} = ?"
//    val selectionArgs = arrayOf(id.toString())
//    val resolver = context.contentResolver
//    resolver.delete(uri, selection, selectionArgs)
//}

suspend fun ImageCapture.takePicture(executor: Executor, context: Context): Uri? {
    val photoFile = withContext(Dispatchers.IO) {
        if (isExternalStorageWritable()) {
            kotlin.runCatching {
                //File.createTempFile("image", "jpg")
                //Log.d(TAG, "root file directory " + context.getExternalFilesDir(null)?.toUri().toString())
                val directory = File(
                    context.getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES
                    ), "mySecondAlbum"
                )
                //val directory =  getExternalStorage(context = context)
                //context.openFileInput("image").buffered(1024 * 1024 * 4).use { value -> value.read() }
                if (!directory.mkdirs()) {
                    Log.d(TAG, "Directory not created")
                } else {
                    Log.d(TAG, "Directory has been created")
                }
                //Log.d(TAG, "createdDirectory: ${directory.toUri()}")
                val image = File.createTempFile(
                    "image", ".jpg", directory
                )
                //File(context.filesDir, "image").mkdirs()
                //SaveFileToExternal(image)
                image
            }.getOrElse { ex ->
                Log.e("TakePicture", "Failed to create temporary file", ex)
                Toast.makeText(context, "Failed to create temporary file", Toast.LENGTH_SHORT).show()
                File("/dev/null")
                //SaveFileToExternal(File("/dev/null"), ex, "Failed to create temporary file")
            }
        } else {
            Log.e("TakePicture", "There is no external volume to reach")
            Toast.makeText(context, "There is no external volume to reach", Toast.LENGTH_SHORT).show()
            File("/dev/null")
            //SaveFileToExternal(File("/dev/null"), messageOfException = "There is no external volume to reach")
        }
    }

    val options = createNewImageFile(context = context)

    return suspendCoroutine { continuation ->
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
//            .build()
        takePicture(options, executor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                Log.d(TAG,"Saved Uri object: ${output.savedUri}")
                continuation.resume(output.savedUri)
            }

            override fun onError(ex: ImageCaptureException) {
                Log.e("TakePicture", "Image capture failed", ex)
                continuation.resumeWithException(ex)
            }
        })
    }
}


suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener({
            continuation.resume(future.get())
        }, executor)
    }
}


val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)


fun Color.convertHex(colorString: String): Color {
    return Color(android.graphics.Color.parseColor("#$colorString"))
}

fun Modifier.drawLine(
    color: Color = Color.Black,
    strokeWidth: Float = 1f,
    offsetY: Int?
) =
    drawWithContent {
        drawContent()
//        inset(){
//
//        }
        translate(top = offsetY?.toFloat() ?: 10f) {
            drawLine(
                color = color,
                start = Offset(x = 0f, y = size.height - 1),
                end = Offset(x = size.width, y = size.height - 1),
                strokeWidth = strokeWidth
            )
        }


    }

// Checks if a volume containing external storage is available
// for read and write.
fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}



object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = AppTheme.colors.profileHighLightColor

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 1f)
}