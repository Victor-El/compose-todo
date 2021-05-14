import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.MenuItem
import androidx.compose.ui.window.Popup

fun main() = Window(title = "Composa") {
    var todos by remember { mutableStateOf(mutableListOf<String>()) }
    var todoValue by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()

    MaterialTheme {
        Scaffold(scaffoldState = scaffoldState, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Todo Manager",
                    style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 35.sp, fontWeight = FontWeight.Bold),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = todoValue,
                        modifier = Modifier.weight(1f),
                        label = { Text("Enter todo") },
                        onValueChange = { text -> todoValue = text })
                    Button(onClick = {
                        if (todoValue.isNotEmpty()) {
                            todos.add(todoValue)
                            todoValue = ""
                            println(todoValue)
                        }
                    }) {
                        Text("Submit")
                    }
                }
                Spacer(modifier = Modifier.padding(all = 16.dp))
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    todos.forEachIndexed {pos, tItem ->
                        item {
                            TodoItem(tItem, pos) { text, index ->
                                todos = todos.filterIndexed {idx, _ -> idx != index } as MutableList<String>
                            }
                            Spacer(Modifier.padding(16.dp))
                        }
                    }
                    /*items(todos) { todo ->
                        Text(todo)
                    }*/
                }
            }
        }
    }
}

@Composable
fun TodoItem(text: String, index: Int, onClick: (text: String, index: Int) -> Unit) {
    Card(backgroundColor = MaterialTheme.colors.secondaryVariant) {
        Row(modifier = Modifier.fillMaxWidth().padding(all = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete", modifier = Modifier.clickable {
                onClick(text, index)
            })
        }
    }
}