package model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeModel {
    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaHoraActual(): String {
        val currentDateTime = LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("dd-MM HH:mm")
        return currentDateTime.format(format)
    }
}