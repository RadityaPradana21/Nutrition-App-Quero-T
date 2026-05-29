package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.QueroViewModel
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.drawBehind

// --- SCREEN 1: SPLASH SCREEN ---
@Composable
fun SplashScreen(onNext: () -> Unit) {
    // Pulse animation for the logo
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    var progressFloat by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(2200, easing = FastOutSlowInEasing)
        ) { value, _ ->
            progressFloat = value
        }
        delay(400)
        onNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFD7BD72), Color(0xFFFEFAF2))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative Leaves/Grain using Emojis (floating in absolute corners with low opacity)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.15f)
        ) {
            Text(
                text = "🌿",
                fontSize = 110.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 24.dp, y = 48.dp)
            )
            Text(
                text = "🌾",
                fontSize = 130.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 16.dp, y = (-80).dp)
            )
            Text(
                text = "🌸",
                fontSize = 76.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-10).dp, y = (-40).dp)
            )
        }

        // Center Content Area
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            // Brand Logo container with shadow and round border
            Box(
                modifier = Modifier
                    .size(144.dp * scale)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color(0xFF705C1B).copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🌾",
                        fontSize = 42.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "🌿",
                        fontSize = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Heading 1 - Plus Jakarta Sans Bold lookalike
            Text(
                text = "Quero-T",
                color = Color(0xFF241A03),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-1.4).sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Nutrisi Tepat, Hidup Sehat",
                color = Color(0xFF4C4639),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.4.sp,
                textAlign = TextAlign.Center
            )
        }

        // Bottom Loading Indicator Area
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 44.dp)
                .width(280.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Slider-like warm bread track
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF4E1B9))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressFloat)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFF3A72A), Color(0xFFD7BD72))
                            )
                        )
                )
            }

            Text(
                text = "Menyiapkan nutrisi terbaik Anda...",
                color = Color(0xFF4C4639),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF566314)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Mulai Sekarang 🌾", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}

// --- SCREEN 2: LOGIN ---
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterSelected: () -> Unit,
    onSellerLoginSelected: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        // Header - Top Branding Section
        Text(
            text = "Quero-T",
            fontSize = 32.sp,
            color = Color(0xFF705C1B),
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = (-0.56).sp
        )
        Text(
            text = "Nutrisi Tepat, Hidup Sehat",
            fontSize = 16.sp,
            color = Color(0xFF4C4639),
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.4.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Main Hero Illustration Section (Sourdough basket / healthy food illustration replica)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFFFFF2DC).copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            // Draw beautiful stylized overlapping organic shapes portraying healthy crops
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Background artistic blob
                drawCircle(
                    color = Color(0xFFD7BD72).copy(alpha = 0.3f),
                    radius = size.minDimension * 0.4f,
                    center = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 0.5f)
                )
                // Left decorative green leaf blob
                drawCircle(
                    color = Color(0xFF566314).copy(alpha = 0.15f),
                    radius = size.minDimension * 0.25f,
                    center = androidx.compose.ui.geometry.Offset(size.width * 0.35f, size.height * 0.45f)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Circle 1
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFF2DC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🌿", fontSize = 28.sp)
                    }
                    // Circle 2
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFF2DC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🍞", fontSize = 36.sp)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Keranjang Roti Organik 🌾",
                    color = Color(0xFF705C1B),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section - Login Form Container card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(32.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Email Field
                Column {
                    Text(
                        text = "Email",
                        fontSize = 14.sp,
                        color = Color(0xFF4C4639),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("nama@email.com") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color(0xFF566314))
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF2C2208),
                            unfocusedTextColor = Color(0xFF2C2208),
                            focusedContainerColor = Color(0xFFF5EDD8),
                            unfocusedContainerColor = Color(0xFFF5EDD8),
                            focusedBorderColor = Color(0xFF566314),
                            unfocusedBorderColor = Color.Transparent,
                            focusedPlaceholderColor = Color(0xFF7D7667),
                            unfocusedPlaceholderColor = Color(0xFF7D7667)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_email_input")
                    )
                }

                // Password Field
                Column {
                    Text(
                        text = "Password",
                        fontSize = 14.sp,
                        color = Color(0xFF4C4639),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        visualTransformation = if (isPasswordVisible) androidx.compose.ui.text.input.VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFF566314))
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Text(
                                    text = if (isPasswordVisible) "👁️" else "🙈",
                                    fontSize = 16.sp
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF2C2208),
                            unfocusedTextColor = Color(0xFF2C2208),
                            focusedContainerColor = Color(0xFFF5EDD8),
                            unfocusedContainerColor = Color(0xFFF5EDD8),
                            focusedBorderColor = Color(0xFF566314),
                            unfocusedBorderColor = Color.Transparent,
                            focusedPlaceholderColor = Color(0xFF7D7667),
                            unfocusedPlaceholderColor = Color(0xFF7D7667)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_password_input")
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Lupa Password?",
                        color = Color(0xFF7D7667),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { }
                    )
                }

                // Action Button Masuk
                Button(
                    onClick = onLoginSuccess,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF566314)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("masuk_button")
                ) {
                    Text(text = "Masuk", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                // Seller Login Option
                Button(
                    onClick = onSellerLoginSelected,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7BD72)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("masuk_seller_button")
                ) {
                    Text(text = "Masuk Sebagai Seller 🏪", color = Color(0xFF5E4B0A), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                // Divider and "Atau"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFCEC6B4))
                    Text(
                        text = "Atau",
                        color = Color(0xFF4C4639),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFCEC6B4))
                }

                // Google login option
                OutlinedButton(
                    onClick = onLoginSuccess,
                    border = BorderStroke(2.dp, Color(0xFFCEC6B4)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        GoogleIcon()
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Login dengan Google", color = Color(0xFF4C4639), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }

                // Signup Link
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Belum punya akun?", color = Color(0xFF4C4639), fontSize = 15.sp)
                        Text(
                            text = "Daftar",
                            color = Color(0xFFFDB033),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { onRegisterSelected() }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// Google SVG Icon as Local Inline Composable (Perfect Google Multi-Color design wheel)
@Composable
fun GoogleIcon() {
    Canvas(modifier = Modifier.size(20.dp)) {
        val r = size.minDimension / 2
        // Draw Google arcs/sectors of Blue, Green, Yellow, Red
        drawArc(Color(0xFFEA4335), startAngle = 180f, sweepAngle = 90f, useCenter = true)
        drawArc(Color(0xFFFBBC05), startAngle = 90f, sweepAngle = 90f, useCenter = true)
        drawArc(Color(0xFF34A853), startAngle = 0f, sweepAngle = 90f, useCenter = true)
        drawArc(Color(0xFF4285F4), startAngle = 270f, sweepAngle = 90f, useCenter = true)
        drawCircle(Color.White, radius = r * 0.5f)
    }
}

// --- SCREEN 3: REGISTER ---
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginSelected: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Simple Top Bar with Back option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onLoginSelected) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF241A03)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Quero-T",
                fontSize = 20.sp,
                color = Color(0xFF705C1B),
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Buat Akun Baru",
                fontSize = 24.sp,
                color = Color(0xFF2C2208),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Mulai perjalanan nutrisi organik Anda hari ini.",
                fontSize = 14.sp,
                color = Color(0xFF4C4639),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            // Form container card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 12.dp, shape = RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Full Name Input
                    Column {
                        Text(
                            text = "Nama Lengkap",
                            fontSize = 12.sp,
                            color = Color(0xFF4C4639),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = { Text("Masukkan nama lengkap") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF2C2208),
                                unfocusedTextColor = Color(0xFF2C2208),
                                focusedContainerColor = Color(0xFFFFECC6),
                                unfocusedContainerColor = Color(0xFFFFECC6),
                                focusedBorderColor = Color(0xFF566314),
                                unfocusedBorderColor = Color.Transparent,
                                focusedPlaceholderColor = Color(0xFF7D7667),
                                unfocusedPlaceholderColor = Color(0xFF7D7667)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Email Input
                    Column {
                        Text(
                            text = "Email",
                            fontSize = 12.sp,
                            color = Color(0xFF4C4639),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("contoh@email.com") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF2C2208),
                                unfocusedTextColor = Color(0xFF2C2208),
                                focusedContainerColor = Color(0xFFFFECC6),
                                unfocusedContainerColor = Color(0xFFFFECC6),
                                focusedBorderColor = Color(0xFF566314),
                                unfocusedBorderColor = Color.Transparent,
                                focusedPlaceholderColor = Color(0xFF7D7667),
                                unfocusedPlaceholderColor = Color(0xFF7D7667)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Password Input
                    Column {
                        Text(
                            text = "Password",
                            fontSize = 12.sp,
                            color = Color(0xFF4C4639),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF2C2208),
                                unfocusedTextColor = Color(0xFF2C2208),
                                focusedContainerColor = Color(0xFFFFECC6),
                                unfocusedContainerColor = Color(0xFFFFECC6),
                                focusedBorderColor = Color(0xFF566314),
                                unfocusedBorderColor = Color.Transparent,
                                focusedPlaceholderColor = Color(0xFF7D7667),
                                unfocusedPlaceholderColor = Color(0xFF7D7667)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Confirm Password Input
                    Column {
                        Text(
                            text = "Konfirmasi Password",
                            fontSize = 12.sp,
                            color = Color(0xFF4C4639),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF2C2208),
                                unfocusedTextColor = Color(0xFF2C2208),
                                focusedContainerColor = Color(0xFFFFECC6),
                                unfocusedContainerColor = Color(0xFFFFECC6),
                                focusedBorderColor = Color(0xFF566314),
                                unfocusedBorderColor = Color.Transparent,
                                focusedPlaceholderColor = Color(0xFF7D7667),
                                unfocusedPlaceholderColor = Color(0xFF7D7667)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Terms check
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF566314))
                        )
                        Text(
                            text = "Saya menyetujui semua Syarat & Ketentuan serta Kebijakan Privasi Quero-T.",
                            color = Color(0xFF4C4639),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    // CTA Button - Daftar Sekarang
                    Button(
                        onClick = { if (checked) onRegisterSuccess() },
                        enabled = checked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF566314),
                            disabledContainerColor = Color(0xFF566314).copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(text = "Daftar Sekarang", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                    // Social Divider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFCEC6B4))
                        Text(
                            text = "Atau daftar dengan",
                            color = Color(0xFF7D7667),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFCEC6B4))
                    }

                    // Google login alternative
                    OutlinedButton(
                        onClick = onRegisterSuccess,
                        border = BorderStroke(1.dp, Color(0xFFCEC6B4)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GoogleIcon()
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Google", color = Color(0xFF241A03), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.padding(bottom = 32.dp)) {
                Text(text = "Sudah punya akun? ", color = Color(0xFF4C4639), fontSize = 14.sp)
                Text(
                    text = "Masuk",
                    color = Color(0xFF566314),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onLoginSelected() }
                )
            }
        }
    }
}

// --- SCREENS 4-8: ONBOARDING ---
@Composable
fun OnboardingScreen(
    viewModel: QueroViewModel
) {
    val step by viewModel.onboardingStep.collectAsState()
    val name by viewModel.nameInput.collectAsState()
    val gender by viewModel.genderInput.collectAsState()
    val age by viewModel.ageInput.collectAsState()
    val height by viewModel.heightInput.collectAsState()
    val weight by viewModel.weightInput.collectAsState()
    val activeActivity by viewModel.activityInput.collectAsState()
    val activeGoal by viewModel.goalInput.collectAsState()
    val activeRestrictions by viewModel.restrictionsInput.collectAsState()

    // Calculated fields
    val bmi = weight / ((height / 100).let { if (it <= 0.0) 1.7 else it }.let { it * it })
    val bmiStatus = when {
        bmi < 18.5 -> "Kurus"
        bmi < 25.0 -> "Normal (Ideal)"
        bmi < 30.0 -> "Gemuk"
        else -> "Obesitas"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        // TOP STATUS BAR SIMULATION
        MockStatusBar(backgroundColor = BgCream, contentColor = DarkBrownText)

        Spacer(modifier = Modifier.height(16.dp))

        // Progress indicators list of 5 steps
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (i in 1..5) {
                val isCurrentOrPassed = i <= step
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(if (isCurrentOrPassed) Color(0xFF566314) else Color(0xFFCEC6B4).copy(alpha = 0.5f))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (step) {
                1 -> OnboardingStep1(
                    name = name,
                    onNameChange = { viewModel.nameInput.value = it },
                    onNext = { viewModel.nextOnboardingStep() },
                    onPrev = { viewModel.navigateUser("LOGIN") }
                )
                2 -> OnboardingStep2(
                    gender = gender,
                    onGenderChange = { viewModel.genderInput.value = it },
                    age = age,
                    onAgeChange = { viewModel.ageInput.value = it },
                    onNext = { viewModel.nextOnboardingStep() },
                    onPrev = { viewModel.prevOnboardingStep() }
                )
                3 -> OnboardingStep3(
                    height = height,
                    weight = weight,
                    bmi = bmi,
                    bmiStatus = bmiStatus,
                    onHeightChange = { viewModel.heightInput.value = it },
                    onWeightChange = { viewModel.weightInput.value = it },
                    onNext = { viewModel.nextOnboardingStep() },
                    onPrev = { viewModel.prevOnboardingStep() }
                )
                4 -> OnboardingStep4(
                    activity = activeActivity,
                    onActivityChange = { viewModel.activityInput.value = it },
                    goal = activeGoal,
                    onGoalChange = { viewModel.goalInput.value = it },
                    onNext = { viewModel.nextOnboardingStep() },
                    onPrev = { viewModel.prevOnboardingStep() }
                )
                5 -> OnboardingStep5(
                    activeRestrictions = activeRestrictions,
                    onToggle = { viewModel.toggleRestriction(it) },
                    onFinish = { viewModel.completeOnboarding() },
                    onPrev = { viewModel.prevOnboardingStep() }
                )
            }
        }
    }
}

@Composable
fun OnboardingStep1(
    name: String,
    onNameChange: (String) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPrev) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color(0xFF566314))
            }
            Text(
                text = "Kembali ke Login",
                color = Color(0xFF566314),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Siapa nama Anda? ✨",
            fontSize = 24.sp,
            color = Color(0xFF241A03),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Silakan masukkan nama lengkap Anda untuk memulai formulasi kecerdasan buatan.",
            fontSize = 14.sp,
            color = Color(0xFF4C4639),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 24.dp),
            textAlign = TextAlign.Start
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2DC)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "NAMA LENGKAP",
                    fontSize = 12.sp,
                    color = Color(0xFF4C4639),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    placeholder = { Text("Ahmad Hidayat", color = Color(0xFF7D7667)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF566314),
                        unfocusedBorderColor = Color(0xFFCEC6B4),
                        focusedLabelColor = Color(0xFF566314)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFFFF2DC).copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🥖", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Quero-T mempersonalisasi nutrisi secara presisi sesuai dengan profil tubuh Anda.",
                    color = Color(0xFF4C4639),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { if (name.isNotBlank()) onNext() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7BD72)),
            shape = RoundedCornerShape(24.dp),
            enabled = name.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(text = "Lanjut >", color = Color(0xFF241A03), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(modifier = Modifier.size(24.dp, 6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
        }
    }
}

@Composable
fun OnboardingStep2(
    gender: String,
    onGenderChange: (String) -> Unit,
    age: Int,
    onAgeChange: (Int) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPrev) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color(0xFF566314))
            }
            Text(
                text = "Kembali",
                color = Color(0xFF566314),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Profil Anda 👤",
            fontSize = 24.sp,
            color = Color(0xFF241A03),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Jenis kelamin & usia membantu menyelaraskan metabolisme basal Mifflin-St Jeor.",
            fontSize = 14.sp,
            color = Color(0xFF4C4639),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 24.dp),
            textAlign = TextAlign.Start
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2DC)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "JENIS KELAMIN",
                    fontSize = 12.sp,
                    color = Color(0xFF4C4639),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val list = listOf("Laki-laki", "Perempuan")
                    list.forEach { option ->
                        val isSelected = gender == option
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Color(0xFF566314) else Color.White)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color(0xFF566314) else Color(0xFFCEC6B4),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { onGenderChange(option) }
                                .padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option,
                                color = if (isSelected) Color.White else Color(0xFF241A03),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                HorizontalDivider(color = Color(0xFFCEC6B4).copy(alpha = 0.5f), thickness = 1.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "USIA",
                        fontSize = 12.sp,
                        color = Color(0xFF4C4639),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$age Tahun",
                        fontSize = 20.sp,
                        color = Color(0xFF566314),
                        fontWeight = FontWeight.Bold
                    )
                }

                Slider(
                    value = age.toFloat(),
                    onValueChange = { onAgeChange(it.toInt()) },
                    valueRange = 12f..80f,
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color(0xFF566314),
                        inactiveTrackColor = Color(0xFFCEC6B4).copy(alpha = 0.5f),
                        thumbColor = Color(0xFF566314)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7BD72)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Lanjut >", color = Color(0xFF241A03), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Langkah 2 dari 5",
            color = Color(0xFF4C4639),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(24.dp, 6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
        }
    }
}

@Composable
fun OnboardingStep3(
    height: Double,
    weight: Double,
    bmi: Double,
    bmiStatus: String,
    onHeightChange: (Double) -> Unit,
    onWeightChange: (Double) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPrev) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color(0xFF566314))
            }
            Text(
                text = "Kembali",
                color = Color(0xFF566314),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Tinggi & Berat Badan? 📏",
            fontSize = 24.sp,
            color = Color(0xFF241A03),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Informasi tinggi dan berat badan digunakan untuk mengukur indeks BMI harian Anda.",
            fontSize = 14.sp,
            color = Color(0xFF4C4639),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 24.dp),
            textAlign = TextAlign.Start
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2DC)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Height Slider
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "TINGGI BADAN", fontSize = 12.sp, color = Color(0xFF4C4639), fontWeight = FontWeight.Bold)
                        Text(text = "${height.toInt()} cm", fontSize = 18.sp, color = Color(0xFF566314), fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = height.toFloat(),
                        onValueChange = { onHeightChange(it.toDouble()) },
                        valueRange = 100f..220f,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF566314),
                            inactiveTrackColor = Color(0xFFCEC6B4).copy(alpha = 0.5f),
                            thumbColor = Color(0xFF566314)
                        )
                    )
                }

                HorizontalDivider(color = Color(0xFFCEC6B4).copy(alpha = 0.5f), thickness = 1.dp)

                // Weight Slider
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "BERAT BADAN", fontSize = 12.sp, color = Color(0xFF4C4639), fontWeight = FontWeight.Bold)
                        Text(text = "${weight.toInt()} kg", fontSize = 18.sp, color = Color(0xFF566314), fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = weight.toFloat(),
                        onValueChange = { onWeightChange(it.toDouble()) },
                        valueRange = 30f..150f,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF566314),
                            inactiveTrackColor = Color(0xFFCEC6B4).copy(alpha = 0.5f),
                            thumbColor = Color(0xFF566314)
                        )
                    )
                }

                // Live BMI Display Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Indeks Massa Tubuh (BMI) Anda", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Status: $bmiStatus", fontSize = 15.sp, color = Color(0xFF241A03), fontWeight = FontWeight.Bold)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF566314))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = String.format("%.1f", bmi),
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7BD72)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Lanjut >", color = Color(0xFF241A03), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Langkah 3 dari 5",
            color = Color(0xFF4C4639),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(24.dp, 6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
        }
    }
}

@Composable
fun OnboardingStep4(
    activity: String,
    onActivityChange: (String) -> Unit,
    goal: String,
    onGoalChange: (String) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPrev) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color(0xFF566314))
            }
            Text(
                text = "Kembali",
                color = Color(0xFF566314),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Kebutuhan Anda 🎯",
            fontSize = 24.sp,
            color = Color(0xFF241A03),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Pilih tingkat aktivitas harian Anda serta target kesehatan yang ingin dicapai.",
            fontSize = 14.sp,
            color = Color(0xFF4C4639),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 20.dp),
            textAlign = TextAlign.Start
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2DC)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section: Aktivitas
                Text(
                    text = "TINGKAT AKTIVITAS",
                    fontSize = 11.sp,
                    color = Color(0xFF4C4639),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                val activities = listOf(
                    Triple("Ringan", "Banyak duduk, olahraga jarang 🚶", "Ringan"),
                    Triple("Sedang", "Aktif bergerak, olahraga 3x/mgg 🏃", "Sedang"),
                    Triple("Berat", "Olahraga intens & kerja fisik 💪", "Berat")
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    activities.forEach { (title, desc, key) ->
                        val isSelected = activity == key
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) Color(0xFF566314) else Color.White)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color(0xFF566314) else Color(0xFFCEC6B4),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { onActivityChange(key) }
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onActivityChange(key) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.White,
                                        unselectedColor = Color(0xFF566314)
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = title,
                                        color = if (isSelected) Color.White else Color(0xFF241A03),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = desc,
                                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color(0xFF4C4639),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = Color(0xFFCEC6B4).copy(alpha = 0.5f), thickness = 1.dp)

                // Section: Target Sifat
                Text(
                    text = "TARGET KESEHATAN",
                    fontSize = 11.sp,
                    color = Color(0xFF4C4639),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                val goals = listOf(
                    Triple("Menurunkan Berat Badan", "Defisit kalori sehat 📉", "Menurunkan Berat Badan"),
                    Triple("Menjaga Berat Badan", "Keseimbangan energi seimbang ⚖️", "Menjaga Berat Badan"),
                    Triple("Menaikkan Berat Badan", "Surplus energi untuk tumbuh 📈", "Menaikkan Berat Badan")
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    goals.forEach { (title, desc, key) ->
                        val isSelected = goal == key
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) Color(0xFF566314) else Color.White)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color(0xFF566314) else Color(0xFFCEC6B4),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { onGoalChange(key) }
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onGoalChange(key) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.White,
                                        unselectedColor = Color(0xFF566314)
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = title,
                                        color = if (isSelected) Color.White else Color(0xFF241A03),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = desc,
                                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color(0xFF4C4639),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7BD72)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Lanjut >", color = Color(0xFF241A03), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Langkah 4 dari 5",
            color = Color(0xFF4C4639),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(24.dp, 6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFCEC6B4)))
        }
    }
}

@Composable
fun OnboardingStep5(
    activeRestrictions: Set<String>,
    onToggle: (String) -> Unit,
    onFinish: () -> Unit,
    onPrev: () -> Unit
) {
    val options = listOf("Gluten", "Laktosa", "Kacang", "Seafood", "Telur", "Tidak Ada")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPrev) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color(0xFF566314))
            }
            Text(
                text = "Kembali",
                color = Color(0xFF566314),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Pantangan Makanan? 🛡️",
            fontSize = 24.sp,
            color = Color(0xFF241A03),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Bantu kami menyelaraskan resep sehat harian Anda dengan alergi & pantangan Anda.",
            fontSize = 14.sp,
            color = Color(0xFF4C4639),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 24.dp),
            textAlign = TextAlign.Start
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2DC)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PILIH ALERGI / DIET",
                    fontSize = 12.sp,
                    color = Color(0xFF4C4639),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                // Grid Layout for Options
                val rows = options.chunked(2)
                rows.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { option ->
                            val isSelected = activeRestrictions.contains(option)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (isSelected) Color(0xFF566314) else Color.White)
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) Color(0xFF566314) else Color(0xFFCEC6B4),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable { onToggle(option) }
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val emoji = when (option) {
                                        "Gluten" -> "🌾"
                                        "Laktosa" -> "🥛"
                                        "Kacang" -> "🥜"
                                        "Seafood" -> "🍤"
                                        "Telur" -> "🥚"
                                        else -> "🟢"
                                    }
                                    Text(text = emoji, fontSize = 16.sp)
                                    Text(
                                        text = option,
                                        color = if (isSelected) Color.White else Color(0xFF241A03),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinish,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF566314)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = "Formulasikan Nutrisi Saya ✨",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Langkah 5 dari 5",
            color = Color(0xFF4C4639),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF566314)))
            Box(modifier = Modifier.size(24.dp, 6.dp).clip(CircleShape).background(Color(0xFF566314)))
        }
    }
}

// --- SCREEN 9: HOME DASHBOARD ---
@Composable
fun HomeScreen(
    userName: String,
    targetCalories: Int,
    cartCount: Int,
    onNavigate: (String) -> Unit,
    onSelectProduct: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        // TOP NOTIFICATION & DATE CHIP
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Halo, $userName! 👋", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "28 Mei 2026", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Bell", tint = OliveGreen)
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Daily Nutrition Card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(CreamGold, SurfaceLight)
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DonutChart(consumedCalories = 1200f, targetCalories = targetCalories.toFloat())
                        Column(
                            modifier = Modifier.padding(start = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "Asupan Hari Ini", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            StatLabel(emoji = "🥩", label = "Protein", value = "35 / 60g")
                            StatLabel(emoji = "🌾", label = "Serat", value = "12 / 25g")
                            StatLabel(emoji = "💊", label = "Vitamin", value = "Tercukupi ✓")
                        }
                    }
                }
            }

            // Section 2: Quick Recommendations
            item {
                Column {
                    Text(
                        text = "Rekomendasi Cepat Untukmu",
                        color = DarkBrownText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    // Horizontally scrolling list of 3 popular items
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(ProductData.products.take(3)) { prod ->
                            QuickProductCard(product = prod, onSelect = { onSelectProduct(prod.id) })
                        }
                    }
                }
            }

            // Section 3: Promo Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(OliveGreen)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Paket Sehat Mingguan",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Berlangganan rutin dapat diskon 20%",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(WarmOrange)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "DISKON 20%",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Section 4: Nutrition Progress Bars
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "Rincian Nutrisi Makro", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    MacroProgressBar(label = "Kalori", value = 1200f, target = 2100f, unit = "kcal")
                    MacroProgressBar(label = "Protein", value = 35f, target = 60f, unit = "g")
                    MacroProgressBar(label = "Karbohidrat", value = 130f, target = 250f, unit = "g")
                    MacroProgressBar(label = "Serat", value = 12f, target = 25f, unit = "g")
                }
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }

        // Bottom nav
        UserBottomNavigation(activeScreen = "HOME", cartCount = cartCount, onTabSelected = onNavigate)
    }
}

@Composable
fun StatLabel(emoji: String, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = emoji, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "$label: ", fontSize = 11.sp, color = DarkBrownText.copy(alpha = 0.7f))
        Text(text = value, fontSize = 11.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun QuickProductCard(product: BreadProduct, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(android.graphics.Color.parseColor(product.colorHex)).copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getProductEmoji(product.name),
                    fontSize = 28.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${product.calories} kcal • ${product.protein}g Prot",
                fontSize = 10.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(OliveGreen.copy(alpha = 0.1f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(text = "⭐ Cocok untukmu", fontSize = 8.sp, color = OliveGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MacroProgressBar(label: String, value: Float, target: Float, unit: String) {
    val rate = (value / target).coerceIn(0f, 1f)
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = 11.sp, color = DarkBrownText, fontWeight = FontWeight.Medium)
            Text(text = "${value.toInt()} / ${target.toInt()} $unit", fontSize = 11.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(rate)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(OliveGreen)
            )
        }
    }
}

// --- SCREEN 10: PRODUCT CATALOG ---
@Composable
fun ProductCatalogScreen(
    cartCount: Int,
    onNavigate: (String) -> Unit,
    onAddToCart: (Int) -> Unit,
    onSelectProduct: (Int) -> Unit
) {
    var searchtext by remember { mutableStateOf("") }
    var currentFilter by remember { mutableStateOf("Semua") }

    val filteredProducts = ProductData.products.filter { prod ->
        val matchSearch = prod.name.lowercase().contains(searchtext.lowercase())
        val matchCategory = when (currentFilter) {
            "Semua" -> true
            "Roti" -> prod.category == "Roti"
            "Kue" -> prod.category == "Kue"
            "Tinggi Protein" -> prod.protein >= 10
            else -> true
        }
        matchSearch && matchCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        // TOP SEARCH BAR
        OutlinedTextField(
            value = searchtext,
            onValueChange = { searchtext = it },
            placeholder = { Text("Cari Roti Sehat Quero-T...") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF2C2208),
                unfocusedTextColor = Color(0xFF2C2208),
                focusedContainerColor = SurfaceLight,
                unfocusedContainerColor = SurfaceLight,
                focusedBorderColor = OliveGreen,
                unfocusedBorderColor = Color.Transparent,
                focusedPlaceholderColor = Color(0xFF9CA3AF),
                unfocusedPlaceholderColor = Color(0xFF9CA3AF)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // FILTER CHIPS row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Semua", "Roti", "Kue", "Tinggi Protein").forEach { name ->
                val isSelected = currentFilter == name
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) OliveGreen else Color.White)
                        .border(1.dp, if (isSelected) OliveGreen else Color.LightGray, RoundedCornerShape(16.dp))
                        .clickable { currentFilter = name }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = name,
                        color = if (isSelected) Color.White else DarkBrownText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // GRID of products
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Group list into chunk of 2 in order to display grid manually
            items(filteredProducts.chunked(2)) { list ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    list.forEach { prod ->
                        CatalogProductCard(
                            product = prod,
                            onAddToCart = { onAddToCart(prod.id) },
                            onSelect = { onSelectProduct(prod.id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (list.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        UserBottomNavigation(activeScreen = "CATALOG", cartCount = cartCount, onTabSelected = onNavigate)
    }
}

@Composable
fun CatalogProductCard(
    product: BreadProduct,
    onAddToCart: () -> Unit,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                // Colored Circle represent product
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(android.graphics.Color.parseColor(product.colorHex)).copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getProductEmoji(product.name),
                        fontSize = 32.sp
                    )
                }
                if (product.badge.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(WarmOrange)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(text = product.badge, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${product.calories} kcal • ${product.protein}g Prot",
                fontSize = 9.sp,
                color = Color.Gray,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rp ${product.price.toInt()}",
                color = OliveGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedButton(
                onClick = onAddToCart,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, OliveGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                Text(text = "+ Keranjang", color = OliveGreen, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- SCREEN 11: PRODUCT DETAIL ---
@Composable
fun ProductDetailScreen(
    product: BreadProduct,
    onAddToCart: (Int) -> Unit,
    onBack: () -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = DarkBrownText)
            }
            Text(text = "Detail Produk", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite", tint = DarkBrownText)
            }
        }

        // Hero image area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(android.graphics.Color.parseColor(product.colorHex)).copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getProductEmoji(product.name),
                fontSize = 80.sp
            )
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = product.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkBrownText)
            Text(text = product.subtitle, fontSize = 13.sp, color = Color.Gray, fontStyle = FontStyle.Italic)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Rp ${product.price.toInt()}", color = OliveGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = product.description, fontSize = 12.sp, color = DarkBrownText, lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(20.dp))

            // Nutrition Grid
            Text(text = "Rincian Gizi", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Row 1
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MiniNutritionCell("🔥 Kalori", "${product.calories} kcal", Modifier.weight(1f))
                    MiniNutritionCell("🥩 Protein", "${product.protein} g", Modifier.weight(1f))
                    MiniNutritionCell("🌾 Serat", "${product.fiber} g", Modifier.weight(1f))
                }
                // Row 2
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MiniNutritionCell("🧈 Lemak", "${product.fat} g", Modifier.weight(1f))
                    MiniNutritionCell("🥖 Karbo", "${product.carbs} g", Modifier.weight(1f))
                    MiniNutritionCell("💊 Vitamin/Min", product.vitamins, Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tags Suitable
            Text(text = "Labels & Kesesuaian", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                product.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceLight)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = tag, fontSize = 10.sp, color = DarkBrownText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Qty selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Jumlah", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceLight)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }, modifier = Modifier.size(36.dp)) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            drawLine(
                                color = DarkBrownText,
                                start = androidx.compose.ui.geometry.Offset(0f, size.height / 2f),
                                end = androidx.compose.ui.geometry.Offset(size.width, size.height / 2f),
                                strokeWidth = 5f
                            )
                        }
                    }
                    Text(text = quantity.toString(), fontSize = 14.sp, color = DarkBrownText, modifier = Modifier.padding(horizontal = 12.dp), fontWeight = FontWeight.Bold)
                    IconButton(onClick = { quantity++ }, modifier = Modifier.size(36.dp)) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = DarkBrownText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action button
            Button(
                onClick = { onAddToCart(quantity) },
                colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Tambah ke Keranjang", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MiniNutritionCell(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 12.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
    }
}

// --- SCREEN 12: SMART RECOMMENDATION (AI Results) ---
@Composable
fun SmartRecommendationScreen(
    viewModel: QueroViewModel,
    onNavigate: (String) -> Unit,
    onOrderAll: () -> Unit
) {
    val loading by viewModel.aiExplainLoading.collectAsState()
    val explanationText by viewModel.aiExplanationText.collectAsState()
    val profile by viewModel.userProfile.collectAsState()

    // Trigger Gemini Call on First Launch
    LaunchedEffect(Unit) {
        viewModel.fetchGeminiRecommendationExplanation()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        MockStatusBar(backgroundColor = BgCream, contentColor = DarkBrownText)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Rekomendasi Pintar AI",
                            color = Color(0xFF2C2208),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Text(
                            text = "Dietary program harian berbasis kecerdasan buatan",
                            color = Color(0xFF4C4639),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(text = "🤖", fontSize = 28.sp)
                }
            }

            // User statistics high-fidelity card
            item {
                profile?.let { prof ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFFD7BD72), Color(0xFFC4AB5F))
                                    )
                                )
                                .padding(20.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "PROFIL NUTRISI ANDA",
                                        color = Color(0xFF5E4B0A),
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 11.sp,
                                        letterSpacing = 1.sp
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFF566314))
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(text = "Active", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }

                                Text(
                                    text = prof.name,
                                    color = Color(0xFF241A03),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )

                                Divider(color = Color(0xFF241A03).copy(alpha = 0.15f), thickness = 1.dp)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = "Target Kalori", fontSize = 11.sp, color = Color(0xFF5E4B0A), fontWeight = FontWeight.Bold)
                                        Text(text = "${prof.calculatedCalories} kcal/Hari", fontSize = 15.sp, color = Color(0xFF241A03), fontWeight = FontWeight.Bold)
                                    }
                                    Column {
                                        Text(text = "Fokus Tujuan", fontSize = 11.sp, color = Color(0xFF5E4B0A), fontWeight = FontWeight.Bold)
                                        Text(text = prof.healthGoal, fontSize = 15.sp, color = Color(0xFF241A03), fontWeight = FontWeight.Bold)
                                    }
                                    Column {
                                        Text(text = "Tingkat Aktif", fontSize = 11.sp, color = Color(0xFF5E4B0A), fontWeight = FontWeight.Bold)
                                        Text(text = prof.activityLevel, fontSize = 15.sp, color = Color(0xFF241A03), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // WHY THIS card (Insight area)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF7C3AED)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "✨", fontSize = 12.sp, color = Color.White)
                            }
                            Text(
                                text = "Analisis Kecocokan Gizi AI",
                                color = Color(0xFF7C3AED),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        if (loading) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Menganalisis profil fisik & pantangan makan...", color = Color(0xFF5B21B6), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(12.dp))
                                LinearProgressIndicator(color = Color(0xFF7C3AED), trackColor = Color(0xFFDDD6FE), modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape))
                            }
                        } else {
                            Text(
                                text = explanationText, 
                                color = Color(0xFF2C2208), 
                                fontSize = 13.sp, 
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Food schedule timeline
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Jadwal Gizi Sehat Anda", 
                        color = Color(0xFF2C2208), 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                    )
                    ScheduleItem(time = "🌅 07:00 Pagi", title = "Quero-T Active x2", desc = "360 kcal, 24g Protein (Paling disarankan)", emoji = "🍞")
                    ScheduleItem(time = "☀️ 12:00 Siang", title = "Quero-T Energy x1", desc = "120 kcal, Booster pre-workout tenaga", emoji = "💪")
                    ScheduleItem(time = "🌤 15:00 Sore", title = "Quero-T FruitBoost x1", desc = "90 kcal, Vitamin C penunjang imunitas", emoji = "🍊")
                    ScheduleItem(time = "🌙 19:00 Malam", title = "Quero-T Fit x1", desc = "120 kcal, Rendah kalori, kaya serat", emoji = "🥖")
                }
            }

            // Progress Summary box
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "TOTAL REKOMENDASI GIZI", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "690 kcal dari Roti & Kue", fontSize = 16.sp, color = Color(0xFF2C2208), fontWeight = FontWeight.Bold)
                        }
                        // Progress indicators circle
                        Box(contentAlignment = Alignment.Center) {
                            Canvas(modifier = Modifier.size(44.dp)) {
                                drawCircle(color = Color.LightGray.copy(alpha = 0.3f))
                                drawArc(color = Color(0xFFD7BD72), startAngle = -90f, sweepAngle = 240f, useCenter = false, style = Stroke(width = 8f))
                            }
                            Text(text = "33%", fontSize = 11.sp, color = Color(0xFF2C2208), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Order all recommendations CTAs
            item {
                Button(
                    onClick = onOrderAll,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF566314)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Order All", tint = Color.White)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Pesan Semua Rekomendasi (Rp 55.000)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }

        UserBottomNavigation(activeScreen = "HOME", cartCount = 0, onTabSelected = onNavigate)
    }
}

@Composable
fun ScheduleItem(time: String, title: String, desc: String, emoji: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Circle container matching center emoji rules
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFF2DC)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 24.sp)
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title, fontSize = 14.sp, color = Color(0xFF2C2208), fontWeight = FontWeight.Bold)
                    Text(text = time, fontSize = 11.sp, color = Color(0xFF566314), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = desc, fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

// --- SCREEN 13: NUTRITION TRACKING ---
@Composable
fun NutritionTrackingScreen(
    viewModel: QueroViewModel,
    onNavigate: (String) -> Unit
) {
    var currentTab by remember { mutableStateOf("Hari Ini") }
    val logs by viewModel.nutritionLogs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        MockStatusBar(backgroundColor = BgCream, contentColor = DarkBrownText)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "📊 Pelacakan Nutrisi",
                    color = DarkBrownText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            // Tab switcher
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceLight)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    listOf("Hari Ini", "Minggu", "Bulan").forEach { name ->
                        val isSelected = currentTab == name
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) OliveGreen else Color.Transparent)
                                .clickable { currentTab = name }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = name,
                                color = if (isSelected) Color.White else DarkBrownText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            if (currentTab == "Hari Ini") {
                // DONUT CHART
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DonutChart(consumedCalories = 450f, targetCalories = 2100f)
                    }
                }

                // TIMELINE VIEW
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Rencana Konsumsi Anda", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        logs.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = item.timeLabel, color = Color.Gray, fontSize = 10.sp)
                                    Text(text = "${item.productName} (x${item.quantity})", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(text = "${item.calories} kcal • ${item.protein}g Protein", color = Color.Gray, fontSize = 10.sp)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(if (item.isConsumed) OliveGreen else Color.LightGray.copy(alpha = 0.5f))
                                        .clickable { viewModel.toggleNutritionLogConsumed(item.id, item.isConsumed) }
                                        .padding(10.dp)
                                ) {
                                    Icon(
                                        imageVector = if (item.isConsumed) Icons.Filled.Check else Icons.Filled.Refresh,
                                        contentDescription = "Status",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            } else if (currentTab == "Minggu") {
                // WEEKLY BAR CHART
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        WeeklyBarChart(heights = listOf(0.85f, 0.45f, 0.95f, 0.15f, 0.65f, 0.0f, 0.0f))
                    }
                }

                // ACHIEVEMENT BADGES
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Lencana Pencapaian Sehat", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            BadgeItem(emoji = "🔥", title = "3 Hari Beruntun", desc = "Target Kalori Terpenuhi")
                            BadgeItem(emoji = "💪", title = "Protein Terpenuhi", desc = "Massa Otot Optimal")
                        }
                    }
                }
            } else {
                // Monthly view placeholder
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Data grafik bulanan Anda tersimpan aman dan terintegrasi di Cloud lokal.", color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        UserBottomNavigation(activeScreen = "NUTRITION", cartCount = 0, onTabSelected = onNavigate)
    }
}

@Composable
fun BadgeItem(emoji: String, title: String, desc: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, fontSize = 11.sp, color = DarkBrownText, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text(text = desc, fontSize = 8.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}

// --- SCREEN 14: CART & CHECKOUT ---
@Composable
fun CartCheckoutScreen(
    viewModel: QueroViewModel,
    onNavigate: (String) -> Unit
) {
    val step by viewModel.checkoutStep.collectAsState()
    val cart by viewModel.cartItems.collectAsState()
    val payMethod by viewModel.selectedPaymentMethod.collectAsState()

    // Price calculation
    val subtotal = cart.sumOf { it.price * it.quantity }
    val shipping = if (cart.isEmpty()) 0.0 else 10000.0
    val total = subtotal + shipping

    // Nutrition total calculation
    val totalCalories = cart.sumOf { it.calories * it.quantity }
    val totalProtein = cart.sumOf { it.protein * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        MockStatusBar(backgroundColor = BgCream, contentColor = DarkBrownText)

        // Progress indicators stepper (Keranjang -> Alamat -> Pembayaran -> Selesai)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StepPill(label = "Keranjang", active = step == 1, completed = step > 1)
            StepPill(label = "Alamat", active = step == 2, completed = step > 2)
            StepPill(label = "Pembayaran", active = step == 3, completed = step > 3)
            StepPill(label = "Selesai", active = step == 4, completed = step == 4)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (step) {
                1 -> {
                    // STEP 1 CONTENT: Keranjang belanja list
                    if (cart.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Keranjang Anda kosong.\nYuk pesan menu gizi sehatmu sekarang!", color = Color.Gray, textAlign = TextAlign.Center)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item { Text(text = "Menu Pilihan Anda", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(vertical = 12.dp)) }
                            items(cart) { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White)
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = item.name, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(text = item.tagline, color = Color.Gray, fontSize = 10.sp)
                                        Text(text = "Rp ${item.price.toInt()} | ${item.calories} kcal", color = OliveGreen, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(onClick = { viewModel.handleUpdateCartQuantity(item.productId, item.quantity - 1) }, modifier = Modifier.size(32.dp)) {
                                            Canvas(modifier = Modifier.size(10.dp)) {
                                                drawLine(
                                                    color = DarkBrownText,
                                                    start = androidx.compose.ui.geometry.Offset(0f, size.height / 2f),
                                                    end = androidx.compose.ui.geometry.Offset(size.width, size.height / 2f),
                                                    strokeWidth = 5f
                                                )
                                            }
                                        }
                                        Text(text = item.quantity.toString(), color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 8.dp))
                                        IconButton(onClick = { viewModel.handleUpdateCartQuantity(item.productId, item.quantity + 1) }, modifier = Modifier.size(32.dp)) {
                                            Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = DarkBrownText)
                                        }
                                    }
                                }
                            }

                            // Nutrition total summary block
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(SurfaceLight)
                                        .padding(16.dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text(text = "Ringkasan Gizi Keranjang Belanja", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text(text = "Total Kalori:", fontSize = 11.sp, color = Color.Gray)
                                            Text(text = "$totalCalories kcal", fontSize = 11.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
                                        }
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text(text = "Total Protein:", fontSize = 11.sp, color = Color.Gray)
                                            Text(text = "$totalProtein g", fontSize = 11.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                        }
                    }
                }
                2 -> {
                    // STEP 2 CONTENT: Alamat pengiriman
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(text = "Alamat Pengiriman", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Kami akan mengirim pesanan segar Anda langsung ke lokasi ini.", color = Color.Gray, fontSize = 11.sp, modifier = Modifier.padding(bottom = 20.dp))

                        OutlinedTextField(
                            value = "Jalan Rungkut Madya No. 4, UPN Veteran Jawa Timur, Surabaya",
                            onValueChange = { },
                            maxLines = 3,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF2C2208),
                                unfocusedTextColor = Color(0xFF2C2208),
                                focusedContainerColor = SurfaceLight,
                                unfocusedContainerColor = SurfaceLight,
                                focusedBorderColor = OliveGreen,
                                unfocusedBorderColor = Color.Transparent,
                                focusedPlaceholderColor = Color(0xFF9CA3AF),
                                unfocusedPlaceholderColor = Color(0xFF9CA3AF)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                3 -> {
                    // STEP 3 CONTENT: Pembayaran
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(text = "Pilih Metode Pembayaran", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Pilih opsi termudah untuk transaksi Anda.", color = Color.Gray, fontSize = 11.sp, modifier = Modifier.padding(bottom = 20.dp))

                        listOf("E-wallet", "Transfer Bank", "COD").forEach { method ->
                            val isSelected = payMethod == method
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) CreamGold.copy(alpha = 0.2f) else Color.White)
                                    .border(1.dp, if (isSelected) OliveGreen else Color.LightGray, RoundedCornerShape(12.dp))
                                    .clickable { viewModel.selectedPaymentMethod.value = method }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = method, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { viewModel.selectedPaymentMethod.value = method },
                                    colors = RadioButtonDefaults.colors(selectedColor = OliveGreen)
                                )
                            }
                        }
                    }
                }
                4 -> {
                    // STEP 4 CONTENT: Selesai
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(OliveGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Filled.Check, contentDescription = "Selesai", tint = Color.White, modifier = Modifier.size(48.dp))
                            }
                            Spacer(modifier = Modifier.height(18.dp))
                            Text(text = "Selamat, Pembayaran Berhasil!", fontSize = 18.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Pesanan Anda sedang dipersiapkan oleh penjual.", color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    viewModel.checkoutStep.value = 1
                                    viewModel.userScreen.value = "HOME"
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = "Kembali ke Dashboard", color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        // Actions bar (Receipt & buttons)
        if (cart.isNotEmpty() && step < 4) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Receipt summary
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Subtotal:", fontSize = 11.sp, color = Color.Gray)
                    Text(text = "Rp ${subtotal.toInt()}", fontSize = 11.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Ongkos Kirim:", fontSize = 11.sp, color = Color.Gray)
                    Text(text = "Rp ${shipping.toInt()}", fontSize = 11.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Total:", fontSize = 13.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
                    Text(text = "Rp ${total.toInt()}", fontSize = 13.sp, color = OliveGreen, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (step == 3) {
                    Button(
                        onClick = { viewModel.handlePayNow() },
                        colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(text = "Bayar Sekarang", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = { viewModel.checkoutStep.value = step + 1 },
                        colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(text = "Lanjut ke Pembayaran", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            UserBottomNavigation(activeScreen = "CART", cartCount = cart.size, onTabSelected = onNavigate)
        }
    }
}

@Composable
fun StepPill(label: String, active: Boolean, completed: Boolean) {
    val color = when {
        active -> OliveGreen
        completed -> CreamGold
        else -> Color.LightGray.copy(alpha = 0.5f)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
    }
}

// --- SCREEN 15: PROFILE ---
@Composable
fun ProfileScreen(
    viewModel: QueroViewModel,
    onNavigate: (String) -> Unit
) {
    val profile by viewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        MockStatusBar(backgroundColor = BgCream, contentColor = DarkBrownText)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "👤 Profil Pengguna",
                    color = DarkBrownText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            // User header card
            item {
                profile?.let { prof ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(CreamGold, SurfaceLight)
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Filled.Person, contentDescription = "Avatar", tint = OliveGreen, modifier = Modifier.size(36.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = prof.name, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(OliveGreen)
                                            .size(8.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Aktif • ${prof.streakDays} hari streak", color = OliveGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // User metrics grid
            item {
                profile?.let { prof ->
                    val bmi = prof.weight / ((prof.height / 100.0) * (prof.height / 100.0))
                    val bmiStatus = when {
                        bmi < 18.5 -> "Underweight"
                        bmi < 25.0 -> "Normal"
                        bmi < 30.0 -> "Overweight"
                        else -> "Obese"
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Informasi Tubuh & Kesehatan", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            MiniNutritionCell("Keadaan BMI", bmiStatus, Modifier.weight(1f))
                            MiniNutritionCell("Tinggi", "${prof.height.toInt()} cm", Modifier.weight(1f))
                            MiniNutritionCell("Berat", "${prof.weight.toInt()} kg", Modifier.weight(1f))
                        }
                    }
                }
            }

            // Social Impact section "Tentang Quero-T"
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = OliveGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Tentang Quero-T", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Quero-T membantu masyarakat Indonesia mendapatkan nutrisi seimbang melalui produk pangan lokal yang terjangkau dan bergizi.",
                            color = Color.Gray,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Navigation items list
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(8.dp)
                ) {
                    ProfileMenuRow(icon = Icons.Filled.List, title = "Riwayat Pesanan")
                    ProfileMenuRow(icon = Icons.Outlined.FavoriteBorder, title = "Produk Favorit")
                    ProfileMenuRow(icon = Icons.Outlined.Notifications, title = "Preferensi Notifikasi")
                    ProfileMenuRow(icon = Icons.Filled.Star, title = "Langganan Mingguan Aktif")
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        UserBottomNavigation(activeScreen = "PROFILE", cartCount = 0, onTabSelected = onNavigate)
    }
}

@Composable
fun ProfileMenuRow(
    icon: ImageVector,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = OliveGreen, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = DarkBrownText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
    }
}

// --- SCREEN: AI PROCESSING SCREEN ---
@Composable
fun AiProcessingScreen(
    onNavigateNext: () -> Unit
) {
    // Navigate after 3.5 seconds
    LaunchedEffect(Unit) {
        delay(3500)
        onNavigateNext()
    }

    var dotsText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            delay(600)
            dotsText = when (dotsText) {
                "" -> "."
                "." -> ".."
                ".." -> "..."
                else -> ""
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFAF2)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Quero-T AI",
                    color = Color(0xFF705C1B),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp
                )
            }

            // Central circle container & Circular Progress Indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(vertical = 40.dp)
            ) {
                // Central white circle (static)
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .shadow(elevation = 4.dp, shape = CircleShape)
                        .background(Color.White, shape = CircleShape)
                        .border(width = 3.dp, color = Color(0xFFD7BD72), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🧠", fontSize = 52.sp)
                }

                // Simple spinner below circle matching 32x32px
                CircularProgressIndicator(
                    color = Color(0xFF576415),
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(32.dp),
                    trackColor = Color(0xFFEEEEEE)
                )
            }

            // Progress Steps (all visible immediately, static render)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Step 1
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2F0D9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF576415),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "Membaca data profil kamu...",
                        fontSize = 14.sp,
                        color = Color(0xFF4C4639),
                        fontWeight = FontWeight.Medium
                    )
                }

                // Step 2
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2F0D9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF576415),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "Menghitung kebutuhan kalori harian...",
                        fontSize = 14.sp,
                        color = Color(0xFF4C4639),
                        fontWeight = FontWeight.Medium
                    )
                }

                // Step 3
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2F0D9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF576415),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "Mencocokkan dengan produk Quero-T...",
                        fontSize = 14.sp,
                        color = Color(0xFF4C4639),
                        fontWeight = FontWeight.Medium
                    )
                }

                // Step 4 (spinning loading icon)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val infiniteTransition = rememberInfiniteTransition("spinning_steps")
                    val spinAngle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1200, easing = LinearEasing)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer { rotationZ = spinAngle },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Loading",
                            tint = Color(0xFF825500),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "Menyiapkan rekomendasi personalmu" + dotsText,
                        fontSize = 14.sp,
                        color = Color(0xFF825500),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Footer
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Ini hanya butuh beberapa detik 🌿",
                    color = Color(0xFF7D7667),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                // Linear sliding progress bar indicator
                val barTransition = rememberInfiniteTransition(label = "loading_bar")
                val barOffset by barTransition.animateFloat(
                    initialValue = -32f,
                    targetValue = 128f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "bar"
                )

                Box(
                    modifier = Modifier
                        .width(128.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEEEEE))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(32.dp)
                            .align(Alignment.CenterStart)
                            .graphicsLayer {
                                translationX = barOffset.dp.toPx()
                            }
                            .background(Color(0xFF576415), shape = CircleShape)
                    )
                }
            }
        }
    }
}

// --- SCREEN: ONBOARDING RESULT SCREEN ---
@Composable
fun OnboardingResultScreen(
    viewModel: QueroViewModel,
    onNavigateNext: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val profileState by viewModel.userProfile.collectAsState()
    val profile = profileState

    val calories = profile?.calculatedCalories ?: 2100
    val weight = profile?.weight ?: 65.0
    val protein = (weight * 1.25).toInt().let { if (it <= 0) 60 else it }
    val fiber = (calories / 84).toInt().let { if (it <= 0) 25 else it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F1))
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header (static, in normal flow)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF705C1B)
                )
            }
            Text(
                text = "Quero-T",
                color = Color(0xFF705C1B),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.width(40.dp)) // Equal spacer for centering title
        }

        // Celebration Header (no animation)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDBEB8D)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🎉", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Rencana Nutrisi Anda Siap!",
                color = Color(0xFF241A03),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Kami telah menghitung profil kesehatan Anda.",
                color = Color(0xFF4C4639),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }

        // Main Card (static, shadow-sm / elevation = 2.dp)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFFF4E1B9), shape = RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "KEBUTUHAN HARIAN ANDA",
                            color = Color(0xFF4C4639),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$calories Kalori",
                            color = Color(0xFF705C1B),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF2DC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🍽️", fontSize = 24.sp)
                    }
                }

                // Nutrient chips (simple Row + Box, NO icons)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Protein
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF3E0))
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "💪 Protein ${protein}g",
                            fontSize = 12.sp,
                            color = Color(0xFF705C1B),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Fiber
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF1F8E9))
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🌿 Serat ${fiber}g",
                            fontSize = 12.sp,
                            color = Color(0xFF576415),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Vitamin
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFAFAFA))
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✅ Vitamin",
                            fontSize = 12.sp,
                            color = Color(0xFF4C4639),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Hydration Row & Static Progress bar
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "Target Hidrasi — 2.5L/hari",
                        fontSize = 12.sp,
                        color = Color(0xFF4C4639),
                        fontWeight = FontWeight.Medium
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFECC6))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.75f)
                                .background(Color(0xFF576415), shape = CircleShape)
                        )
                    }
                }
            }
        }

        // Recommendation Section (bg #FFF2DC, static)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFF2DC))
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "💡", fontSize = 16.sp)
                    Text(
                        text = "Rekomendasi Menu",
                        color = Color(0xFF241A03),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Inner white box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "Berdasarkan target Anda, kami menyarankan:",
                            fontSize = 14.sp,
                            color = Color(0xFF4C4639)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("2× ")
                                withStyle(style = SpanStyle(color = Color(0xFF705C1B), fontWeight = FontWeight.Bold)) {
                                    append("Quero-T Active")
                                }
                                append(" + 1× ")
                                withStyle(style = SpanStyle(color = Color(0xFFF3A72A), fontWeight = FontWeight.Bold)) {
                                    append("Quero-T FruitBoost")
                                }
                            },
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }

                // Product Horizontal Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Box 1 (Active)
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD7BD72).copy(alpha = 0.3f))
                            .border(width = 1.dp, color = Color(0xFFD7BD72), shape = RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🍞", fontSize = 32.sp)
                    }

                    // Box 2 (FruitBoost)
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF3A72A).copy(alpha = 0.2f))
                            .border(width = 1.dp, color = Color(0xFFF3A72A).copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🥐", fontSize = 32.sp)
                    }

                    // Box 3 (+)
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(width = 2.dp, color = Color(0xFFCEC6B4), shape = RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "+", fontSize = 20.sp, color = Color(0xFF7D7667), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // CTA Button with mt-8 (normal flow, static position)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3A72A)),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mulai Perjalanan Sehat →",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

