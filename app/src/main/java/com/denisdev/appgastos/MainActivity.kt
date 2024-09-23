package com.denisdev.appgastos

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import database.AppDatabase
import database.TransaccionDao
import database.TransaccionViewModelFactory
import view.MainView
import viewModel.MainViewModel

class MainActivity : ComponentActivity() {

    private lateinit var transaccionDao: TransaccionDao


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Inicializa la base de datos y el DAO
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "gastos_database" // Nombre de la base de datos
        ).build()
        transaccionDao = database.transaccionDao()

        val mainViewModel = ViewModelProvider(this, TransaccionViewModelFactory(transaccionDao)).get(MainViewModel::class.java)

        setContent {
            MainView(viewModel = mainViewModel)
        }
}
}