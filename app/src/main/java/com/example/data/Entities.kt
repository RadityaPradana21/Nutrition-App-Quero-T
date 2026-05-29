package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1, // Only one user profile in local DB for this demo/app
    val name: String = "Ahmad Hidayat",
    val age: Int = 22,
    val weight: Double = 65.0,
    val height: Double = 170.0,
    val activityLevel: String = "Sedang", // Ringan, Sedang, Berat
    val healthGoal: String = "Menjaga Kesehatan", // Menjaga Kesehatan, Diet, Jaga Berat Badan, Massa Otot, Aktivitas Tinggi
    val restrictions: String = "Tidak Ada", // Comma separated, e.g., "Gluten, Laktosa", "Tidak Ada"
    val calculatedCalories: Int = 2100,
    val streakDays: Int = 5,
    val totalOrders: Int = 12,
    val subscriptionActive: Boolean = true,
    val subscriptionPlan: String = "Weekly" // Weekly or Monthly
)

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int,
    val name: String,
    val category: String,
    val tagline: String,
    val price: Double,
    val calories: Int,
    val protein: Int,
    val quantity: Int,
    val colorHex: String
)

@Entity(tableName = "nutrition_logs")
data class NutritionLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // e.g. "2026-05-28" or "Hari Ini"
    val timeLabel: String, // e.g. "07:30 Pagi", "12:00 Siang", "15:00 Sore"
    val productName: String,
    val quantity: Int,
    val calories: Int,
    val protein: Int,
    val isConsumed: Boolean = true // true: Checked, false: Scheduled
)

@Entity(tableName = "ai_configurations")
data class AiConfig(
    @PrimaryKey val id: Int = 1,
    // Store rule configs as editable strings if needed, initialized with default values
    val bmiRuleUnderweight: String = "Protein+, Energy",
    val bmiRuleNormal: String = "Active, FruitBoost",
    val bmiRuleOverweight: String = "Fit, Low GI, Lite",
    val bmiRuleObese: String = "Lite, Low GI, Senior",
    val activityRuleLight: String = "FruitBoost + Fit",
    val activityRuleMedium: String = "Active + FruitBoost + Energy",
    val activityRuleHeavy: String = "Protein+ + Active + Energy",
    val goalRuleDiet: String = "Fit + Lite + Low GI",
    val goalRuleMuscle: String = "Protein+ + Active + Energy",
    val goalRuleHealth: String = "Active + FruitBoost + Fit"
)

// In-Memory or constant product definition helper
data class BreadProduct(
    val id: Int,
    val name: String,
    val category: String, // Roti or Kue
    val subtitle: String,
    val description: String,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int,
    val fiber: Int,
    val vitamins: String,
    val price: Double,
    val badge: String = "", // BEST, BARU, KIDS or empty
    val tags: List<String> = emptyList(),
    val suitableFor: List<String> = emptyList(),
    val colorHex: String = "#D7BD72"
)

object ProductData {
    val products = listOf(
        BreadProduct(
            id = 1,
            name = "Quero-T Active",
            category = "Roti",
            subtitle = "Energi & Vitalitas",
            description = "Roti sehat gandum utuh dengan formula karbohidrat kompleks berkualitas tinggi untuk vitalitas dan energi sepanjang hari Anda. Kaya akan Vitamin B.",
            calories = 180,
            protein = 12,
            fat = 4,
            carbs = 24,
            fiber = 3,
            vitamins = "Vit B, C",
            price = 15000.0,
            badge = "BEST",
            tags = listOf("Tinggi Protein", "Bebas Pengawet"),
            suitableFor = listOf("Gym Goers", "Pekerja Aktif"),
            colorHex = "#D7BD72"
        ),
        BreadProduct(
            id = 2,
            name = "Quero-T Fit",
            category = "Roti",
            subtitle = "Rendah Kalori",
            description = "Roti gandum rendah kalori yang sangat cocok bagi Anda yang sedang menjalani program pengurangan berat badan namun menginginkan rasa lezat.",
            calories = 120,
            protein = 8,
            fat = 2,
            carbs = 18,
            fiber = 4,
            vitamins = "Vit B12",
            price = 13000.0,
            badge = "",
            tags = listOf("Low Sugar", "Tinggi Serat"),
            suitableFor = listOf("Diet Sehat", "Pekerja Aktif"),
            colorHex = "#F5EDD8"
        ),
        BreadProduct(
            id = 3,
            name = "Quero-T Protein+",
            category = "Roti",
            subtitle = "High Protein",
            description = "Diformulasikan khusus dengan protein kedelai murni dan kacang-kacangan untuk membantu membangun serta mempertahankan massa otot Anda.",
            calories = 200,
            protein = 18,
            fat = 5,
            carbs = 22,
            fiber = 2,
            vitamins = "Vit B Complex",
            price = 18000.0,
            badge = "BARU",
            tags = listOf("Sangat Tinggi Protein", "Organik"),
            suitableFor = listOf("Gym Goers", "Aktivitas Tinggi"),
            colorHex = "#566314"
        ),
        BreadProduct(
            id = 4,
            name = "Quero-T Senior",
            category = "Roti",
            subtitle = "Kalsium & Serat",
            description = "Roti lembut yang diperkaya dengan Kalsium tinggi dan Vitamin D untuk kesehatan tulang serta sistem pencernaan aktif bagi kalangan lanjut usia.",
            calories = 150,
            protein = 9,
            fat = 3,
            carbs = 20,
            fiber = 6,
            vitamins = "Kalsium, Vit D",
            price = 14000.0,
            badge = "",
            tags = listOf("Tinggi Kalsium", "Tinggi Serat"),
            suitableFor = listOf("Lanjut Usia", "Diet Sehat"),
            colorHex = "#F3A72A"
        ),
        BreadProduct(
            id = 5,
            name = "Quero-T Low GI",
            category = "Roti",
            subtitle = "Diabetes Friendly",
            description = "Roti sehat dengan indeks glikemik rendah (Low GI) sehingga tidak memicu lonjakan gula darah mendadak. Sangat aman dan disarankan bagi penderita diabetes.",
            calories = 130,
            protein = 10,
            fat = 3,
            carbs = 16,
            fiber = 5,
            vitamins = "Chromium, Vit E",
            price = 16000.0,
            badge = "",
            tags = listOf("Indeks Glikemik Rendah", "Bebas Gula"),
            suitableFor = listOf("Penderita Diabetes", "Diet Sehat"),
            colorHex = "#E6DBC0"
        ),
        BreadProduct(
            id = 6,
            name = "Quero-T FruitBoost",
            category = "Kue",
            subtitle = "Vitamin & Antioksidan",
            description = "Kue buah organik sehat dengan antioksidan melimpah dari buah beri liar dan kismis. Menjaga daya tahan tubuh dan menangkal radikal bebas.",
            calories = 90,
            protein = 4,
            fat = 1,
            carbs = 14,
            fiber = 3,
            vitamins = "Vit C, E",
            price = 12000.0,
            badge = "",
            tags = listOf("Tinggi Antioksidan", "Rendah Lemak"),
            suitableFor = listOf("Pecinta Buah", "Anak-Anak"),
            colorHex = "#EFC2B3"
        ),
        BreadProduct(
            id = 7,
            name = "Quero-T Lite",
            category = "Kue",
            subtitle = "Ultra Low Calorie",
            description = "Kue manis sehat yang diolah dengan pemanis daun stevia alami demi kalori seminimal mungkin. Nikmati kelezatan kue tanpa khawatir gemuk.",
            calories = 70,
            protein = 3,
            fat = 1,
            carbs = 10,
            fiber = 2,
            vitamins = "Vit C",
            price = 11000.0,
            badge = "",
            tags = listOf("Tanpa Gula Tambahan", "Kalori Ultra Rendah"),
            suitableFor = listOf("Pelaku Diet Ketat", "Diet Sehat"),
            colorHex = "#CBD2A4"
        ),
        BreadProduct(
            id = 8,
            name = "Quero-T Energy",
            category = "Kue",
            subtitle = "Pre-workout Boost",
            description = "Kue bernutrisi tinggi dengan kombinasi oats, pisang, dan madu organik. Memberikan pasokan energi cepat dan bertahan lama untuk olahraga intens Anda.",
            calories = 120,
            protein = 8,
            fat = 2,
            carbs = 20,
            fiber = 2,
            vitamins = "Vit B6, B12",
            price = 13000.0,
            badge = "",
            tags = listOf("Energi Cepat", "Bebas Gluten"),
            suitableFor = listOf("Gym Goers", "Aktivitas Tinggi"),
            colorHex = "#FFDB58"
        ),
        BreadProduct(
            id = 9,
            name = "Quero-T Kids",
            category = "Kue",
            subtitle = "Nutrisi Anak",
            description = "Kue lucu dengan rasa alami cokelat organik yang diformulasikan dengan Zat Besi dan Vitamin A pendukung pertumbuhan otak dan fisik si kecil secara maksimal.",
            calories = 100,
            protein = 5,
            fat = 3,
            carbs = 15,
            fiber = 2,
            vitamins = "Zat Besi, Vit A",
            price = 12000.0,
            badge = "KIDS",
            tags = listOf("Tinggi Zat Besi", "Disukai Anak-Anak"),
            suitableFor = listOf("Anak Tumbuh Kembang", "Cemilan Sehat"),
            colorHex = "#C39B62"
        )
    )
}

fun getProductEmoji(name: String): String {
    return when {
        name.contains("Active", ignoreCase = true) -> "🍞"
        name.contains("Fit", ignoreCase = true) -> "🥖"
        name.contains("Protein+", ignoreCase = true) -> "💪🍞"
        name.contains("Senior", ignoreCase = true) -> "🌾"
        name.contains("Low GI", ignoreCase = true) -> "🫓"
        name.contains("FruitBoost", ignoreCase = true) -> "🧁"
        name.contains("Lite", ignoreCase = true) -> "🍰"
        name.contains("Energy", ignoreCase = true) -> "⚡🍞"
        name.contains("Kids", ignoreCase = true) -> "🎠"
        else -> "🍞"
    }
}
