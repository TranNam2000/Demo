package com.example.demo.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.base.BaseViewModel
import com.app.base.common.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@HiltViewModel
class AccountViewModel @Inject constructor() : BaseViewModel() {
    private val _profileImageState = MutableLiveData<State<Bitmap>>()
    val profileImageState: LiveData<State<Bitmap>> = _profileImageState
    
    fun saveImageFromUri(context: Context, uri: Uri) {
        _profileImageState.value = State.Loading
        
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            if (bitmap != null) {
                // Save to external storage
                saveImageToStorage(context, bitmap)
                _profileImageState.value = State.Success(bitmap)
            } else {
                _profileImageState.value = State.Error("Failed to load image")
            }
            
        } catch (e: Exception) {
            _profileImageState.value = State.Error(e.message ?: "Unknown error")
        }
    }
    
    fun saveImageFromBitmap(context: Context, bitmap: Bitmap) {
        _profileImageState.value = State.Loading
        
        try {
            saveImageToStorage(context, bitmap)
            _profileImageState.value = State.Success(bitmap)
        } catch (e: Exception) {
            _profileImageState.value = State.Error(e.message ?: "Unknown error")
        }
    }
    
    private fun saveImageToStorage(context: Context, bitmap: Bitmap): Uri? {
        return try {
            val imagesDir = File(context.getExternalFilesDir(null), "ProfileImages")
            if (!imagesDir.exists()) {
                imagesDir.mkdirs()
            }
            
            val imageFile = File(imagesDir, "profile_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
            
            Uri.fromFile(imageFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun loadSavedImage(context: Context) {
        _profileImageState.value = State.Loading
        
        try {
            val imagesDir = File(context.getExternalFilesDir(null), "ProfileImages")
            if (imagesDir.exists()) {
                val imageFiles = imagesDir.listFiles { _, name ->
                    name.startsWith("profile_") && name.endsWith(".jpg")
                }
                
                if (imageFiles?.isNotEmpty() == true) {
                    val latestFile = imageFiles.maxByOrNull { it.lastModified() }
                    latestFile?.let { file ->
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        if (bitmap != null) {
                            _profileImageState.value = State.Success(bitmap)
                        } else {
                            _profileImageState.value = State.Error("Failed to load saved image")
                        }
                    } ?: run {
                        _profileImageState.value = State.Error("No saved image found")
                    }
                } else {
                    _profileImageState.value = State.Error("No saved image found")
                }
            } else {
                _profileImageState.value = State.Error("No saved image found")
            }
        } catch (e: Exception) {
            _profileImageState.value = State.Error(e.message ?: "Unknown error")
        }
    }
}
