package com.mocomoco.tradin.presentation.main.home

import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.model.Location
import com.mocomoco.tradin.model.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    val a = "asdf"
}


data class HomeState(
    val location: Location,
    val sortType: SortType,


) {
//    data class Event(
//        val
//    )
}

