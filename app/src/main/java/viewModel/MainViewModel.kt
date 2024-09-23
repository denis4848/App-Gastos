package viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import database.AppDatabase
import database.TransaccionDao
import database.Transaction
import model.DateTimeModel

class MainViewModel(private val transaccionDao: TransaccionDao) : ViewModel() {
    private val dateTimeModel = DateTimeModel()

    // LiveData para obtener todas las transacciones
    val todasLasTransacciones: LiveData<List<Transaction>> = transaccionDao.obtenerTodasLasTransacciones()


    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerFechaHoraActual(): String {
        return dateTimeModel.obtenerFechaHoraActual()
    }

    fun insertarTransaccion(transaccion: Transaction) {
        transaccionDao.insertarTransaccion(transaccion)
    }

    fun actualizarTransaccion(transaccion: Transaction) {
        transaccionDao.actualizarTransaccion(transaccion)
    }

    fun eliminarTransaccion(transaccion: Transaction) {
        transaccionDao.eliminarTransaccion(transaccion)
    }

    fun obtenerTodasLasTransacciones(): LiveData<List<Transaction>> {
        return transaccionDao.obtenerTodasLasTransacciones()
    }

    fun obtenerTransaccionesPorTipo(tipo: String): LiveData<List<Transaction>> {
        return transaccionDao.obtenerTransaccionesPorTipo(tipo)
    }

}