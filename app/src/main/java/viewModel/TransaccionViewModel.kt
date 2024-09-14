package viewModel

import androidx.lifecycle.*
import database.TipoTransaccion
import database.TransaccionRepository
import database.Transaction
import kotlinx.coroutines.launch

class TransaccionViewModel(private val repository: TransaccionRepository) : ViewModel() {

    val todasLasTransacciones: LiveData<List<Transaction>> = repository.todasLasTransacciones


    fun insertar(transaccion: Transaction) = viewModelScope.launch {
        repository.insertar(transaccion)
    }

    fun actualizar(transaccion: Transaction) = viewModelScope.launch {
        repository.actualizar(transaccion)
    }

    fun eliminar(transaccion: Transaction) = viewModelScope.launch {
        repository.eliminar(transaccion)
    }

    fun obtenerTransaccionesPorTipo(tipo: TipoTransaccion): LiveData<List<Transaction>> {
        return repository.obtenerTransaccionesPorTipo(tipo)
    }
}

class TransaccionViewModelFactory(private val repository: TransaccionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransaccionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransaccionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
