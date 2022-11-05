package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.common.Constants
import com.mocomoco.tradin.data.data.dto.request_body.AddProductBody
import com.mocomoco.tradin.data.data.dto.request_body.PutProductBody
import com.mocomoco.tradin.data.data.dto.response.ImageUrlDto
import com.mocomoco.tradin.data.data.dto.response.ProductDto
import com.mocomoco.tradin.data.data.dto.response.history.ProductHistoryDto
import com.mocomoco.tradin.data.data.dto.response.products.ProductsDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {
    @POST("/api/v1/product")
    suspend fun postUploadProduct(@Body body: AddProductBody): Response<Unit>

    @Multipart
    @POST("/api/v1/file/image")
    suspend fun postUploadImage(@Part images: MultipartBody.Part): Response<ImageUrlDto>

    @GET("/api/v1/product/me")
    suspend fun getProducts(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int = Constants.PAGE_SIZE
    ): Response<ProductsDto>

    @GET("/api/v1/product/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): Response<ProductDto>

    @PUT("/api/v1/product")
    suspend fun putProduct(@Body body: PutProductBody): Response<Unit>

    @DELETE("/api/v1/product/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Unit>

    @GET("/api/v1/product/origin")
    suspend fun getHistory(): Response<ProductHistoryDto>
}