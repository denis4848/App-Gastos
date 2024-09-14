package viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import model.DateTimeModel

class MainViewModel : ViewModel() {
    private val dateTimeModel = DateTimeModel()

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaHoraActual(): String {
        return dateTimeModel.obtenerFechaHoraActual()
    }

}