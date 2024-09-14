package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Aquí registras los TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaccionDao(): TransaccionDao
}