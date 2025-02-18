package org.d3if0084.assesment3_mobpro.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0084.assesment3_mobpro.model.Gundam
import org.d3if0084.assesment3_mobpro.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL="https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface GundamApiService {
    @GET("api_fadhlan.php")
    suspend fun getGundam(
        @Header("Authorization") userId: String
    ) : List<Gundam>

    @Multipart
    @POST("api_fadhlan.php")
    suspend fun postGundam(
        @Header("Authorization") userId: String,
        @Part("namaGundam") namaGundam: RequestBody,
        @Part("grade") grade: RequestBody,
        @Part image: MultipartBody.Part
    ):OpStatus

    @DELETE("api_fadhlan.php")
    suspend fun DeleteGundam(
        @Header("Authorization") userId: String,
        @Query("id") id: String,
    ): OpStatus
}

object GundamApi{
    val service: GundamApiService by lazy {
        retrofit.create(GundamApiService::class.java)
    }

    fun getGundamUrl(imageId: String): String{
        return "${BASE_URL}image.php? id=$imageId"
    }
}

enum class ApiStatus {LOADING, SUCCESS, FAILED}