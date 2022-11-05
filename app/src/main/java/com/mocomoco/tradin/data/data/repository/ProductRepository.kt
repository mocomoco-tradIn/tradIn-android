package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.AddProductBody
import com.mocomoco.tradin.data.data.dto.response.ImageUrlDto
import okhttp3.MultipartBody
import retrofit2.Response

interface ProductRepository {
    suspend fun postUploadProduct(body: AddProductBody)
    suspend fun postUploadImage(body: MultipartBody.Part): ImageUrlDto
}