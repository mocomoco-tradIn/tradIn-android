package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.common.Constants.PAGE_SIZE
import com.mocomoco.tradin.data.data.dto.request_body.PutProductBody
import com.mocomoco.tradin.data.data.dto.request_body.RefreshTokenBody
import com.mocomoco.tradin.data.data.dto.response.ProductDto
import com.mocomoco.tradin.data.data.dto.response.history.ProductHistoryDto
import com.mocomoco.tradin.data.data.dto.response.products.ProductsDto
import com.mocomoco.tradin.data.data.dto.response.refresh_token.RefreshTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RefreshTokenApi {
    @POST("/api/v1/auth/token/refresh")
    suspend fun postRefreshAccessToken(@Body body: RefreshTokenBody): Response<RefreshTokenDto>

}
