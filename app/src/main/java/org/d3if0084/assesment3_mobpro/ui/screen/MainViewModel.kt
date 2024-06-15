package org.d3if0084.assesment3_mobpro.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if0084.assesment3_mobpro.model.Gundam
import org.d3if0084.assesment3_mobpro.network.ApiStatus
import org.d3if0084.assesment3_mobpro.network.GundamApi
import java.io.ByteArrayOutputStream
import kotlin.Exception

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Gundam>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO){
            status.value = ApiStatus.LOADING
            try {
                data.value= GundamApi.service.getGundam(userId)
                status.value = ApiStatus.SUCCESS
            }
            catch (e:Exception){
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun DeleteData(userId: String, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GundamApi.service.DeleteGundam(
                    userId,
                    id
                )
                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)

            } catch (e: Exception) {
                Log.d("DELETE", "Errors: ${e.message}")
            }
        }
    }

    fun saveData(userId: String, namaGundam: String, grade: String, bitmap: Bitmap) {
        viewModelScope.launch (Dispatchers.IO){
            try {
                val result = GundamApi.service.postGundam(
                    userId,
                    namaGundam.toRequestBody("text/plain".toMediaTypeOrNull()),
                    grade.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            }catch (e: Exception){
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    private fun  Bitmap.toMultipartBody(): MultipartBody.Part{
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null }
}