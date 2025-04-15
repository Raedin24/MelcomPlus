package com.example.melcomplus.data

import android.util.Log
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.models.Subcategory
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader

object CsvProcessor {

    fun loadData(
        productCsv: InputStream,
        categoryCsv: InputStream,
        subcategoryCsv: InputStream
    ) {
        val categoriesMap = mutableMapOf<String, String>() // Name -> Image
        val subcategoriesMap = mutableMapOf<String, String>()

        // Load categories
        CSVReader(InputStreamReader(categoryCsv)).use { reader ->
            reader.readNext() // skip header
            reader.forEach { row ->
                if (row.size >= 2) {
                    val name = row[0].trim()
                    val image = row[1].trim()
                    categoriesMap[name] = image
                }
            }
        }

        // Load subcategories
        CSVReader(InputStreamReader(subcategoryCsv)).use { reader ->
            reader.readNext() // skip header
            reader.forEach { row ->
                if (row.size >= 2) {
                    val name = row[0].trim()
                    val image = row[1].trim()
                    subcategoriesMap[name] = image
                }
            }
        }

        // Group products
        val catalogMap = mutableMapOf<String, MutableMap<String, MutableList<Product>>>()

        CSVReader(InputStreamReader(productCsv)).use { reader ->
            reader.readNext() // skip header
            reader.forEach { row ->
                if (row.size >= 9) {
                    val sku = row[0].trim()
                    val productName = row[1].trim()
                    val category = row[2].trim()
                    val subCategory = row[3].trim()
                    val type = row[4].trim()
                    val price = row[5].toDoubleOrNull() ?: 0.0
                    val description = row[6].trim()
                    val imageUrl = row[8].trim()

                    val product = Product(
                        sku = sku,
                        name = productName,
                        details = description,
                        price = price,
                        imageUrl = imageUrl,
                        type = type
                    )

                    val subMap = catalogMap.getOrPut(category) { mutableMapOf() }
                    val productList = subMap.getOrPut(subCategory) { mutableListOf() }
                    productList.add(product)
                }
            }
        }

        // Build final structure
        val finalCategories = catalogMap.map { (catName, subMap) ->
            val subcategories = subMap.map { (subName, products) ->
                Subcategory(
                    name = subName,
                    imageUrl = subcategoriesMap[subName] ?: "",
                    products = products
                )
            }
            Category(
                name = catName,
                icon = categoriesMap[catName] ?: "",
                subcategories = subcategories
            )
        }

        // Set in repository
        CategoryRepository.categories = finalCategories
        Log.d("CsvProcessor", "Loaded ${finalCategories.size} categories with subcategories and products.")
    }
}
