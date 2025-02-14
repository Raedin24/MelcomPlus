//CategoryRepository.kt
package com.example.melcomplus.data

import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product

object CategoryRepository {
    val categories = listOf(
        Category(
            name = "FOOD CUPBOARD",
            icon = "pizzimg/pizza.png",
            items = listOf(
                Product(
                    name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
                    details = "Betty Crocker Supermoist Cakemix Carrot 425G",
                    price = 71.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18366.png"
                ),
                Product(
                    name = "GOYA VIENNA SAUSAGE 142Gms 46Oz",
                    details = "Goya Vienna Sausage 142Gms 46Oz",
                    price = 2.40,
                    imageUrl = "https://demo8.1hour.in/media/products/18356.png"
                ),
                Product(
                    name = "ADITYA FRESH ATTA 5 Kg",
                    details = "Aditya Fresh Atta 5 Kg",
                    price = 65.90,
                    imageUrl = "https://demo8.1hour.in/media/products/18886.png"
                )
            )
        ),
        Category(
            name = "BEVERAGES",
            icon = "pizzimg/drink.png",
            items = listOf(
                Product(
                    name = "COCA-COLA 500ml",
                    details = "Coca-Cola 500ml Bottle",
                    price = 1.99,
                    imageUrl = "https://demo8.1hour.in/media/products/12345.png"
                ),
                Product(
                    name = "TROPICANA ORANGE JUICE 1L",
                    details = "Tropicana Orange Juice 1L",
                    price = 4.50,
                    imageUrl = "https://demo8.1hour.in/media/products/67890.png"
                )
            )
        ),
        Category(
            name = "HOUSEHOLD SUPPLIES",
            icon = "pizzimg/cleaning.png",
            items = listOf(
                Product(
                    name = "Ariel Detergent Powder 1Kg",
                    details = "Ariel Detergent Powder 1Kg",
                    price = 8.99,
                    imageUrl = "https://demo8.1hour.in/media/products/22222.png"
                ),
                Product(
                    name = "Domestos Bleach 750ml",
                    details = "Domestos Bleach 750ml",
                    price = 5.50,
                    imageUrl = "https://demo8.1hour.in/media/products/33333.png"
                )
            )
        )
    )
}
