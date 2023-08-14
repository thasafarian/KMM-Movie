package com.alphadevdigital.movieapp.android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alphadevdigital.movieapp.android.ui.theme.secondaryDark

@Composable
fun CaptionBox(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.background(
            secondaryDark.copy(alpha = 0.5f)
        ).padding(8.dp),
        content = content
    )
}