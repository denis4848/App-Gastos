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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
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

    // Observing the data from the ViewModel as LiveData
    val allItems by viewModel.obtenerTodasLasTransacciones().observeAsState(emptyList())
    val gastos by viewModel.obtenerTransaccionesPorTipo("Gasto").observeAsState(emptyList())
    val ingresos by viewModel.obtenerTransaccionesPorTipo("Ingreso").observeAsState(emptyList())

    // State variables for controlling UI elements
    var showDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Gasto") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }


    // Main container box with background and padding
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
            // Header Text
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

            // List of items displayed in a LazyColumn
            LazyColumn {
                items(allItems) { transaction ->
                    ItemList(transaction, viewModel) {
                        viewModel.eliminarTransaccion(transaction)
                    }
                }
            }
        }

        // Floating button to open the dialog for adding a new item
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

        // Dialog for adding a new transaction
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
                        // Title of the dialog
                        Text(
                            text = "Añadir nuevo gasto",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Input fields for the new transaction
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Título") },
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

                        // Dropdown menu to select the type of transaction
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

                        // Buttons for cancel and confirm actions
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
                                    // Show error if any field is empty
                                    if (title.isBlank() || description.isBlank() || amount.isBlank()) {
                                        showErrorDialog = true
                                    } else {
                                        if (selectedOption == "Gasto"){
                                            viewModel.total -= amount.toDouble()
                                        }else{
                                            viewModel.total += amount.toDouble()
                                        }
                                        // Create a new transaction and add it to the ViewModel
                                        val item = Transaction(
                                            name = title,
                                            description = description,
                                            date = viewModel.obtenerFechaHoraActual(),
                                            amount = amount.toDouble(),
                                            total = viewModel.total,
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

        // Error dialog for missing fields
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

        // Auto-close the error dialog after 3 seconds
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
    // Determine the color based on the transaction type
    val colorGasto = if (transaction.type == "Gasto") "#FF0000" else "#00FF00"

    // Row for each transaction item in the list
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .background(Color(0xFFC4C4C4), shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 5.dp)
    ) {
        // Left section with date and total in a Column
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f).padding(5.dp) // Make this section take available space
        ) {
            // First Row with date and total aligned to the right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth() // Fill the available width
            ) {
                Text(
                    text = transaction.date.substring(0, 11),
                    color = Color(0xFF02144B),
                    fontSize = 12.sp,
                    fontWeight = FontWeight(weight = 880),
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = transaction.total.toString() + "€",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(weight = 400)
                )
            }

            // Second Row with name and amount aligned to the right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth() // Fill the available width
            ) {
                Text(
                    text = transaction.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(weight = 400),
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Text(
                    text = transaction.amount.toString() + "€",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(weight = 400),
                    color = Color(android.graphics.Color.parseColor(colorGasto))
                )
            }
        }

        // Right section with delete button
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
        }
    }
}

