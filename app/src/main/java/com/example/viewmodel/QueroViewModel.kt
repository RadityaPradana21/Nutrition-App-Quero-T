package com.example.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QueroViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = QueroRepository(db)

    // Roles and navigation
    val currentRole = MutableStateFlow("USER") // USER, SELLER, AI
    val userScreen = MutableStateFlow("SPLASH") // SPLASH, LOGIN, REGISTER, ONBOARDING, HOME, CATALOG, DETAIL, SMART_RECOMMEND, NUTRITION, CART, PROFILE
    val sellerScreen = MutableStateFlow("DASHBOARD") // DASHBOARD, PRODUCTS, ORDERS, INVENTORY, SUBSCRIPTION, REPORT
    val aiScreen = MutableStateFlow("PROCESSING") // PROCESSING, LOGIC, INSIGHTS, CONFIG, ALERTS

    // Room Flows
    val userProfile: StateFlow<UserProfile?> = repository.userProfileFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val cartItems: StateFlow<List<CartItem>> = repository.cartItemsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val nutritionLogs: StateFlow<List<NutritionLog>> = repository.nutritionLogsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val aiConfig: StateFlow<AiConfig?> = repository.aiConfigFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Onboarding Flow Form States ---
    val onboardingStep = MutableStateFlow(1) // 1 to 5
    val nameInput = MutableStateFlow("")
    val genderInput = MutableStateFlow("Laki-laki")
    val ageInput = MutableStateFlow(22)
    val heightInput = MutableStateFlow(170.0)
    val weightInput = MutableStateFlow(65.0)
    val activityInput = MutableStateFlow("Sedang") // Ringan, Sedang, Berat
    val goalInput = MutableStateFlow("Menjaga Kesehatan")
    val restrictionsInput = MutableStateFlow(setOf("Tidak Ada"))

    // Active Catalog/Product State
    val activeFilter = MutableStateFlow("Semua") // Semua, Roti, Kue, Tinggi Protein
    val selectedProductId = MutableStateFlow(1) // Under detail view

    // Checkout Flow States
    val checkoutStep = MutableStateFlow(1) // 1 = Keranjang, 2 = Alamat, 3 = Pembayaran, 4 = Selesai
    val selectedPaymentMethod = MutableStateFlow("E-wallet") // E-wallet, Transfer Bank, COD

    // Seller Dashboard States
    val sellerProductSearch = MutableStateFlow("")
    val sellerProductFilter = MutableStateFlow("Semua")
    val sellerOrderActiveTab = MutableStateFlow("Semua") // Semua, Baru, Diproses, Dikirim, Selesai

    // Seller Inventory edit
    val showEditProductSheet = MutableStateFlow(false)
    val editingProductId = MutableStateFlow<Int?>(null)
    val editingProductStock = MutableStateFlow(50)

    // AI Configuration Accordion
    val expandedConfigAccordion = MutableStateFlow(mapOf(
        "BMI" to true,
        "Activity" to false,
        "Goal" to false,
        "Time" to false
    ))

    // Gemini API State
    val aiExplainLoading = MutableStateFlow(false)
    val aiExplanationText = MutableStateFlow("")

    init {
        // Run pre-fill database operation on first start
        viewModelScope.launch(Dispatchers.IO) {
            repository.initializeDemoData()
            // Pull initial onboarding state values from DB
            val profile = repository.getUserProfile()
            if (profile != null) {
                nameInput.value = profile.name
                ageInput.value = profile.age
                heightInput.value = profile.height
                weightInput.value = profile.weight
                activityInput.value = profile.activityLevel
                goalInput.value = profile.healthGoal
                restrictionsInput.value = profile.restrictions.split(", ").toSet()
            }
        }
    }

    // Role Switcher
    fun switchRole(role: String) {
        currentRole.value = role
    }

    // Navigation wrappers
    fun navigateUser(screen: String) {
        userScreen.value = screen
    }

    fun navigateSeller(screen: String) {
        sellerScreen.value = screen
    }

    fun navigateAi(screen: String) {
        aiScreen.value = screen
    }

    // Onboarding Actions
    fun nextOnboardingStep() {
        if (onboardingStep.value < 5) {
            onboardingStep.value += 1
        }
    }

    fun prevOnboardingStep() {
        if (onboardingStep.value > 1) {
            onboardingStep.value -= 1
        }
    }

    fun toggleRestriction(restriction: String) {
        val current = restrictionsInput.value.toMutableSet()
        if (restriction == "Tidak Ada") {
            current.clear()
            current.add("Tidak Ada")
        } else {
            current.remove("Tidak Ada")
            if (current.contains(restriction)) {
                current.remove(restriction)
                if (current.isEmpty()) current.add("Tidak Ada")
            } else {
                current.add(restriction)
            }
        }
        restrictionsInput.value = current
    }

    // Save Onboarding Metrics, Calculate Calories via Mifflin-St Jeor
    fun completeOnboarding() {
        viewModelScope.launch {
            val name = nameInput.value
            val gender = genderInput.value
            val weight = weightInput.value
            val height = heightInput.value
            val age = ageInput.value
            val activity = activityInput.value
            val goal = goalInput.value

            // BMR Formula
            // Miflin-St Jeor formula taking Gender into account:
            // Laki-laki: (10 * weight) + (6.25 * height) - (5 * age) + 5
            // Perempuan: (10 * weight) + (6.25 * height) - (5 * age) - 161
            val genderOffset = if (gender == "Laki-laki") 5 else -161
            val bmr = (10 * weight) + (6.25 * height) - (5 * age) + genderOffset
            val activityFactor = when (activity) {
                "Ringan" -> 1.375
                "Sedang" -> 1.55
                "Berat" -> 1.725
                else -> 1.375
            }
            var tdee = (bmr * activityFactor).toInt()
            
            // Adjust depending on goal
            when (goal) {
                "Diet" -> tdee -= 350
                "Massa Otot" -> tdee += 300
                "Aktivitas Tinggi" -> tdee += 200
            }

            val currentProfile = repository.getUserProfile() ?: UserProfile()
            val updated = currentProfile.copy(
                name = name,
                age = age,
                weight = weight,
                height = height,
                activityLevel = activity,
                healthGoal = goal,
                restrictions = restrictionsInput.value.joinToString(", "),
                calculatedCalories = tdee
            )
            repository.saveUserProfile(updated)
            userScreen.value = "AI_PROCESSING"
        }
    }

    // Cart Actions
    fun handleAddToCart(productId: Int, quantity: Int = 1) {
        val prod = ProductData.products.find { it.id == productId }
        if (prod != null) {
            viewModelScope.launch {
                repository.addToCart(prod, quantity)
            }
        }
    }

    fun handleUpdateCartQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(productId, quantity)
        }
    }

    fun handleRemoveFromCart(productId: Int) {
        viewModelScope.launch {
            repository.removeFromCart(productId)
        }
    }

    fun handleClearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    // Checkout & Add Nutrition logs on completion
    fun handlePayNow() {
        viewModelScope.launch {
            val cart = repository.getCartItems()
            // Logging purchased products into Nutrition Logs timeline
            cart.forEach { item ->
                repository.addNutritionLog(
                    productName = item.name,
                    calories = item.calories,
                    protein = item.protein,
                    quantity = item.quantity,
                    timeLabel = "Pagi", // Assign default
                    isConsumed = false
                )
            }
            // Clear checkout cart
            repository.clearCart()
            // Increment seller orders statistics as demo trigger
            val profile = repository.getUserProfile()
            if (profile != null) {
                repository.saveUserProfile(
                    profile.copy(
                        totalOrders = profile.totalOrders + 1,
                        streakDays = profile.streakDays + 1
                    )
                )
            }
            checkoutStep.value = 4 // Completed screen
        }
    }

    // Nutrition Logs Action
    fun toggleNutritionLogConsumed(id: Long, currentStatus: Boolean) {
        viewModelScope.launch {
            repository.updateLogStatus(id, !currentStatus)
        }
    }

    // Seller - Inventory Stock Management
    fun selectProductForEditing(id: Int) {
        editingProductId.value = id
        editingProductStock.value = 40 // Default or retrieve
        showEditProductSheet.value = true
    }

    fun saveProductStockAndNutrition() {
        // Save customized values in memory or persistent state
        showEditProductSheet.value = false
    }

    // AI configuration update
    fun toggleConfigAccordion(key: String) {
        val current = expandedConfigAccordion.value.toMutableMap()
        current[key] = !(current[key] ?: false)
        expandedConfigAccordion.value = current
    }

    // --- Gemini Explanations Generator ---
    fun fetchGeminiRecommendationExplanation() {
        val profile = userProfile.value ?: return
        aiExplainLoading.value = true
        aiExplanationText.value = ""

        viewModelScope.launch(Dispatchers.IO) {
            val key = BuildConfig.GEMINI_API_KEY
            if (key.isBlank() || key == "MY_GEMINI_API_KEY") {
                // Return high-quality, simulated local Indonesian explaining based on user statistics
                val explanation = generateLocalExplanation(profile)
                withContext(Dispatchers.Main) {
                    aiExplanationText.value = explanation
                    aiExplainLoading.value = false
                }
                return@launch
            }

            val prompt = """
                Bertindaklah sebagai ahli gizi profesional platform Quero-T (JuaraVibeCoding). Berikan rekomendasi nutrisi personal berbasis data untuk pengguna bernama ${profile.name}, usia ${profile.age} tahun, berat ${profile.weight} kg, tinggi ${profile.height} cm, tingkat aktivitas ${profile.activityLevel}, dan tujuan kesehatan '${profile.healthGoal}'.
                
                Jelaskan kebutuhan gizi hariannya menggunakan perhitungan sains gizi (BMI, Mifflin-St Jeor, TDEE) secara sistematis dan merekomendasikan produk roti/kue Quero-T Active atau Quero-T FruitBoost sesuai sarapan, makan siang, sore, dan malam.
                Format respon harus ramah, menyemangati, dan menggunakan bahasa Indonesia yang baik, santun, dan informatif. Maksimal 3 paragraf.
            """.trimIndent()

            val request = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = prompt)))),
                generationConfig = GenerationConfig(temperature = 0.7f, maxOutputTokens = 1000)
            )

            try {
                val response = RetrofitClient.service.generateContent(key, request)
                val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                withContext(Dispatchers.Main) {
                    aiExplanationText.value = responseText ?: generateLocalExplanation(profile)
                    aiExplainLoading.value = false
                }
            } catch (e: Exception) {
                Log.e("QueroViewModel", "Gemini API failed, using fallback explanation", e)
                val fallback = generateLocalExplanation(profile)
                withContext(Dispatchers.Main) {
                    aiExplanationText.value = "Terjadi kendala jaringan saat menghubungkan ke satelit AI kami. Tenang, ini adalah analisis lokal presisi kami untuk Anda:\n\n$fallback"
                    aiExplainLoading.value = false
                }
            }
        }
    }

    private fun generateLocalExplanation(profile: UserProfile): String {
        val bmi = profile.weight / ((profile.height / 100.0) * (profile.height / 100.0))
        val bmiStatus = when {
            bmi < 18.5 -> "Kurang"
            bmi < 25.0 -> "Normal"
            bmi < 30.0 -> "Berlebih"
            else -> "Obesitas"
        }
        return """
            Halo, ${profile.name}! Berdasarkan analisis gizi cerdas Quero-T, indeks massa tubuh (BMI) Anda berada di angka ${String.format("%.1f", bmi)} yang tergolong **$bmiStatus**. Kebutuhan kalori harian Anda yang dihitung menggunakan rumus Mifflin-St Jeor dengan tingkat aktivitas **${profile.activityLevel}** adalah sebesar **${profile.calculatedCalories} Kalori**.
            
            Untuk menunjang tujuan kesehatan Anda yaitu **${profile.healthGoal}**, sistem merekomendasikan kombinasi produk roti berserat tinggi seperti **Quero-T Active** di pagi hari untuk memberikan energi berkelanjutan, diimbangi dengan **Quero-T FruitBoost** di sore hari guna menyuplai kalsium, vitamin, dan antioksidan alami bagi sistem kekebalan tubuh Anda yang prima!
            
            Tetap konsisten menjaga pola hidup sehat bersama produk organik buatan lokal Indonesia dari Quero-T. Jadwal asupan nutrisi Anda telah disesuaikan secara real-time demi mencapai kebugaran terbaik!
        """.trimIndent()
    }
}
