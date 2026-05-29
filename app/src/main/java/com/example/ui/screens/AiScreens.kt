package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProfile
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.QueroViewModel
import kotlinx.coroutines.delay

@Composable
fun AiMainScreen(
    viewModel: QueroViewModel
) {
    val activeScreen by viewModel.aiScreen.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        // TOP AI SYSTEM HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AiPurple)
                .padding(vertical = 4.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                viewModel.switchRole("USER")
                viewModel.navigateUser("LOGIN")
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Kembali ke Login",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "🤖 AI SYSTEM PANEL", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "Engine v3.1-Beta", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (activeScreen) {
                "PROCESSING" -> AiProcessingScreen(viewModel)
                "LOGIC" -> AiLogicScreen(viewModel)
                "INSIGHTS" -> AiInsightsScreen(viewModel)
                "CONFIG" -> AiConfigScreen(viewModel)
                "ALERTS" -> AiNotificationScreen(viewModel)
            }
        }

        // Custom Navigation for AI Engine screens
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color.White)
                .border(width = 1.dp, color = Color(0xFFE2E8F0), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserNavTab(
                label = "Proses",
                icon = Icons.Filled.Refresh,
                activeIcon = Icons.Filled.Refresh,
                active = activeScreen == "PROCESSING",
                onClick = { viewModel.navigateAi("PROCESSING") }
            )
            UserNavTab(
                label = "Logika AI",
                icon = Icons.Filled.List,
                activeIcon = Icons.Filled.List,
                active = activeScreen == "LOGIC",
                onClick = { viewModel.navigateAi("LOGIC") }
            )
            UserNavTab(
                label = "Insights",
                icon = Icons.Filled.Star,
                activeIcon = Icons.Filled.Star,
                active = activeScreen == "INSIGHTS",
                onClick = { viewModel.navigateAi("INSIGHTS") }
            )
            UserNavTab(
                label = "Aturan",
                icon = Icons.Filled.Settings,
                activeIcon = Icons.Filled.Settings,
                active = activeScreen == "CONFIG",
                onClick = { viewModel.navigateAi("CONFIG") }
            )
            UserNavTab(
                label = "Alerts",
                icon = Icons.Filled.Notifications,
                activeIcon = Icons.Filled.Notifications,
                active = activeScreen == "ALERTS",
                onClick = { viewModel.navigateAi("ALERTS") }
            )
        }
    }
}

// --- AI SCREEN 1: PROCESSING ANIMATION ---
@Composable
fun AiProcessingScreen(viewModel: QueroViewModel) {
    // Progress floats linearly from 0f to 1f over 4000ms
    var progressFloat by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(4000, easing = LinearEasing)
        ) { value, _ ->
            progressFloat = value
        }
        // After 4s auto-navigate
        viewModel.navigateAi("LOGIC")
    }

    // Concentric ring animations
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_rings")

    // Ring 1 (delay 0ms):
    val ring1Scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 2.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 0, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring1Scale"
    )
    val ring1Opacity by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 0, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring1Opacity"
    )

    // Ring 2 (delay 400ms):
    val ring2Scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 2.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring2Scale"
    )
    val ring2Opacity by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring2Opacity"
    )

    // Ring 3 (delay 800ms):
    val ring3Scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 2.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring3Scale"
    )
    val ring3Opacity by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring3Opacity"
    )

    // Slow spinning brain/spark icon (3 seconds per full rotation)
    val spinAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spinAngle"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Pulse Rings Container
        Box(
            modifier = Modifier
                .size(220.dp),
            contentAlignment = Alignment.Center
        ) {
            // Pulsing Ring 3
            Box(
                modifier = Modifier
                    .size(100.dp * ring3Scale)
                    .clip(CircleShape)
                    .border(2.dp, AiPurple.copy(alpha = ring3Opacity), CircleShape)
            )
            // Pulsing Ring 2
            Box(
                modifier = Modifier
                    .size(100.dp * ring2Scale)
                    .clip(CircleShape)
                    .border(2.dp, AiPurple.copy(alpha = ring2Opacity), CircleShape)
            )
            // Pulsing Ring 1
            Box(
                modifier = Modifier
                    .size(100.dp * ring1Scale)
                    .clip(CircleShape)
                    .border(2.dp, AiPurple.copy(alpha = ring1Opacity), CircleShape)
            )

            // Inner Core containing slow spinning AI brain icon
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(AiPurple.copy(alpha = 0.15f))
                    .border(2.dp, AiPurple, CircleShape)
                    .rotate(spinAngle),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "AI processing Brain",
                    tint = AiPurple,
                    modifier = Modifier.size(54.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Sistem Rekomendasi Gizi Cerdas", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = "Sedang mensinkronisasikan profil Anda dengan satelit pangan...", color = Color.Gray, fontSize = 11.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(36.dp))

        // Sequential step texts fading in
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProcessingStepItem(
                text = "Membaca data profil kamu...",
                visible = progressFloat >= 0.125f,     // 0.5s / 4s
                finished = progressFloat >= 0.375f      // Finish when Step 2 starts
            )
            ProcessingStepItem(
                text = "Menghitung kebutuhan kalori harian...",
                visible = progressFloat >= 0.375f,     // 1.5s / 4s
                finished = progressFloat >= 0.625f      // Finish when Step 3 starts
            )
            ProcessingStepItem(
                text = "Mencocokkan produk Quero-T...",
                visible = progressFloat >= 0.625f,     // 2.5s / 4s
                finished = progressFloat >= 0.875f      // Finish when Step 4 starts
            )
            ProcessingStepItem(
                text = "Menyiapkan rekomendasi personalmu ···",
                visible = progressFloat >= 0.875f,     // 3.5s / 4s
                finished = false
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Progress bar filling from 0% to 100% over 4 seconds
        // Using exactly linear-gradient(90deg, #566314, #F3A72A)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progressFloat)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF566314), Color(0xFFF3A72A))
                        )
                    )
            )
        }
    }
}

@Composable
fun ProcessingStepItem(text: String, visible: Boolean, finished: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(400)) + 
                slideInVertically(
                    initialOffsetY = { 16 },
                    animationSpec = tween(400)
                )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (finished) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle, 
                    contentDescription = "Finished", 
                    tint = OliveGreen, 
                    modifier = Modifier.size(18.dp)
                )
            } else {
                CircularProgressIndicator(
                    color = WarmOrange, 
                    strokeWidth = 2.dp, 
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text, 
                color = DarkBrownText, 
                fontSize = 13.sp, 
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// --- AI SCREEN 2: RECOMMENDATION ENGINE (logic) ---
@Composable
fun AiLogicScreen(viewModel: QueroViewModel) {
    val profile by viewModel.userProfile.collectAsState()

    // Calculated fields based on profile
    val user = profile ?: UserProfile()
    val bmi = user.weight / ((user.height / 100.0) * (user.height / 100.0))
    val bmiStatus = when {
        bmi < 18.5 -> "Kurus (Underweight)"
        bmi < 25.0 -> "Normal"
        bmi < 30.0 -> "Gemuk (Overweight)"
        else -> "Obesitas (Obese)"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Hitung Kalori Sains Gizi",
                color = DarkBrownText,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Formula Mifflin-St Jeor visualization Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Formula Mifflin-St Jeor & TDEE", color = OliveGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "BMR = (10 × W) + (6.25 × H) - (5 × A) + CONST\nTDEE = BMR × [Faktor Aktivitas]",
                        color = DarkBrownText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp
                    )
                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                    Text(
                        text = "Data Terdaftar:\n• Berat (W): ${user.weight} kg\n• Tinggi (H): ${user.height} cm\n• Usia (A): ${user.age} tahun\n• Faktor Aktivitas: ${user.activityLevel} (= 1.55)\n\nTDEE Terkalkulasi: ${user.calculatedCalories} Kalori",
                        color = Color.Gray,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        item { Text(text = "Tabel Kecocokan Jenis Roti", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp) }

        // Rules mapping table
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RuleRow(range = "BMI Kurang (<18.5)", match = "Protein+, Energy")
                RuleRow(range = "BMI Ideal (18.5 - 24.9)", match = "Active, FruitBoost")
                RuleRow(range = "BMI Berlebih (25.0 - 29.9)", match = "Fit, Low GI, Lite")
                RuleRow(range = "BMI Obesitas (>= 30.0)", match = "Lite, Low GI, Senior")
            }
        }
    }
}

@Composable
fun RuleRow(range: String, match: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = range, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(AiPurple.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = match, color = AiPurple, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// --- AI SCREEN 3: NUTRITION INSIGHTS ---
@Composable
fun AiInsightsScreen(viewModel: QueroViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Rasio Kecukupan Nutrisi Mingguan", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(12.dp))

        // Radar Chart integrated
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            RadarChart(values = listOf(0.78f, 0.65f, 0.45f, 0.90f, 0.70f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 3 Insight cards
        Text(text = "Rangkuman Rekomendasi Cerdas", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            InsightCard(emoji = "✅", text = "Asupan vitamin harianmu sangat baik!", color = Color(0xFFD1FAE5), textColor = Color(0xFF065F46))
            InsightCard(emoji = "⚠️", text = "Asupan serat masih 45% dari target gizi seimbang", color = Color(0xFFFEE2E2), textColor = Color(0xFF991B1B))
            InsightCard(emoji = "📈", text = "Konsistensimu meningkat 23% minggu ini!", color = Color(0xFFFEF3C7), textColor = Color(0xFF92400E))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Model accuracy indicator
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = "Akurasi Prediksi Model AI: 78%", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.78f)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(AiPurple)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun InsightCard(emoji: String, text: String, color: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, color = textColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

// --- AI SCREEN 4: ADMIN CONFIG PANEL ---
@Composable
fun AiConfigScreen(viewModel: QueroViewModel) {
    val expanded by viewModel.expandedConfigAccordion.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Kelola Aturan Pencocokan Gizi",
                color = DarkBrownText,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Accordion 1
        item {
            ConfigAccordionRow(
                title = "Rule 1: BMI-Based Mapping",
                expanded = expanded["BMI"] ?: false,
                onClick = { viewModel.toggleConfigAccordion("BMI") },
                content = "• BMI <18.5 -> Protein+, Energy\n• BMI 18.5-24.9 -> Active, FruitBoost\n• BMI 25-29.9 -> Fit, Low GI, Lite\n• BMI >=30 -> Lite, Low GI, Senior"
            )
        }

        // Accordion 2
        item {
            ConfigAccordionRow(
                title = "Rule 2: Activity Mapping",
                expanded = expanded["Activity"] ?: false,
                onClick = { viewModel.toggleConfigAccordion("Activity") },
                content = "• Ringan -> FruitBoost + Fit\n• Sedang -> Active + FruitBoost + Energy\n• Berat -> Protein+ + Active + Energy"
            )
        }

        // Accordion 3
        item {
            ConfigAccordionRow(
                title = "Rule 3: Goal Mapping",
                expanded = expanded["Goal"] ?: false,
                onClick = { viewModel.toggleConfigAccordion("Goal") },
                content = "• Diet -> Fit + Lite + Low GI\n• Massa Otot -> Protein+ + Active + Energy\n• Menjaga Kesehatan -> Active + FruitBoost + Fit"
            )
        }

        // Accordion 4
        item {
            ConfigAccordionRow(
                title = "Rule 4: Time of Day Mapping",
                expanded = expanded["Time"] ?: false,
                onClick = { viewModel.toggleConfigAccordion("Time") },
                content = "• Pagi (06-10) -> Active, Energy\n• Sore (15-17) -> FruitBoost, Lite\n• Malam (18-21) -> Fit, Low GI"
            )
        }

        // Button save
        item {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Simpan Konfigurasi Aturan", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun ConfigAccordionRow(
    title: String,
    expanded: Boolean,
    onClick: () -> Unit,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = OliveGreen
            )
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, color = Color.Gray, fontSize = 12.sp, lineHeight = 18.sp)
        }
    }
}

// --- AI SCREEN 5: NOTIFICATION CENTER ---
@Composable
fun AiNotificationScreen(viewModel: QueroViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Penerima Pemberitahuan Cerdas",
                color = DarkBrownText,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            AlertNotificationRow(emoji = "⚠️", title = "Asupan serat 3 hari di bawah target", desc = "Tambahkan konsumsi roti gandum utuh kami sesegera mungkin.")
        }
        item {
            AlertNotificationRow(emoji = "🏆", title = "7 hari streak konsumsi rutin!", desc = "Selamat, kumpulkan lencana prestasi baru gizi seimbang Anda.")
        }
        item {
            AlertNotificationRow(emoji = "📦", title = "Stok Quero-T Protein+ tinggal 3 pcs", desc = "Peringatan kritis bagi toko untuk segera memproduksi persediaan.")
        }
        item {
            AlertNotificationRow(emoji = "✨", title = "Model AI diperbarui", desc = "Akurasi penaksir meningkat dari 72% menjadi 78%.")
        }
    }
}

@Composable
fun AlertNotificationRow(emoji: String, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = desc, color = Color.Gray, fontSize = 11.sp)
        }
    }
}
