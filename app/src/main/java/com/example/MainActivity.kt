package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.data.ProductData
import com.example.ui.components.MainRoleSwitcher
import com.example.ui.components.PhoneShell
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.QueroViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: QueroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val activeRole by viewModel.currentRole.collectAsState()
                val userActiveScreen by viewModel.userScreen.collectAsState()
                val cart by viewModel.cartItems.collectAsState()
                val selectedProdId by viewModel.selectedProductId.collectAsState()

                PhoneShell(
                    modifier = Modifier.fillMaxSize(),
                    roleSwitcher = {
                        MainRoleSwitcher(activeRole = activeRole) { role ->
                            viewModel.switchRole(role)
                            // If switching to AI role or SELLER role, set starting screens
                            if (role == "SELLER") {
                                viewModel.navigateSeller("DASHBOARD")
                            } else if (role == "AI") {
                                viewModel.navigateAi("PROCESSING")
                            } else {
                                viewModel.navigateUser("HOME")
                            }
                        }
                    }
                ) {
                    when (activeRole) {
                        "USER" -> {
                            when (userActiveScreen) {
                                "SPLASH" -> SplashScreen(onNext = { viewModel.navigateUser("LOGIN") })
                                "LOGIN" -> LoginScreen(
                                    onLoginSuccess = { viewModel.navigateUser("ONBOARDING") },
                                    onRegisterSelected = { viewModel.navigateUser("REGISTER") },
                                    onSellerLoginSelected = {
                                        viewModel.switchRole("SELLER")
                                        viewModel.navigateSeller("DASHBOARD")
                                    }
                                )
                                "REGISTER" -> RegisterScreen(
                                    onRegisterSuccess = { viewModel.navigateUser("ONBOARDING") },
                                    onLoginSelected = { viewModel.navigateUser("LOGIN") }
                                )
                                "ONBOARDING" -> OnboardingScreen(viewModel = viewModel)
                                "AI_PROCESSING" -> AiProcessingScreen(
                                    onNavigateNext = { viewModel.navigateUser("ONBOARDING_RESULT") }
                                )
                                "ONBOARDING_RESULT" -> OnboardingResultScreen(
                                    viewModel = viewModel,
                                    onNavigateNext = { viewModel.navigateUser("HOME") },
                                    onNavigateBack = { viewModel.navigateUser("ONBOARDING") }
                                )
                                "HOME" -> HomeScreen(
                                    userName = "Ahmad Hidayat",
                                    targetCalories = 2100,
                                    cartCount = cart.sumOf { it.quantity },
                                    onNavigate = { screen ->
                                        if (screen == "SMART_RECOMMEND") {
                                            viewModel.navigateUser("SMART_RECOMMEND")
                                        } else {
                                            viewModel.navigateUser(screen)
                                        }
                                    },
                                    onSelectProduct = { id ->
                                        viewModel.selectedProductId.value = id
                                        viewModel.navigateUser("DETAIL")
                                    }
                                )
                                "CATALOG" -> ProductCatalogScreen(
                                    cartCount = cart.sumOf { it.quantity },
                                    onNavigate = { viewModel.navigateUser(it) },
                                    onAddToCart = { viewModel.handleAddToCart(it) },
                                    onSelectProduct = { id ->
                                        viewModel.selectedProductId.value = id
                                        viewModel.navigateUser("DETAIL")
                                    }
                                )
                                "DETAIL" -> {
                                    val prod = ProductData.products.find { it.id == selectedProdId } ?: ProductData.products.first()
                                    ProductDetailScreen(
                                        product = prod,
                                        onAddToCart = { quantity ->
                                            viewModel.handleAddToCart(prod.id, quantity)
                                            viewModel.navigateUser("CART")
                                        },
                                        onBack = { viewModel.navigateUser("CATALOG") }
                                    )
                                }
                                "NUTRITION" -> NutritionTrackingScreen(viewModel = viewModel, onNavigate = { viewModel.navigateUser(it) })
                                "CART" -> CartCheckoutScreen(viewModel = viewModel, onNavigate = { viewModel.navigateUser(it) })
                                "PROFILE" -> ProfileScreen(viewModel = viewModel, onNavigate = { viewModel.navigateUser(it) })
                                "SMART_RECOMMEND" -> SmartRecommendationScreen(
                                    viewModel = viewModel,
                                    onNavigate = { viewModel.navigateUser(it) },
                                    onOrderAll = {
                                        // Add Quero-T Active x2 and FruitBoost x1 to cart
                                        viewModel.handleAddToCart(1, 2)
                                        viewModel.handleAddToCart(6, 1)
                                        viewModel.navigateUser("CART")
                                    }
                                )
                            }
                        }
                        "SELLER" -> {
                            SellerMainScreen(viewModel = viewModel)
                        }
                        "AI" -> {
                            AiMainScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
