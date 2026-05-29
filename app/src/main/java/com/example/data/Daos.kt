package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfile(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(profile: UserProfile)
}

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItemsFlow(): Flow<List<CartItem>>

    @Query("SELECT * FROM cart_items")
    suspend fun getCartItems(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}

@Dao
interface NutritionLogDao {
    @Query("SELECT * FROM nutrition_logs ORDER BY date DESC, id DESC")
    fun getNutritionLogsFlow(): Flow<List<NutritionLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: NutritionLog)

    @Query("UPDATE nutrition_logs SET isConsumed = :isConsumed WHERE id = :id")
    suspend fun updateConsumedStatus(id: Long, isConsumed: Boolean)

    @Query("DELETE FROM nutrition_logs WHERE id = :id")
    suspend fun deleteLog(id: Long)

    @Query("DELETE FROM nutrition_logs")
    suspend fun clearLogs()
}

@Dao
interface AiConfigDao {
    @Query("SELECT * FROM ai_configurations WHERE id = 1 LIMIT 1")
    fun getAiConfigFlow(): Flow<AiConfig?>

    @Query("SELECT * FROM ai_configurations WHERE id = 1 LIMIT 1")
    suspend fun getAiConfig(): AiConfig?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAiConfig(config: AiConfig)
}
