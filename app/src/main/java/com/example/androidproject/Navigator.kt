
//import com.example.androidproject.ShowCardScreen
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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.toRoute
import com.example.androidproject.AddCardScreen
import com.example.androidproject.FlashCardList
import com.example.androidproject.HomeScreen
import com.example.androidproject.LoginScreen
import com.example.androidproject.SearchCardsScreen
import com.example.androidproject.StudyCardScreen
import com.example.androidproject.TokenScreen
import com.example.androidproject.data.local.FlashCard
import com.example.androidproject.data.local.FlashCardDao
import com.example.androidproject.network.NetworkService
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
object AddCardDestination

@Serializable
object SearchCardDestination

@Serializable
data class StudyCardDestination(val email: String, val token: String)

@Serializable
object ShowCardDestination

@Serializable
object LoginDestination

@Serializable
data class EditCardRoute(val english: String, val vietnamese: String)

@Serializable
data class TokenDestination(val email: String)

@Serializable
object ListCardDestination


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(
    navController: NavHostController,
    flashCardDao: FlashCardDao,
    networkService: NetworkService
) {
    var coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    // --- Navigation lambdas ---

    var message by remember { mutableStateOf("") }
    val changeMessage: (String) -> Unit = { message = it }

    // 1. Observe the database in real-time
    // Whenever the DB updates, 'flashCards' will automatically change
    val flashCards by flashCardDao.getAllFlow().collectAsState(initial = emptyList())

    var searchResults by remember { mutableStateOf<List<FlashCard>?>(null) }

    val displayList = searchResults ?: flashCards

    val scope = rememberCoroutineScope()

    // Load all cards once when Navigator is first shown
//    LaunchedEffect(Unit) {
//        flashCards = flashCardDao.getAll()
//    }

    // Callback to save new card
    val onSaveCard: (String, String) -> Unit = { en, vn ->
        coroutineScope.launch {
            val existing = flashCardDao.findByCards(en, vn)
            if (existing != null) {
                changeMessage("Card already exists!")
                return@launch
            }
            flashCardDao.insertAll(FlashCard(uid = 0, enCard = en, vnCard = vn, audioFile = null))
            // NO NEED to reload flashCards manually. Flow handles it.
            changeMessage("Card saved!")
        }
    }

    val onSearchCards: (String, String, Boolean, Boolean) -> Unit =
        { en, vn, enExact, vnExact ->
            scope.launch {
                // Prepare the search strings based on the checkboxes
                // If NOT checked, add % for "contains" search
                val searchEn = if (enExact) en else "%$en%"
                val searchVn = if (vnExact) vn else "%$vn%"

                val result = when {
                    // If user typed in both fields
                    en.isNotEmpty() && vn.isNotEmpty() -> flashCardDao.searchBoth(searchEn, searchVn)
                    // If user typed only English
                    en.isNotEmpty() -> flashCardDao.searchEnglish(searchEn)
                    // If user typed only Vietnamese
                    vn.isNotEmpty() -> flashCardDao.searchVietnamese(searchVn)
                    // If both empty, reset to show all
                    else -> null
                }

                searchResults = result
                changeMessage(if (result == null) "Showing all" else "${result.size} results found")
            }
        }


    // TYPE-SAFE navigation lambdas ✅
    val navigateToToken = { navController.navigate(TokenDestination) }

    val navigateToAddCard = { navController.navigate(AddCardDestination) }

    val navigateToSearchCard = { navController.navigate(SearchCardDestination) }

    val navigateToLogin = { navController.navigate(LoginDestination) }
    val navigateToStudy = { navController.navigate(StudyCardDestination(email, token)) }


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
                    navigateToStudyCards = navigateToStudy,
                    navigateToSearchCards = navigateToSearchCard,
                    navigateToLogin = navigateToLogin
                )
            }
            composable<AddCardDestination> {
                AddCardScreen(
                    changeMessage = changeMessage,
                    onSaveCard = onSaveCard
                )
            }
            composable<StudyCardDestination> { backStackEntry ->
                val route = backStackEntry.toRoute<StudyCardDestination>()

                StudyCardScreen(
                    changeMessage = changeMessage,
                    flashCardDao = flashCardDao,
                    networkService = networkService,
                    email = route.email,
                    token = route.token,
                    coroutineScope = coroutineScope
                )
            }
            composable<SearchCardDestination> {
                SearchCardsScreen(
                    // Pass searchResults if the list should show specific search hits
                    // or just navigate to ListCardDestination
                    navigateToList = { navController.navigate(ListCardDestination) },
                    changeMessage = changeMessage,
                    onSearchCards = onSearchCards
                )
            }
            composable<ListCardDestination> {
                FlashCardList(
                    // Decide here: do you want to show ALL cards or SEARCH results?
                    // Usually, ListCardDestination shows the whole DB:
                    flashCards = displayList,
                    onDelete = { card ->
                        coroutineScope.launch {
                            flashCardDao.deleteFlashCard(card.enCard ?: "", card.vnCard ?: "")
                            changeMessage("Card deleted.")
                            // NO NEED to manually reload.
                        }
                    },
                    onEdit = { card ->
                        navController.navigate(EditCardRoute(card.enCard ?: "", card.vnCard ?: ""))
                    }
                )
            }
            composable<TokenDestination> {
                TokenScreen(
                    email = email,
                    changeMessage = changeMessage,
                    navigateToHome = { enteredToken ->

                        // 🔥 THIS IS THE MISSING LINE
                        token = enteredToken

                        changeMessage("Token saved")
                        navController.navigate(HomeDestination)
                    }
                )
            }

            composable<LoginDestination> {
                LoginScreen(
                    changeMessage = changeMessage,
                    networkService = networkService,
                    navigateToToken = { enteredEmail ->
                        email = enteredEmail // <--- Add this line to save the email immediately
                        navController.navigate(TokenDestination(enteredEmail))
                    }
                )
            }

            composable<EditCardRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<EditCardRoute>()

                // Find the current card in your list or database
                val cardToEdit = flashCards.find { it.enCard == route.english && it.vnCard == route.vietnamese }

                if (cardToEdit != null) {
                    EditCardScreen(
                        flashCard = cardToEdit,
                        email = email,
                        token = token,
                        networkService = networkService,
                        flashCardDao = flashCardDao,
                        changeMessage = changeMessage,
                        onBack = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}


