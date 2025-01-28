package com.example.nexus.framework.service.local.until

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun Long.toFormattedTime(): String {
    val date = Date(this)  // Convertendo o timestamp para um objeto Date
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)  // Retorna a hora formatada como HH:mm
}