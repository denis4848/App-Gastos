package model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateTimeModel {
    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaHoraActual(): String {
        val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())
        val format = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss")
        return currentDateTime.format(format)
    }
}
