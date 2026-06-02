package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PoppinsFontFamily

@Composable
fun MainBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        Triple("HOME", Icons.Filled.Home, "Beranda"),
        Triple("NUTRITION", Icons.Filled.Star, "Nutrisi"),
        Triple("SMART_RECOMMEND", Icons.Filled.Notifications, "Notifikasi"),
        Triple("PROFILE", Icons.Filled.Person, "Profil")
    )

    NavigationBar(
        containerColor = Color(0xFFFFF8F1).copy(alpha = 0.95f),
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        items.forEach { (route, icon, label) ->
            val selected = currentRoute.uppercase() == route.uppercase()
            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) onNavigate(route) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontFamily = PoppinsFontFamily,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
                        letterSpacing = 0.2.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF576415),
                    selectedTextColor = Color(0xFF576415),
                    indicatorColor = Color(0xFFDBEB8D),
                    unselectedIconColor = Color(0xFF7D7667),
                    unselectedTextColor = Color(0xFF7D7667)
                )
            )
        }
    }
}
