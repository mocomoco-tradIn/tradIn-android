package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.data.dto.response.location.LocationDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationApi {
    @GET("/api/v1/region") // 한번만 호출할 것
    fun getAllLocationInfo(): Response<LocationDto>
}