package com.example.pixabayviewer.view.ui.detail

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object ImageSizeFormatter {
    private val formatter = DecimalFormat("##.##", DecimalFormatSymbols(Locale.ENGLISH)).apply {
        isDecimalSeparatorAlwaysShown = false
    }

    private const val MEGABYTES_POSTFIX = " Mb"
    private const val MEGA: Float = 1024F * 1024F

    fun formatMB(fileSizeInBytes: Int): String {
        return formatter.format(fileSizeInBytes / MEGA) + MEGABYTES_POSTFIX
    }
}