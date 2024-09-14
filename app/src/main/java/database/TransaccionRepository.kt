package database

import androidx.lifecycle.LiveData

class TransaccionRepository(private val transaccionDao: TransaccionDao) {

    val todasLasTransacciones: LiveData<List<Transaction>> = transaccionDao.obtenerTodasLasTransacciones()

    suspend fun insertar(transaccion: Transaction) {
        transaccionDao.insertarTransaccion(transaccion)
    }

    suspend fun actualizar(transaccion: Transaction) {
        transaccionDao.actualizarTransaccion(transaccion)
    }

    suspend fun eliminar(transaccion: Transaction) {
        transaccionDao.eliminarTransaccion(transaccion)
    }

    fun obtenerTransaccionesPorTipo(tipo: TipoTransaccion): LiveData<List<Transaction>> {
        return transaccionDao.obtenerTransaccionesPorTipo(tipo)
    }
}
