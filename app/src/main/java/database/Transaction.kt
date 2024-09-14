package database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transacciones")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val date: Date,
    val amount: Double,
    val type: TipoTransaccion
)

enum class TipoTransaccion {
    INGRESO,
    GASTO
}
