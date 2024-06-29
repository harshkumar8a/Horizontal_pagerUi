package com.example1.pager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example1.pager.screens.HorizontalPagerUI
import com.example1.pager.ui.theme.PagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PagerTheme {

                HorizontalPagerUI()
            }
        }
    }
}
