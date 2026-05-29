package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

// Simulates the mobile web shell layout rules for desktop / tablet streaming viewing
@Composable
fun PhoneShell(
    modifier: Modifier = Modifier,
    roleSwitcher: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isTablet = screenWidth > 450.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E)), // Dark outer background
        contentAlignment = Alignment.Center
    ) {
        // Outer dark area role switcher on wider screens
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            roleSwitcher()
        }

        // Inner Phone Shell
        Box(
            modifier = modifier
                .then(
                    if (isTablet) {
                        Modifier
                            .width(390.dp)
                            .height(844.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .border(8.dp, Color(0xFF2A2A3E), RoundedCornerShape(40.dp))
                    } else {
                        Modifier.fillMaxSize()
                    }
                )
                .background(BgCream)
        ) {
            content()
        }
    }
}

// Decorative Status Bar at the top of every screen
@Composable
fun MockStatusBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = OliveGreen,
    contentColor: Color = Color.White
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "09:41",
            color = contentColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Signal",
                tint = contentColor,
                modifier = Modifier.size(15.dp)
            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Wifi",
                tint = contentColor,
                modifier = Modifier.size(15.dp)
            )
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Battery",
                tint = contentColor,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

// Floating Role Switcher Pill (integrated beautifully)
@Composable
fun MainRoleSwitcher(
    activeRole: String,
    onRoleSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2A2A3E))
            .border(1.dp, Color(0xFF4A4A6E), RoundedCornerShape(20.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        RoleTab(label = "👤 User", active = activeRole == "USER") {
            onRoleSelected("USER")
        }
        RoleTab(label = "🏪 Seller", active = activeRole == "SELLER") {
            onRoleSelected("SELLER")
        }
        RoleTab(label = "🤖 AI", active = activeRole == "AI") {
            onRoleSelected("AI")
        }
    }
}

@Composable
fun RoleTab(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (active) Color.White else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (active) Color(0xFF1A1A2E) else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
        )
    }
}

// Standard M3 Bottom Navigation for Customer Role
@Composable
fun UserBottomNavigation(
    activeScreen: String,
    cartCount: Int,
    onTabSelected: (String) -> Unit
) {
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
            label = "Beranda",
            icon = Icons.Outlined.Home,
            activeIcon = Icons.Filled.Home,
            active = activeScreen == "HOME",
            onClick = { onTabSelected("HOME") }
        )
        UserNavTab(
            label = "Produk",
            icon = Icons.Outlined.List,
            activeIcon = Icons.Filled.List,
            active = activeScreen == "CATALOG" || activeScreen == "DETAIL",
            onClick = { onTabSelected("CATALOG") }
        )
        UserNavTab(
            label = "Nutrisi",
            icon = Icons.Outlined.Star,
            activeIcon = Icons.Filled.Star,
            active = activeScreen == "NUTRITION",
            onClick = { onTabSelected("NUTRITION") }
        )
        UserNavTab(
            label = "Keranjang",
            icon = Icons.Outlined.ShoppingCart,
            activeIcon = Icons.Filled.ShoppingCart,
            active = activeScreen == "CART",
            badgeCount = cartCount,
            onClick = { onTabSelected("CART") }
        )
        UserNavTab(
            label = "Profil",
            icon = Icons.Outlined.Person,
            activeIcon = Icons.Filled.Person,
            active = activeScreen == "PROFILE",
            onClick = { onTabSelected("PROFILE") }
        )
    }
}

@Composable
fun UserNavTab(
    label: String,
    icon: ImageVector,
    activeIcon: ImageVector,
    active: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(68.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                imageVector = if (active) activeIcon else icon,
                contentDescription = label,
                tint = if (active) OliveGreen else Color(0xFF94A3B8),
                modifier = Modifier.size(24.dp)
            )
            if (badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .offset(x = 6.dp, y = (-4).dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(WarmOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = badgeCount.toString(),
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = if (active) OliveGreen else Color(0xFF94A3B8),
            fontSize = 9.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
        )
        if (active) {
            Box(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(CreamGold)
            )
        }
    }
}

// Standard Bottom Navigation for Seller
@Composable
fun SellerBottomNavigation(
    activeScreen: String,
    onTabSelected: (String) -> Unit
) {
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
            label = "Dasbor",
            icon = Icons.Outlined.Home,
            activeIcon = Icons.Filled.Home,
            active = activeScreen == "DASHBOARD",
            onClick = { onTabSelected("DASHBOARD") }
        )
        UserNavTab(
            label = "Produk",
            icon = Icons.Outlined.List,
            activeIcon = Icons.Filled.List,
            active = activeScreen == "PRODUCTS",
            onClick = { onTabSelected("PRODUCTS") }
        )
        UserNavTab(
            label = "Pesanan",
            icon = Icons.Outlined.Email,
            activeIcon = Icons.Filled.Email,
            active = activeScreen == "ORDERS",
            onClick = { onTabSelected("ORDERS") }
        )
        UserNavTab(
            label = "Stok",
            icon = Icons.Outlined.Settings,
            activeIcon = Icons.Filled.Settings,
            active = activeScreen == "INVENTORY",
            onClick = { onTabSelected("INVENTORY") }
        )
        UserNavTab(
            label = "Subscription",
            icon = Icons.Outlined.Star,
            activeIcon = Icons.Filled.Star,
            active = activeScreen == "SUBSCRIPTION",
            onClick = { onTabSelected("SUBSCRIPTION") }
        )
        UserNavTab(
            label = "Laporan",
            icon = Icons.Outlined.Info,
            activeIcon = Icons.Filled.Info,
            active = activeScreen == "REPORT",
            onClick = { onTabSelected("REPORT") }
        )
    }
}

// Custom Pure Canvas Donut Chart for Nutrition Logs
@Composable
fun DonutChart(
    consumedCalories: Float,
    targetCalories: Float,
    modifier: Modifier = Modifier
) {
    val percentage = if (targetCalories > 0) (consumedCalories / targetCalories).coerceIn(0f, 1f) else 0f
    Box(
        modifier = modifier.size(140.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background Arc
            drawArc(
                color = Color.LightGray.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 30f)
            )
            // Progress Arc
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(WarmOrange, CreamGold)
                ),
                startAngle = -90f,
                sweepAngle = percentage * 360f,
                useCenter = false,
                style = Stroke(width = 30f)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${consumedCalories.toInt()} kcal",
                color = DarkBrownText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "dari ${targetCalories.toInt()}",
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }
}

// Custom Pure Weekly Bar Chart
@Composable
fun WeeklyBarChart(
    heights: List<Float>, // Percentage values from 0.0f to 1.0f
    modifier: Modifier = Modifier
) {
    val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        heights.forEachIndexed { index, percent ->
            val barColor = if (percent >= 0.8f) OliveGreen else WarmOrange
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .width(18.dp)
                        .fillMaxHeight(percent.coerceIn(0.05f, 1f))
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(barColor)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = days[index],
                    fontSize = 10.sp,
                    color = DarkBrownText,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Radar Chart for AI Insights (5 points)
@Composable
fun RadarChart(
    values: List<Float>, // 5 items from 0.0f to 1.0f (Calorie, Protein, Fiber, Vitamin, Fat)
    modifier: Modifier = Modifier
) {
    val labels = listOf("Kalori", "Protein", "Serat", "Vitamin", "Lemak")
    Box(
        modifier = modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.width / 2.5f

            // Draw 5 axis lines
            for (i in 0 until 5) {
                val angle = (Math.PI * 2 * i / 5) - Math.PI / 2
                val target = Offset(
                    (center.x + radius * cos(angle)).toFloat(),
                    (center.y + radius * sin(angle)).toFloat()
                )
                drawLine(
                    color = Color.LightGray,
                    start = center,
                    end = target,
                    strokeWidth = 2f
                )
            }

            // Draw Outer Polygon Guide
            val outerPath = Path()
            for (i in 0 until 5) {
                val angle = (Math.PI * 2 * i / 5) - Math.PI / 2
                val point = Offset(
                    (center.x + radius * cos(angle)).toFloat(),
                    (center.y + radius * sin(angle)).toFloat()
                )
                if (i == 0) outerPath.moveTo(point.x, point.y) else outerPath.lineTo(point.x, point.y)
            }
            outerPath.close()
            drawPath(outerPath, color = Color.LightGray.copy(alpha = 0.5f), style = Stroke(width = 2f))

            // Draw Value Area
            val valuePath = Path()
            for (i in 0 until 5) {
                val valRate = values.getOrElse(i) { 0.5f }
                val angle = (Math.PI * 2 * i / 5) - Math.PI / 2
                val currentRadius = radius * valRate
                val point = Offset(
                    (center.x + currentRadius * cos(angle)).toFloat(),
                    (center.y + currentRadius * sin(angle)).toFloat()
                )
                if (i == 0) valuePath.moveTo(point.x, point.y) else valuePath.lineTo(point.x, point.y)
            }
            valuePath.close()
            drawPath(valuePath, color = AiPurple.copy(alpha = 0.35f))
            drawPath(valuePath, color = AiPurple, style = Stroke(width = 4f))
        }
    }
}
