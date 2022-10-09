package com.mocomoco.tradin.ui.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    Text(text = viewModel.a)
}