package database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransaccionDao {
    @Insert
    suspend fun insertarTransaccion(transaccion: Transaction)

    @Update
    suspend fun actualizarTransaccion(transaccion: Transaction)

    @Delete
    suspend fun eliminarTransaccion(transaccion: Transaction)

    @Query("SELECT * FROM transacciones ORDER BY date DESC")
    fun obtenerTodasLasTransacciones(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transacciones WHERE type = :tipo ORDER BY date DESC")
    fun obtenerTransaccionesPorTipo(tipo: TipoTransaccion): LiveData<List<Transaction>>
}
