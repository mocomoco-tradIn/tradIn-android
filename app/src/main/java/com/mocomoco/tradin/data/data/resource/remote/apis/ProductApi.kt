package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.data.dto.request_body.AddProductBody
import com.mocomoco.tradin.data.data.dto.response.ImageUrlDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductApi {
    @POST("/api/v1/product")
    suspend fun postUploadProduct(body: AddProductBody): Response<Unit>

    @Multipart
    @POST("/api/v1/file/image")
    suspend fun postUploadImage(@Part images: MultipartBody.Part): Response<ImageUrlDto>
}