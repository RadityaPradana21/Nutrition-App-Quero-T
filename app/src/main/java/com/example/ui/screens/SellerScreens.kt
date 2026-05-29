package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.QueroViewModel

@Composable
fun SellerMainScreen(
    viewModel: QueroViewModel
) {
    val activeScreen by viewModel.sellerScreen.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        // TOP SELLER HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(OliveGreen)
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
                    tint = CreamGold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "SELLER PORTAL 🏪", color = CreamGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "Toko Sukses Makmur", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (activeScreen) {
                "DASHBOARD" -> SellerDashboardScreen(viewModel)
                "PRODUCTS" -> SellerProductsScreen(viewModel)
                "ORDERS" -> SellerOrdersScreen(viewModel)
                "INVENTORY" -> SellerInventoryScreen(viewModel)
                "SUBSCRIPTION" -> SellerSubscriptionScreen()
                "REPORT" -> SellerReportScreen()
            }
        }

        SellerBottomNavigation(activeScreen = activeScreen, onTabSelected = { viewModel.navigateSeller(it) })
    }
}

// --- SELLER SCREEN 1: DASHBOARD ---
@Composable
fun SellerDashboardScreen(viewModel: QueroViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(12.dp)) }

        // KPI cards grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    KpiCard(title = "Total Penjualan", value = "Rp 1.250.000", badge = "+12% ↑", color = WarmOrange, modifier = Modifier.weight(1f))
                    KpiCard(title = "Pesanan Masuk", value = "47 Pesanan", badge = "8 Baru", color = OliveGreen, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    KpiCard(title = "Produk Aktif", value = "9 Produk", badge = "2 Tipis", color = CreamGold, modifier = Modifier.weight(1f))
                    KpiCard(title = "Langganan Aktif", value = "134 Orang", badge = "Weekly 89", color = OliveGreen, modifier = Modifier.weight(1f))
                }
            }
        }

        // Sales graph
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Ringkasan Penjualan 7 Hari", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    WeeklyBarChart(heights = listOf(0.4f, 0.65f, 0.35f, 0.45f, 0.9f, 0.75f, 0.2f))
                }
            }
        }

        // Recent limit orders list
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "Pesanan Terbaru", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                SellerOrderRow("ORD-98218", "Budi Santoso", "Quero-T Active x2", "Baru", WarmOrange)
                SellerOrderRow("ORD-98217", "Siti Aminah", "Quero-T Fit x1", "Diproses", CreamGold)
                SellerOrderRow("ORD-98216", "Joko Susilo", "Quero-T FruitBoost x3", "Dikirim", OliveGreen)
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun KpiCard(title: String, value: String, badge: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .border(width = 1.dp, color = Color.LightGray.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp))
                .padding(14.dp)
        ) {
            Text(text = title, color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = DarkBrownText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(text = badge, color = color, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SellerOrderRow(id: String, name: String, items: String, status: String, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = id, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(text = name, color = Color.Gray, fontSize = 10.sp)
            Text(text = items, color = DarkBrownText, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(statusColor)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(text = status, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// --- SELLER SCREEN 2: PRODUCT MANAGEMENT ---
@Composable
fun SellerProductsScreen(viewModel: QueroViewModel) {
    val search by viewModel.sellerProductSearch.collectAsState()

    val filteredProducts = ProductData.products.filter {
        it.name.lowercase().contains(search.lowercase())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Kelola Produk Toko", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                modifier = Modifier.height(32.dp)
            ) {
                Text(text = "+ Tambah", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        OutlinedTextField(
            value = search,
            onValueChange = { viewModel.sellerProductSearch.value = it },
            placeholder = { Text("Cari produk toko anda...") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
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
                .padding(bottom = 12.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredProducts) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color(android.graphics.Color.parseColor(item.colorHex)).copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = getProductEmoji(item.name),
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.name, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(text = "Rp ${item.price.toInt()} | Stok: 40 pcs", color = Color.Gray, fontSize = 10.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { viewModel.selectProductForEditing(item.id) }) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit", tint = OliveGreen)
                        }
                        Switch(
                            checked = true,
                            onCheckedChange = { },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = OliveGreen)
                        )
                    }
                }
            }
        }
    }
}

// --- SELLER SCREEN 3: ORDER MANAGEMENT ---
@Composable
fun SellerOrdersScreen(viewModel: QueroViewModel) {
    val activeTab by viewModel.sellerOrderActiveTab.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Tab Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Semua", "Baru", "Diproses", "Dikirim").forEach { name ->
                val isSelected = activeTab == name
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) OliveGreen else Color.White)
                        .border(1.dp, if (isSelected) OliveGreen else Color.LightGray, RoundedCornerShape(12.dp))
                        .clickable { viewModel.sellerOrderActiveTab.value = name }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = name,
                        color = if (isSelected) Color.White else DarkBrownText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SellerOrderRow("ORD-98218", "Budi Santoso", "Quero-T Active x2", "Baru", WarmOrange)
            }
            item {
                SellerOrderRow("ORD-98217", "Siti Aminah", "Quero-T Fit x1", "Diproses", CreamGold)
            }
            item {
                SellerOrderRow("ORD-98216", "Joko Susilo", "Quero-T FruitBoost x3", "Dikirim", OliveGreen)
            }
        }
    }
}

// --- SELLER SCREEN 4: STOCK & INVENTORY ---
@Composable
fun SellerInventoryScreen(viewModel: QueroViewModel) {
    val editOpen by viewModel.showEditProductSheet.collectAsState()
    val editingId by viewModel.editingProductId.collectAsState()
    val editingStock by viewModel.editingProductStock.collectAsState()

    val currentEditingProduct = ProductData.products.find { it.id == editingId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Warning Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(WarmOrange.copy(alpha = 0.2f))
                .border(1.dp, WarmOrange, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.Warning, contentDescription = null, tint = WarmOrange)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Peringatan: 2 produk stok hampir habis!", color = DarkBrownText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Text(text = "Persentase Stok Tersedia", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
            items(ProductData.products) { item ->
                val mockStock = if (item.id == 1 || item.id == 4) 4 else 42
                val mockTarget = 50
                val progress = mockStock.toFloat() / mockTarget.toFloat()
                val progressColor = if (mockStock < 10) WarmOrange else OliveGreen

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.name, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(text = "$mockStock / $mockTarget pcs", color = Color.Gray, fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .fillMaxHeight()
                                .clip(CircleShape)
                                .background(progressColor)
                        )
                    }
                }
            }
        }

        // Simulated Edit Product Bottom Sheet
        if (editOpen && currentEditingProduct != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .padding(24.dp)
            ) {
                Column {
                    Text(text = "Update Stok: ${currentEditingProduct.name}", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Stok Sedia", color = Color.Gray, fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { if (editingStock > 0) viewModel.editingProductStock.value-- }) {
                                Text("-", fontSize = 24.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
                            }
                            Text(text = editingStock.toString(), color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 12.dp))
                            IconButton(onClick = { viewModel.editingProductStock.value++ }) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.saveProductStockAndNutrition() },
                        colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text(text = "Simpan Perubahan", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// --- SELLER SCREEN 5: SUBSCRIPTION MANAGEMENT ---
@Composable
fun SellerSubscriptionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Summary Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(OliveGreen)
                .padding(16.dp)
        ) {
            Column {
                Text(text = "Manajemen Langganan Aktif", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "89 Pelanggan Mingguan | 45 Pelanggan Bulanan", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Text(text = "Daftar Pelanggan", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
            item {
                SubscriberRow(name = "Andi Setiawan", plan = "Mingguan", nextDelivery = "Besok")
            }
            item {
                SubscriberRow(name = "Mega Lestari", plan = "Bulanan", nextDelivery = "2 Juni")
            }
            item {
                SubscriberRow(name = "Eko Prasetyo", plan = "Mingguan", nextDelivery = "Lusa")
            }
        }
    }
}

@Composable
fun SubscriberRow(name: String, plan: String, nextDelivery: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = null, tint = OliveGreen)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = name, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(text = "Next Delivery: $nextDelivery", color = Color.Gray, fontSize = 10.sp)
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(CreamGold.copy(alpha = 0.25f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = plan, color = DarkBrownText, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// --- SELLER SCREEN 6: SALES REPORT ---
@Composable
fun SellerReportScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Laporan Penjualan Cerdas", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            // Period selector
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = "Bulan Ini", fontSize = 10.sp, color = DarkBrownText, fontWeight = FontWeight.Bold)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Stats Row
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    KpiCard(title = "Pendapatan Bersih", value = "Rp 15.420.000", badge = "Sangat Baik", color = OliveGreen, modifier = Modifier.weight(1f))
                }
            }

            // Sales bar chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Trend Pendapatan", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        WeeklyBarChart(heights = listOf(0.4f, 0.75f, 0.9f, 0.35f, 0.65f, 0.5f, 0.85f))
                    }
                }
            }

            // Best seller ranked list with badges
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Produk Terlaris Teratas", color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    BestProductItem(rank = "🥇", name = "Quero-T Active", sales = "187 terjual")
                    BestProductItem(rank = "🥈", name = "Quero-T FruitBoost", sales = "142 terjual")
                    BestProductItem(rank = "🥉", name = "Quero-T Energy", sales = "98 terjual")
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun BestProductItem(rank: String, name: String, sales: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = rank, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = name, color = DarkBrownText, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Text(text = sales, color = OliveGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}
