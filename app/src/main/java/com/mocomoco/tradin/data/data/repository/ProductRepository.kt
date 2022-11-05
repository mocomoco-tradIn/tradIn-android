package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.AddProductBody
import com.mocomoco.tradin.data.data.dto.request_body.PutProductBody
import com.mocomoco.tradin.data.data.dto.response.ImageUrlDto
import com.mocomoco.tradin.data.data.dto.response.ProductDto
import com.mocomoco.tradin.data.data.dto.response.history.ProductHistoryDto
import com.mocomoco.tradin.data.data.dto.response.products.ProductsDto
import okhttp3.MultipartBody

interface ProductRepository {
    suspend fun postUploadProduct(body: AddProductBody)
    suspend fun postUploadImage(body: MultipartBody.Part): ImageUrlDto
    suspend fun getProducts(lastId: Int): ProductsDto
    suspend fun getProductDetails(id: Int): ProductDto
    suspend fun putProduct(body: PutProductBody)
    suspend fun deleteProduct(id: Int)
    suspend fun getHistory(): ProductHistoryDto
}