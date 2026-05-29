package com.example.data

import kotlinx.coroutines.flow.Flow

class QueroRepository(
    private val db: AppDatabase
) {
    val userProfileFlow: Flow<UserProfile?> = db.userProfileDao().getUserProfileFlow()
    val cartItemsFlow: Flow<List<CartItem>> = db.cartDao().getCartItemsFlow()
    val nutritionLogsFlow: Flow<List<NutritionLog>> = db.nutritionLogDao().getNutritionLogsFlow()
    val aiConfigFlow: Flow<AiConfig?> = db.aiConfigDao().getAiConfigFlow()

    suspend fun getUserProfile(): UserProfile? = db.userProfileDao().getUserProfile()
    
    suspend fun saveUserProfile(profile: UserProfile) {
        db.userProfileDao().insertOrUpdateUserProfile(profile)
    }

    suspend fun addToCart(product: BreadProduct, quantity: Int = 1) {
        val existingItems = db.cartDao().getCartItems()
        val existing = existingItems.find { it.productId == product.id }
        if (existing != null) {
            db.cartDao().updateQuantity(product.id, existing.quantity + quantity)
        } else {
            db.cartDao().insertCartItem(
                CartItem(
                    productId = product.id,
                    name = product.name,
                    category = product.category,
                    tagline = product.subtitle,
                    price = product.price,
                    calories = product.calories,
                    protein = product.protein,
                    quantity = quantity,
                    colorHex = product.colorHex
                )
            )
        }
    }

    suspend fun updateCartQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            db.cartDao().deleteCartItem(productId)
        } else {
            db.cartDao().updateQuantity(productId, quantity)
        }
    }

    suspend fun removeFromCart(productId: Int) {
        db.cartDao().deleteCartItem(productId)
    }

    suspend fun getCartItems(): List<CartItem> = db.cartDao().getCartItems()

    suspend fun clearCart() {
        db.cartDao().clearCart()
    }

    suspend fun addNutritionLog(productName: String, calories: Int, protein: Int, quantity: Int, timeLabel: String, isConsumed: Boolean = true) {
        db.nutritionLogDao().insertLog(
            NutritionLog(
                date = "Hari Ini",
                timeLabel = timeLabel,
                productName = productName,
                quantity = quantity,
                calories = calories,
                protein = protein,
                isConsumed = isConsumed
            )
        )
    }

    suspend fun updateLogStatus(id: Long, isConsumed: Boolean) {
        db.nutritionLogDao().updateConsumedStatus(id, isConsumed)
    }

    suspend fun deleteLog(id: Long) {
        db.nutritionLogDao().deleteLog(id)
    }

    suspend fun saveAiConfig(config: AiConfig) {
        db.aiConfigDao().insertOrUpdateAiConfig(config)
    }

    suspend fun getAiConfig(): AiConfig? = db.aiConfigDao().getAiConfig()

    suspend fun initializeDemoData() {
        // Initialize default user with demo metrics
        val currentProfile = db.userProfileDao().getUserProfile()
        if (currentProfile == null) {
            db.userProfileDao().insertOrUpdateUserProfile(
                UserProfile(
                    id = 1,
                    name = "Ahmad Hidayat",
                    age = 22,
                    weight = 65.0,
                    height = 170.0,
                    activityLevel = "Sedang",
                    healthGoal = "Menjaga Kesehatan",
                    restrictions = "Tidak Ada",
                    calculatedCalories = 2100,
                    streakDays = 5,
                    totalOrders = 12,
                    subscriptionActive = true,
                    subscriptionPlan = "Weekly"
                )
            )
        }

        // Initialize default cart: Quero-T Active x2 + FruitBoost x1 = Rp 60.000
        val currentCart = db.cartDao().getCartItems()
        if (currentCart.isEmpty()) {
            val active = ProductData.products.find { it.id == 1 }
            val fruit = ProductData.products.find { it.id == 6 }
            if (active != null) {
                addToCart(active, 2)
            }
            if (fruit != null) {
                addToCart(fruit, 1)
            }
        }

        // Initialize nutrition timeline logs
        // Today timeline:
        // - 07:30 Quero-T Active x2 - 360 kcal (Consumed)
        // - 12:00 Quero-T FruitBoost x1 - 90 kcal (Consumed)
        // - 15:00 Quero-T Energy x1 - scheduled (Not Consumed yet)
        val currentLogs = db.nutritionLogDao().getNutritionLogsFlow()
        // Check if logs are empty. Let's do a simple check.
        // For security or simplicity, we can clear and pre-fill or only pre-fill once.
        val existing = db.nutritionLogDao().clearLogs() // Clear to start fresh with demo flow
        db.nutritionLogDao().insertLog(
            NutritionLog(
                date = "Hari Ini",
                timeLabel = "07:30 Pagi",
                productName = "Quero-T Active",
                quantity = 2,
                calories = 360,
                protein = 24,
                isConsumed = true
            )
        )
        db.nutritionLogDao().insertLog(
            NutritionLog(
                date = "Hari Ini",
                timeLabel = "12:00 Siang",
                productName = "Quero-T FruitBoost",
                quantity = 1,
                calories = 90,
                protein = 4,
                isConsumed = true
            )
        )
        db.nutritionLogDao().insertLog(
            NutritionLog(
                date = "Hari Ini",
                timeLabel = "15:00 Sore",
                productName = "Quero-T Energy",
                quantity = 1,
                calories = 120,
                protein = 8,
                isConsumed = false
            )
        )

        // Initialize AI Configurations
        val aiConfig = db.aiConfigDao().getAiConfig()
        if (aiConfig == null) {
            db.aiConfigDao().insertOrUpdateAiConfig(
                AiConfig(id = 1)
            )
        }
    }
}
