package com.example.androidproject

import android.content.Context
import android.util.Base64
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.androidproject.data.local.FlashCard
import com.example.androidproject.data.local.FlashCardDao
import com.example.androidproject.network.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun StudyCardsScreen(
//    lesson: List<FlashCard>,
//    changeMessage: (String) -> Unit
//){
//
//    if (lesson.isEmpty()) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("No flashcards available.")
//        }
//        return
//    }
//    var index by remember { mutableStateOf(0) }
//    var showVietnamese by remember { mutableStateOf(false) }
//
//    val current = lesson[index]
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.spacedBy(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        val en = current.enCard ?: ""
//        val vn = current.vnCard ?: ""
//
//        Text(
//            text = if (showVietnamese) vn else en,
//            modifier = Modifier.clickable {
//                showVietnamese = !showVietnamese
//            }
//        )
//
//        // Show Next button only when Vietnamese is shown
//
//        if (showVietnamese && vn.isNotBlank()) {
//            Button(onClick = {
//                index = (index + 1) % lesson.size
//                showVietnamese = false
//            }) {
//                Text("Next")
//            }
//        }
//    }
//
//}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyCardScreen(
    changeMessage: (String) -> Unit,
    flashCardDao: FlashCardDao,
    networkService: NetworkService,
    email: String,
    token: String,
    coroutineScope: CoroutineScope? = null
) {
    val context = LocalContext.current
    val scope = coroutineScope ?: rememberCoroutineScope()
    var lesson by remember { mutableStateOf<List<FlashCard>>(emptyList()) }
    var currentFlashCard by remember { mutableIntStateOf(0) }
    var currentLanguage by remember { mutableStateOf("EN") }

    var email by rememberSaveable() { mutableStateOf("") }
    var token by rememberSaveable { mutableStateOf("") }

    val player = remember { ExoPlayer.Builder(context).build() }

    LaunchedEffect(Unit) {
        context.dataStore.data.collect { preferences ->
            val savedEmail = preferences[EMAIL] ?: ""
            val savedToken = preferences[TOKEN] ?: ""

            if (savedEmail.isNotEmpty() && savedToken.isNotEmpty()) {
                email = savedEmail
                token = savedToken
                changeMessage("Logged in as $email")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { player.release() }
    }
    fun String.toMd5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun saveAudioToInternalStorage(context: Context, audioData: ByteArray, filename: String): File {
        val file = File(context.filesDir, filename)
        FileOutputStream(file).use { fos ->
            fos.write(audioData)
        }
        return file
    }
    fun handleAudio(word: String) {
        // --- USING YOUR UTILS ---
        val fileName = "${word.toMd5()}.mp3"
        val file = File(context.filesDir, fileName)

        scope.launch {
            try {
                if (!file.exists()) {
                    if (email.isBlank() || token.isBlank()) {
                        changeMessage("Login required for audio")
                        return@launch
                    }

                    changeMessage("Downloading Audio...")
                    val response = networkService.generateAudio(
                        request = AudioRequest(word, email, token)
                    )

                    if (response.code == 200) {
                        // Decode Base64 string from API response
                        val audioBytes = Base64.decode(response.message, Base64.DEFAULT)

                        // --- USING YOUR UTILS ---
                        saveAudioToInternalStorage(context, audioBytes, fileName)
                    } else {
                        changeMessage("Server Error: ${response.message}")
                        return@launch
                    }
                }

                // Playback logic
                if (file.exists()) {
                    val mediaItem = MediaItem.fromUri(file.absolutePath.toUri())
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                    changeMessage("Playing pronunciation")
                }

            } catch (e: Exception) {
                changeMessage("Audio error: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        changeMessage("Study Cards")
        lesson = flashCardDao.getLesson(3)
    }

    if (lesson.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No flashcards available.")
        }
        return
    }

    val card = lesson[currentFlashCard]

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (currentLanguage == "EN") card.enCard ?: "" else card.vnCard ?: "",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .clickable {
                    currentLanguage = if (currentLanguage == "EN") "VI" else "EN"

                    // If switching to Vietnamese, try to play audio
                    if (currentLanguage == "VI") {
                        // Safe call: only play if vietnameseCard is not null
                        card.vnCard?.let { handleAudio(it) }
                    }
                }
                .padding(24.dp)
        )

        if (currentLanguage == "VI") {
            Button(onClick = {
                if (currentFlashCard == lesson.lastIndex) {
                    lesson = lesson.shuffled()
                    currentFlashCard = 0
                } else {
                    currentFlashCard += 1
                }
                currentLanguage = "EN"
                changeMessage("Next card")
            }) {
                Text("Next")
            }
        }
    }
}
