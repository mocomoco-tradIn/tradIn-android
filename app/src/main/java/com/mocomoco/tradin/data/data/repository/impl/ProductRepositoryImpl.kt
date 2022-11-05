package com.mocomoco.tradin.data.data.repository.impl

import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.request_body.AddProductBody
import com.mocomoco.tradin.data.data.dto.response.ImageUrlDto
import com.mocomoco.tradin.data.data.repository.ProductRepository
import com.mocomoco.tradin.data.data.resource.remote.apis.ProductApi
import okhttp3.MultipartBody
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRepository {
    override suspend fun postUploadProduct(body: AddProductBody) {
        return handleResponse(productApi.postUploadProduct(body))
    }

    override suspend fun postUploadImage(body: MultipartBody.Part): ImageUrlDto {
        return handleResponse(productApi.postUploadImage(body))
    }
}
