package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.response.location.LocationDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationRepository {
    fun getAllLocationInfo(): LocationDto
}