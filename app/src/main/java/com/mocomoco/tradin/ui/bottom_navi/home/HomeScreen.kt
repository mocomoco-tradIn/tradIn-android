package com.mocomoco.tradin.ui.bottom_navi.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocomoco.tradin.ui.theme.Blue1

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    Text(text = viewModel.a, modifier = Modifier.background(Blue1))
}