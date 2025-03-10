package com.example.melcomplus.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProductTile(
    product: Product,
    cartViewModel: CartViewModel,
    onProductClick: (Product) -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartItem = cartItems.find { it.product.name == product.name }

    var itemCount by remember { mutableStateOf(cartItem?.quantity ?: 0) }

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth().height(250.dp)
            .clickable { onProductClick(product) },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(2.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.fillMaxWidth().height(100.dp),
            )

            if (itemCount == 0) {
                Button(
                    onClick = {
                        cartViewModel.addToCart(product)
                        itemCount++
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF800080)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF800080)),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Text("Add to Cart")
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color(0xFF800080), shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFF800080), shape = RoundedCornerShape(10.dp)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            cartItem?.let {
                                if (it.quantity > 1) {
                                    cartViewModel.decreaseQuantity(it)
                                    itemCount--
                                } else {
                                    cartViewModel.removeFromCart(it)
                                    itemCount = 0
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Text("-", color = Color.White)
                    }

                    Text(
                        text = "$itemCount",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    TextButton(
                        onClick = {
                            cartItem?.let { cartViewModel.increaseQuantity(it) }
                            itemCount++
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        Text("+", color = Color.White)
                    }
                }
            }

            Text(
                text = "₵${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.wrapContentSize().padding(bottom = 1.dp),
                textAlign = TextAlign.Left
            )

            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                modifier = Modifier.wrapContentSize().padding(vertical = 2.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Left
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductTilePreview() {
    val sampleProduct = Product(
        name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
        details = "Betty Crocker Supermoist Cakemix Carrot 425G",
        price = 71.99,
        imageUrl = "https://demo8.1hour.in/media/products/18366.png"
    )

    ProductTile(
        product = sampleProduct,
        cartViewModel = CartViewModel(),
        onProductClick = {}
    )
}