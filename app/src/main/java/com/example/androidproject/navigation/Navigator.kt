
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidproject.data.local.FlashCard
import com.example.androidproject.data.local.FlashCardDao
import com.example.androidproject.ui.add.AddCardScreen
import com.example.androidproject.ui.menu.HomeScreen
import com.example.androidproject.ui.search.SearchCardsScreen
import com.example.androidproject.ui.study.StudyCardsScreen
import kotlinx.coroutines.launch


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(
    navController: NavHostController,
    flashCardDao: FlashCardDao
) {
    var message by remember { mutableStateOf("") }
    val changeMessage: (String) -> Unit = { message = it }

    // Navigation lambdas
    val navigateToAddCard = { navController.navigate("add_card") }
    val navigateToStudyCards = { navController.navigate("study_cards") }
    val navigateToSearchCards = { navController.navigate("search_cards") }

    // --- DB-backed flashcards state ---
    var flashCards by remember { mutableStateOf<List<FlashCard>>(emptyList()) }
    val scope = rememberCoroutineScope()

    // Load all cards once when Navigator is first shown
    LaunchedEffect(Unit) {
        flashCards = flashCardDao.getAll()
    }

    // Callback to save new card
    val onSaveCard: (String, String) -> Unit = { en, vn ->
        scope.launch {
            flashCardDao.insertAll(
                FlashCard(
                    uid = 0,
                    enCard = en,
                    vnCard = vn
                )
            )
            // Reload list after insert
            flashCards = flashCardDao.getAll()
            changeMessage("Card saved!")
        }
    }

    var selectedItem by remember { mutableStateOf<FlashCard?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Teaching Mobile 26"
                        )
                    }
                },
                navigationIcon = {
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    if (currentRoute != "home") {
                        Button(
                            modifier = Modifier.semantics { contentDescription = "navigateBack" },
                            onClick = {
                                navController.navigateUp()
                            }) {
                            Text("Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics {
                                contentDescription = "Message"
                            },
                        textAlign = TextAlign.Center,
                        text = message
                    )
                })
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    changeMessage = changeMessage,
                    navigateToAddCard = navigateToAddCard,
                    navigateToStudyCards = navigateToStudyCards,
                    navigateToSearchCards = navigateToSearchCards
                )
            }
            composable("add_card") {
                AddCardScreen(
                    changeMessage = changeMessage,
                    onSaveCard = onSaveCard
                )
            }
            composable("study_cards") {
                StudyCardsScreen(
                    flashCards = flashCards,
                    changeMessage = changeMessage
                )
            }
            composable("search_cards") {
                SearchCardsScreen(
                    flashCards = flashCards,
                    selectedItem = selectedItem,
                    onSelectItem = { card -> selectedItem = card },
                    changeMessage = changeMessage
                )
            }
        }
    }
}
