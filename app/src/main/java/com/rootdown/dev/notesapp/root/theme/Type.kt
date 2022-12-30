package com.rootdown.dev.notesapp.root.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rootdown.dev.notesapp.R


val fontModal = FontFamily(
    Font(R.font.indieflower)
)
val fontGarmentItem = FontFamily(
    Font(R.font.michromareg)
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fontModal,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = primaryText,
    ),
    h1 = TextStyle(
        fontFamily = fontModal,
        fontWeight = FontWeight.Bold,
        color = primaryText,
        fontSize = 24.sp
    ),
    body2 = TextStyle(
        color = primaryText,
        fontFamily = fontGarmentItem,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    h2 = TextStyle(
        fontFamily = fontGarmentItem,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        color = primaryText
    )
)