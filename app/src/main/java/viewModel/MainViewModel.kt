package viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import database.TransaccionDao
import database.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.DateTimeModel

class MainViewModel(private val transaccionDao: TransaccionDao): ViewModel() {
    private val dateTimeModel = DateTimeModel()

    // LiveData para obtener todas las transacciones
    val todasLasTransacciones: LiveData<List<Transaction>> = transaccionDao.obtenerTodasLasTransacciones()

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaHoraActual(): String {
        return dateTimeModel.obtenerFechaHoraActual()
    }

    fun insertarTransaccion(transaccion: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transaccionDao.insertarTransaccion(transaccion)
        }}

    fun actualizarTransaccion(transaccion: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transaccionDao.actualizarTransaccion(transaccion)
        }
    }

    fun eliminarTransaccion(transaccion: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transaccionDao.eliminarTransaccion(transaccion)
        }
    }

    fun obtenerTodasLasTransacciones(): LiveData<List<Transaction>> {
        return liveData(Dispatchers.IO) {
            emitSource(transaccionDao.obtenerTodasLasTransacciones())
        }
    }

    fun obtenerTransaccionesPorTipo(tipo: String): LiveData<List<Transaction>> {
        return liveData(Dispatchers.IO) {
            emitSource(transaccionDao.obtenerTransaccionesPorTipo(tipo))
        }
    }
}