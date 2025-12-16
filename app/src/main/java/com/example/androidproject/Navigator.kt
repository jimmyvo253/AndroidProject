
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
import com.example.androidproject.AddCardScreen
import com.example.androidproject.HomeScreen
import com.example.androidproject.LoginScreen
import com.example.androidproject.SearchCardsScreen
import com.example.androidproject.ShowCardScreen
import com.example.androidproject.StudyCardsScreen
import com.example.androidproject.data.local.FlashCard
import com.example.androidproject.data.local.FlashCardDao
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
object AddCardDestination

@Serializable
object SearchCardDestination

@Serializable
object StudyCardDestination

@Serializable
object ShowCardDestination

@Serializable
object LoginDestination


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(
    navController: NavHostController,
    flashCardDao: FlashCardDao
) {
    //
    var lesson by remember { mutableStateOf<List<FlashCard>>(emptyList()) }
    // --- Navigation lambdas ---
    val navigateToLogin = { navController.navigate(LoginDestination) }

    var message by remember { mutableStateOf("") }
    val changeMessage: (String) -> Unit = { message = it }

    // --- DB-backed flashcards state ---
    var flashCards by remember { mutableStateOf<List<FlashCard>>(emptyList()) }

    // The card currently selected from SearchCardsScreen
    var selectedItem by remember { mutableStateOf<FlashCard?>(null) }

    // This will both REMEMBER the card and NAVIGATE
    val navigationSelectedItem: (FlashCard) -> Unit = { card ->
        selectedItem = card
        navController.navigate(ShowCardDestination)
    }

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

    // TYPE-SAFE navigation lambdas ✅
    val navigateToAddCard = { navController.navigate(AddCardDestination) }
    val navigateToStudyCards = { navController.navigate(StudyCardDestination) }
    val navigateToSearchCards = { navController.navigate(SearchCardDestination) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = navBackStackEntry != null && navController.previousBackStackEntry != null
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Teaching Mobile 26")
                    }
                },
                navigationIcon = {
                    if (canNavigateBack) {
                        Button(
                            modifier = Modifier.semantics { contentDescription = "navigateBack" },
                            onClick = { navController.navigateUp() }
                        ) {
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
            startDestination = HomeDestination
        ) {
            composable<HomeDestination> {
                HomeScreen(
                    changeMessage = changeMessage,
                    navigateToAddCard = navigateToAddCard,
                    navigateToStudyCards = navigateToStudyCards,
                    navigateToSearchCards = navigateToSearchCards,
                    navigateToLogin = navigateToLogin
                )
            }
            composable<AddCardDestination> {
                AddCardScreen(
                    changeMessage = changeMessage,
                    onSaveCard = onSaveCard
                )
            }
            composable<StudyCardDestination> {
                LaunchedEffect(Unit) {
                    lesson = flashCardDao.getLesson(3)
                    changeMessage("New lesson generated!")
                }
                StudyCardsScreen(
                    lesson = lesson,
                    changeMessage = changeMessage
                )
            }
            composable<SearchCardDestination> {
                SearchCardsScreen(
                    flashCards = flashCards,
                    navigationSelectedItem = navigationSelectedItem,
                    changeMessage = changeMessage
                )
            }
            composable<ShowCardDestination> {
                ShowCardScreen(
                    flashCard = selectedItem,
                    onDelete = { card ->
                        scope.launch {
                            flashCardDao.delete(card)
                            flashCards = flashCardDao.getAll()
                            changeMessage("Card deleted.")
                            navController.navigateUp()
                        }
                    }
                )
            }
            composable<LoginDestination> {
                LoginScreen(changeMessage = changeMessage)
            }
        }
    }
}
