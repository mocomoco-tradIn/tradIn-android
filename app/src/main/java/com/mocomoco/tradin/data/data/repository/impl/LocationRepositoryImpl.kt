package com.mocomoco.tradin.data.data.repository.impl

import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.response.location.LocationDto
import com.mocomoco.tradin.data.data.repository.LocationRepository
import com.mocomoco.tradin.data.data.resource.remote.apis.LocationApi
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi
) : LocationRepository {
    override fun getAllLocationInfo(): LocationDto {
        return handleResponse(locationApi.getAllLocationInfo())
    }
}