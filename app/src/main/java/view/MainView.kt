package view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import database.Transaction
import kotlinx.coroutines.delay
import viewModel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainView(viewModel: MainViewModel) {

    // Observa allItems como LiveData
    val allItems by viewModel.obtenerTodasLasTransacciones().observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Gasto") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFCFCFCF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Registro gastos",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyColumn {
                items(allItems) { transaction ->
                    ItemList(transaction, viewModel) {
                        viewModel.eliminarTransaccion(transaction)
                    }
                }
            }

        }

        // Botón flotante para abrir el diálogo
        FloatingActionButton(
            onClick = { showDialog = true },
            contentColor = Color.White,
            containerColor = Color(0xFF2C2C2C),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(40.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }

        // Diálogo para añadir nuevo gasto
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color= Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Añadir nuevo gasto",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Título") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Descripción") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = amount,
                            onValueChange = { amount = it },
                            label = { Text("Precio") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { expanded = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE0E0E0),
                                    contentColor = Color.Black
                                ),
                                shape = MaterialTheme.shapes.medium,
                                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = selectedOption)
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown",
                                        tint = Color.Black
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Gasto") },
                                    onClick = {
                                        selectedOption = "Gasto"
                                        expanded = false
                                    },
                                    modifier = Modifier.background(Color(0xFFF5F5F5))
                                )
                                DropdownMenuItem(
                                    text = { Text("Ingreso") },
                                    onClick = {
                                        selectedOption = "Ingreso"
                                        expanded = false
                                    },
                                    modifier = Modifier.background(Color(0xFFF5F5F5))
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(
                                onClick = { showDialog = false }
                            ) {
                                Text(text = "Cancelar")
                            }

                            Button(
                                onClick = {
                                    if (title.isBlank() || description.isBlank() || amount.isBlank()) {
                                        showErrorDialog = true
                                    } else {
                                        val item = Transaction(
                                            name = title,
                                            description = description,
                                            date = viewModel.obtenerFechaHoraActual(),
                                            amount = amount.toDouble(),
                                            type = selectedOption
                                        )
                                        viewModel.insertarTransaccion(item)
                                        showDialog = false
                                        title = ""
                                        description = ""
                                        amount = ""
                                    }
                                }
                            ) {
                                Text(text = "Añadir")
                            }
                        }
                    }
                }
            }
        }

        // Diálogo de error
        AnimatedVisibility(
            visible = showErrorDialog,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error") },
                text = { Text("Todos los campos son obligatorios.") },
                confirmButton = {}
            )
        }

        // Cerrar el diálogo automáticamente después de 3 segundos
        LaunchedEffect(key1 = showErrorDialog) {
            if (showErrorDialog) {
                delay(500)
                showErrorDialog = false
            }
        }

    }
}





@Composable
fun ItemList(
    transaction: Transaction,
    viewModel: MainViewModel,
    onDeleteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween, // Add this line
        modifier = Modifier.fillMaxWidth() // Add this line
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = transaction.date,
                color = Color(0xFF02144B),
                fontSize = 12.sp,
                fontWeight = FontWeight(weight = 880),
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = transaction.name,
                fontSize = 20.sp,
                fontWeight = FontWeight(weight = 400),
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
        }
    }
}

