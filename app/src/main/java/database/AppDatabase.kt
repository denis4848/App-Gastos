package database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Transaction::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaccionDao(): TransaccionDao

}