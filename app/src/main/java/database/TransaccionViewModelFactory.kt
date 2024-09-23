package database
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import database.TransaccionDao
import viewModel.MainViewModel

class TransaccionViewModelFactory(private val transaccionDao: TransaccionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(transaccionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
