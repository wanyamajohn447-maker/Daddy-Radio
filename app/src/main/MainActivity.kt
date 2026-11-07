package com.daddyliveradio.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

private const val STREAM_URL = "https://YOUR-STREAM-URL-HERE"

private val Black = Color(0xFF080808)
private val Panel = Color(0xFF171717)
private val Gold = Color(0xFFFFC928)
private val Red = Color(0xFFE50919)
private val Muted = Color(0xFFB8B8B8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val player = ExoPlayer.Builder(this).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(),
                true
            )
        }
        setContent {
            DaddyRadioScreen(
                player = player,
                onMessage = { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
            )
        }
    }
}

@Composable
private fun DaddyRadioScreen(
    player: ExoPlayer,
    onMessage: (String) -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("READY TO CONNECT") }
    var streamConfigured by remember { mutableStateOf(false) }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
                status = if (playing) "ON AIR • LIVE STREAMING" else "STREAM PAUSED"
            }
            override fun onPlayerError(error: PlaybackException) {
                isPlaying = false
                status = "CONNECTION ERROR — CHECK STREAM URL"
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Black,
            surface = Panel,
            primary = Gold,
            secondary = Red
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Black) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF290407), Black, Color(0xFF111111))
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("DADDY", color = Color.White, fontSize = 29.sp, fontWeight = FontWeight.Black)
                    Text("LIVE RADIO", color = Gold, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
                }
                Text(
                    "MUSIC  •  ENTERTAINMENT  •  GOOD VIBES",
                    color = Muted,
                    fontSize = 10.sp,
                    letterSpacing = 1.4.sp
                )

                Spacer(Modifier.height(28.dp))
                Box(
                    modifier = Modifier
                        .size(184.dp)
                        .background(
                            Brush.radialGradient(listOf(Color(0xFF54200A), Color(0xFF1B0808), Black)),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Headphones, contentDescription = null, tint = Gold, modifier = Modifier.size(94.dp))
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp)
                            .background(Red, RoundedCornerShape(30.dp))
                            .padding(horizontal = 13.dp, vertical = 5.dp)
                    ) {
                        Text("DADDY LIVE", color = Color.White, fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }

                Spacer(Modifier.height(22.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFF30070A), RoundedCornerShape(30.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Box(Modifier.size(9.dp).background(if (isPlaying) Color(0xFF28D17C) else Gold, CircleShape))
                    Spacer(Modifier.width(9.dp))
                    Text(status, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(22.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Panel),
                    shape = RoundedCornerShape(22.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.7f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("YOU'RE LISTENING TO", color = Muted, fontSize = 10.sp, letterSpacing = 1.5.sp)
                        Spacer(Modifier.height(5.dp))
                        Text("DADDY LIVE RADIO", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
                        Text("Music never stops.", color = Gold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(22.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(25) { i ->
                                val heights = listOf(10, 18, 27, 15, 35, 22, 42, 18, 30, 12)
                                Box(
                                    Modifier
                                        .padding(horizontal = 2.dp)
                                        .width(5.dp)
                                        .height((heights[i % heights.size]).dp)
                                        .background(Gold.copy(alpha = if (isPlaying) 1f else 0.55f), RoundedCornerShape(3.dp))
                                )
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        FilledIconButton(
                            onClick = {
                                if (!STREAM_URL.startsWith("https://") || STREAM_URL.contains("YOUR-STREAM")) {
                                    onMessage("Add your radio stream URL in MainActivity.kt first.")
                                    status = "STREAM URL REQUIRED"
                                } else if (player.isPlaying) {
                                    player.pause()
                                } else {
                                    if (player.mediaItemCount == 0) {
                                        player.setMediaItem(MediaItem.fromUri(STREAM_URL))
                                        player.prepare()
                                    }
                                    player.play()
                                }
                            },
                            modifier = Modifier.size(76.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Red,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                                contentDescription = if (isPlaying) "Pause live radio" else "Play live radio",
                                modifier = Modifier.size(42.dp)
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(if (isPlaying) "TAP TO PAUSE" else "TAP TO LISTEN LIVE", color = Gold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(Modifier.height(20.dp))
                Text("STAY CONNECTED", color = Gold, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.5.sp)
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickAction("Request a song", Icons.Filled.MusicNote, Modifier.weight(1f)) {
                        onMessage("Add your song-request WhatsApp number to enable requests.")
                    }
                    QuickAction("Share station", Icons.Filled.Share, Modifier.weight(1f)) {
                        onMessage("Share link can be connected when your app is published.")
                    }
                    QuickAction("Follow us", Icons.Filled.Favorite, Modifier.weight(1f)) {
                        onMessage("Add your social media links to complete this section.")
                    }
                }

                Spacer(Modifier.weight(1f))
                Text(
                    "DADDY LIVE RADIO  •  YOUR SOUND, YOUR STATION",
                    color = Muted,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}

@Composable
private fun QuickAction(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(15.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Gold.copy(alpha = 0.65f)),
        contentPadding = PaddingValues(5.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(icon, contentDescription = null, tint = Gold, modifier = Modifier.size(22.dp))
            Spacer(Modifier.height(5.dp))
            Text(label, color = Color.White, fontSize = 10.sp, textAlign = TextAlign.Center, lineHeight = 12.sp)
        }
    }
}
