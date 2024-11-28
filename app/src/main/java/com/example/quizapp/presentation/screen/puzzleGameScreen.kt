import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizapp.R
import kotlinx.coroutines.delay

@Composable
fun PuzzleScreen(navController: NavController) {
    // Timer variables
    var timer by remember { mutableStateOf(150) } // 2:30 min timer
    val totalTime = 150

    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            timer -= 1
        }
    }

    // Calculer le temps restant en minutes et secondes
    val minutes = timer / 60
    val seconds = timer % 60

    val imagePainter: Painter = painterResource(id = R.drawable.puzzle3)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Timer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp)
            )
            // Ajouter ici des icônes ou d'autres éléments comme le score
            IconButton(onClick = { /* action pour fermer ou retourner à l'écran précédent */ }) {
                Icon(painter = painterResource(id = R.drawable.x), contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Image du puzzle découpée en 3x3
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Puzzle Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Instructions (en arabe dans l'exemple)
        BasicText(text = "إنزل على مرتفع بش تحركو", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Placez ici la logique de puzzle
        // Par exemple, un Row de cartes représentant les pièces du puzzle (divisées en 9 par exemple)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Remplacer ces Card() par vos pièces de puzzle dynamiques
            repeat(9) { index ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = (index + 1).toString(), style = MaterialTheme.typography.headlineLarge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ajouter un bouton de soumission
        Button(onClick = { /* Logique pour vérifier si le puzzle est résolu */ }) {
            Text(text = "Vérifier")
        }
    }
}
