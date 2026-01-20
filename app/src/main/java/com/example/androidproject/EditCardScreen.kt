
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.androidproject.AudioRequest
import com.example.androidproject.EMAIL
import com.example.androidproject.TOKEN
import com.example.androidproject.data.local.FlashCard
import com.example.androidproject.data.local.FlashCardDao
import com.example.androidproject.dataStore
import com.example.androidproject.network.NetworkService
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardScreen(
    flashCard: FlashCard,
    email: String,
    token: String,
    networkService: NetworkService,
    flashCardDao: FlashCardDao,
    changeMessage: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Create the player once and keep it as long as this screen is open
    val player = remember { ExoPlayer.Builder(context).build() }

    // 1. CRITICAL: Remember the original values as they were in the DB
    val originalEn = remember { flashCard.enCard ?: "" }
    val originalVn = remember { flashCard.vnCard ?: "" }

    var enText by remember { mutableStateOf(originalEn) }
    var vnText by remember { mutableStateOf(originalVn) }

    // Consistent filename logic using MD5
    fun String.toMd5(): String = java.security.MessageDigest.getInstance("MD5")
        .digest(this.toByteArray()).joinToString("") { "%02x".format(it) }
    val derivedName = remember(vnText) { "${vnText.toMd5()}.mp3" }

    val audioFileName = remember(vnText) { "${vnText.toMd5()}.mp3" }
    val audioFile = remember(audioFileName) { File(context.filesDir, audioFileName) }
    var audioExists by remember { mutableStateOf(audioFile.exists()) }

    // Add this inside EditCardScreen
    var emailState by rememberSaveable { mutableStateOf(email) }
    var tokenState by rememberSaveable { mutableStateOf(token) }

    LaunchedEffect(Unit) {
        context.dataStore.data.collect { preferences ->
            val savedEmail = preferences[EMAIL] ?: ""
            val savedToken = preferences[TOKEN] ?: ""
            if (savedEmail.isNotEmpty() && savedToken.isNotEmpty()) {
                emailState = savedEmail
                tokenState = savedToken
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    Column(/*...*/) {
        OutlinedTextField(value = enText, onValueChange = { enText = it }, label = { Text("English") })
        OutlinedTextField(value = vnText, onValueChange = { vnText = it }, label = { Text("Vietnamese") })

        OutlinedTextField(
            value = if (audioExists) derivedName else "",
            onValueChange = {},
            label = { Text("Audio File") },
            readOnly = true,
            modifier = Modifier
        )

        Button(onClick = {
            scope.launch {
                flashCardDao.updateFlashCard(
                    englishOld = originalEn,
                    vietnameseOld = originalVn,
                    englishNew = enText,
                    vietnameseNew = vnText,
                    // If audio was generated but not yet saved to DB, we save it here
                    audioFile = if (audioExists) audioFileName else null
                )
                changeMessage("Card updated")
                onBack()
            }
        }) { Text("Update flashcard") }

        Spacer(modifier = Modifier.height(16.dp))

        if (audioExists) {
            Button(onClick = { playAudio(player, context, audioFileName, changeMessage) }) {
                Text("Play audio")
            }

            Button(onClick = {
                if (audioFile.delete()) {
                    audioExists = false
                    changeMessage("Audio deleted")
                }
            }) { Text("Clean audio") }
        } else {
            // 3. THE GENERATE BUTTON (Shows when audio doesn't exist)
            Button(onClick = {
                scope.launch {
                    try {
                        if (emailState.isBlank() || tokenState.isBlank()) {
                            changeMessage("Error: Not logged in")
                            return@launch
                        }

                        val response = networkService.generateAudio(
                            request = AudioRequest(vnText, emailState, tokenState)
                        )

                        if (response.code == 200) {
                            val audioBytes = android.util.Base64.decode(response.message, android.util.Base64.DEFAULT)

                            // Save exactly like StudyCardScreen
                            val file = File(context.filesDir, derivedName)
                            file.writeBytes(audioBytes)

                            audioExists = file.exists() // This fills the text box
                            changeMessage("Audio generated successfully")
                        } else {
                            // This captures the 500 error from your screenshot
                            changeMessage("Server Error: ${response.code}")
                        }
                    } catch (e: Exception) {
                        changeMessage("Network Error: ${e.localizedMessage}")
                    }
                }
            }) {
                Text("Generate audio")
            }
        }
}
}

fun playAudio(
    player: ExoPlayer,
    context: Context,
    filename: String,
    changeMessage: (String) -> Unit
) {
    val file = File(context.filesDir, filename)

    if (!file.exists()) {
        changeMessage("Audio file not found")
        return
    }

    // Stop anything currently playing
    player.stop()
    player.clearMediaItems()

    val mediaItem = MediaItem.fromUri(file.toUri())
    player.setMediaItem(mediaItem)
    player.prepare()
    player.play()

    changeMessage("Playing audio...")
}
