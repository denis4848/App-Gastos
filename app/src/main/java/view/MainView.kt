package view

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import viewModel.MainViewModel

val names = listOf("Camiseta Primark", "Helado", "Cocacola", "Burger", "Zapatillas")


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainView(viewModel: MainViewModel) {

    // Obtén el ViewModel
    val viewModel: MainViewModel = viewModel

    // Llama al método para obtener la fecha y hora actual
    val time = viewModel.obtenerFechaHoraActual()

    // Estados para los campos del popup
    var showPopup by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) } // Controlar el desplegable
    var selectedOption by remember { mutableStateOf("Gasto") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize() // Llenar el espacio disponible
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
            } // Box3

            LazyColumn {
                items(names) { name -> ItemList(name, time) }
            }
        } // Column

        // Botón flotante para abrir el popup
        FloatingActionButton(
            onClick = { showPopup = true },
            contentColor = Color.White,
            containerColor = Color(0xFF2C2C2C),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(40.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }

        // Mostrar el Popup cuando showPopup sea verdadero
        if (showPopup) {
            Popup(
                onDismissRequest = { showPopup = false }, // Cierra el popup cuando se haga clic fuera
                alignment = Alignment.Center // Centra el popup en la pantalla
            ) {
                // Contenido del popup
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.9f) // Ancho del 90% de la pantalla
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = Color.White,
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

                        // Aquí puedes añadir más campos, como TextField para entrada de texto
                        OutlinedTextField(
                            value = "", // Aquí puedes agregar el estado para el campo
                            onValueChange = { /* Actualiza el estado con el valor ingresado */ },
                            label = { Text("Titulo") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = "", // Aquí puedes agregar el estado para el campo
                            onValueChange = { /* Actualiza el estado con el valor ingresado */ },
                            label = { Text("Descripción") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = "", // Otro campo de texto para cantidad, etc.
                            onValueChange = { /* Actualiza el estado con el valor ingresado */ },
                            label = { Text("Cantidad") },
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
                                    modifier = Modifier.background(Color(0xFFF5F5F5)) // Fondo de los ítems del menú
                                )
                                DropdownMenuItem(
                                    text = { Text("Ingreso") },
                                    onClick = {
                                        selectedOption = "Ingreso"
                                        expanded = false
                                    },
                                    modifier = Modifier.background(Color(0xFFF5F5F5)) // Fondo de los ítems del menú
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(
                                onClick = { showPopup = false }
                            ) {
                                Text(text = "Cancelar")
                            }

                            Button(
                                onClick = {
                                    // Acción para añadir el gasto
                                    showPopup = false // Cierra el popup
                                }
                            ) {
                                Text(text = "Añadir")
                            }
                        }
                    }
                }
            }
        }
    } // Box1
}









@Composable
fun ItemList(name: String, time: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = time,
            color = Color(0xFF02144B),
            fontSize = 12.sp,
            fontWeight = FontWeight(weight = 880), modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight(weight = 400),
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewMinView(){
    MainView(MainViewModel())
}
