//CategoryRepository.kt


package com.example.melcomplus.data

import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product

object CategoryRepository {
    val categories = listOf(
        Category(
            name = "FOOD CUPBOARD",
            icon = "https://demo8.1hour.in/media/productsCategory/Food_Cupboard_Final_Final_bcbApz0.jpg",
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
                ),
                Product(
                    name = "STLOUIS PURE SUGAR CUBES 500G",
                    details = "Stlouis Pure Sugar Cubes 500G",
                    price = 21.99,
                    imageUrl = "https://demo8.1hour.in/media/products/19383.png"
                ),
                Product(
                    name = "FARAGELLO STRAWBERRY JAM IN JARS 450Gm",
                    details = "Faragello Strawberry Jam In Jars 450Gm",
                    price = 6.99,
                    imageUrl = "https://demo8.1hour.in/media/products/19388.png"
                ),
                Product(
                    name = "FARAGELLO PINEAPPLE JAM IN JAR 450GM",
                    details = "Faragello Pineapple Jam In Jar 450GM",
                    price = 2.99,
                    imageUrl = "https://demo8.1hour.in/media/products/19396.png"
                ),
                Product(
                    name = "KELLOGGS CORNFLAKES BAG PACK 550G",
                    details = "Kelloggs Cornflakes Bag Pack 550G",
                    price = 19.99,
                    imageUrl = "https://demo8.1hour.in/media/products/12981.png"
                ),
                Product(
                    name = "CHTUARA FIELDS FOUL WITH HOMMOS 400Gr",
                    details = "Chtaura Fields Foul With Hommos 400Gr",
                    price = 7.59,
                    imageUrl = "https://demo8.1hour.in/media/products/19576.png"
                ),
                Product(
                    name = "CHINGS HAKKA VEG NOODLES 560G",
                    details = "Chings Hakka Veg Noodles 560G",
                    price = 3.75,
                    imageUrl = "https://demo8.1hour.in/media/products/19744.png"
                ),
                Product(
                    name = "MOTHERS RECIPE BIRYANI PASTE 300G",
                    details = "Mothers Recipe Biryani Paste 300G",
                    price = 2.99,
                    imageUrl = "https://demo8.1hour.in/media/products/19980.png"
                )
            )
        ),
        Category(
            name = "BEVERAGES",
            icon = "https://demo8.1hour.in/media/productsCategory/Beverages_final_ODIv5BB.PNG",
            items = listOf(
                Product(
                    name = "TWININGS CHERRY+CINNAMON HT HS 20S",
                    details = "Twinings Cherry+Cinnamon Ht Hs 20S",
                    price = 21.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18762.png"
                ),
                Product(
                    name = "TWINING BLUEBERRY+APPLE HT HS 20S",
                    details = "Twinings Blueberry+Apple Ht Hs 20S",
                    price = 21.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18760.png"
                ),
                Product(
                    name = "NESQUIK CHOCOLATE POWDER US 10Oz",
                    details = "Nesquik Chocolate Powder Us 10Oz",
                    price = 30.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18357.png"
                ),
                Product(
                    name = "OVALTINE 800G",
                    details = "Ovaltine 800G",
                    price = 156.99,
                    imageUrl = "https://demo8.1hour.in/media/products/17328.png"
                ),
                Product(
                    name = "TATA TEA PREMIUM PEARL 1Kg",
                    details = "Tata Tea Premium Pearl 1Kg",
                    price = 21.25,
                    imageUrl = "https://demo8.1hour.in/media/products/17445.png"
                ),
                Product(
                    name = "ALVARO PEAR 330Ml",
                    details = "Alvaro Pear 330Ml",
                    price = 8.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14538.png"
                ),
                Product(
                    name = "NAJJAR COFFEE BLUE 200G",
                    details = "Najjar Coffee Blue 200G",
                    price = 25.99,
                    imageUrl = "https://demo8.1hour.in/media/products/19632.png"
                ),
                Product(
                    name = "NESTLE FATFREE COFFEEMATE 12 453Gm",
                    details = "Nestle Fatfree Coffeemate 12 453Gm",
                    price = 68.99,
                    imageUrl = "https://demo8.1hour.in/media/products/19640_uf55RKc.png"
                ),
                Product(
                    name = "DON SIMON PEAR & PINEAPPLE JUICE 1L",
                    details = "Don Simon Pear & Pineapple Juice 1L",
                    price = 2.60,
                    imageUrl = "https://demo8.1hour.in/media/products/12167.png"
                ),
                Product(
                    name = "DIMES CAN SOUR CHERRY 330Ml",
                    details = "Dimes Can Sour Cherry 330Ml",
                    price = 1.80,
                    imageUrl = "https://demo8.1hour.in/media/products/12127.png"
                )
            )
        ),
        Category(
            name = "SNACKS",
            icon = "https://demo8.1hour.in/media/productsCategory/Snacks_ZGuyxiB_2fEbNK2.jpg",
            items = listOf(
                Product(
                    name = "PRINGLES CHEESE & ONION 200G",
                    details = "Pringles Cheese & Onion 200G",
                    price = 39.99,
                    imageUrl = "https://demo8.1hour.in/media/products/20132.png"
                ),
                Product(
                    name = "PRINGLES SALT & VINEGAR 165G",
                    details = "Pringles Salt & Vinegar 165G",
                    price = 11.99,
                    imageUrl = "https://demo8.1hour.in/media/products/20130.png"
                ),
                Product(
                    name = "PRINGLES ORIGINAL 149G",
                    details = "Pringles Original 149G",
                    price = 26.99,
                    imageUrl = "https://demo8.1hour.in/media/products/20128.png"
                ),
                Product(
                    name = "NESTLE AFTER EIGHT MINT TIN 300G",
                    details = "Nestle After Eight Mint Tin 300G",
                    price = 0.00,
                    imageUrl = "https://demo8.1hour.in/media/products/18823.png"
                ),
                Product(
                    name = "BAKER MINI CHEDDARS CHESE ONION BISCUIT 33G",
                    details = "Baker Mini Cheddars Chese Onion Biscuit 33G",
                    price = 0.00,
                    imageUrl = "https://demo8.1hour.in/media/products/18827.png"
                ),
                Product(
                    name = "BRITANNIA WAFFERS CHOCOLATE 175Gm",
                    details = "Britannia Waffers Chocolate 175Gm",
                    price = 1.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18936.png"
                ),
                Product(
                    name = "HERSHEYS REESES PEANUT BUTTER CUP 51G",
                    details = "Hersheys Reeses Peanut Butter Cup 51G",
                    price = 9.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18960_1.png"
                ),
                Product(
                    name = "MARILAN CREAM CRACKER 200G",
                    details = "Marilan Cream Cracker 200G",
                    price = 5.49,
                    imageUrl = "https://demo8.1hour.in/media/products/14295.png"
                ),
                Product(
                    name = "HERSHEYS REESES MINITURES POUCH 72G",
                    details = "Hersheys Reeses Miniatures Pouch 72G",
                    price = 15.99,
                    imageUrl = "https://demo8.1hour.in/media/products/19001.png"
                ),
                Product(
                    name = "NESTLE MILKYBAR COOKIES&CREAM 90G",
                    details = "Nestle Milkybar Cookies&Cream 90G",
                    price = 64.99,
                    imageUrl = "https://demo8.1hour.in/media/products/13956.png"
                )
            )
        ),
        Category(
            name = "HOUSEHOLD",
            icon = "https://demo8.1hour.in/media/productsCategory/HouseHold_Final_Final.jpg",
            items = listOf(
                Product(
                    name = "HIT INSECTICIDE SPRAY LEMON 400Ml",
                    details = "Hit Insecticide Spray Lemon 400Ml",
                    price = 7.50,
                    imageUrl = "https://demo8.1hour.in/media/products/18736.png"
                ),
                Product(
                    name = "HIT INSECTICIDE SPRAY GREEN TEA 400Ml",
                    details = "Hit Insecticide Spray Green Tea 400Ml",
                    price = 7.40,
                    imageUrl = "https://demo8.1hour.in/media/products/18731.png"
                ),
                Product(
                    name = "SPLASH AIR REFRESHENER LAVENDER 300Ml",
                    details = "Splash Air Refreshener Lavender 300Ml",
                    price = 7.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18723.png"
                ),
                Product(
                    name = "COMFORT FABRIC CONDITIONER PINK 2L",
                    details = "Comfort Fabric Conditioner Pink 2L",
                    price = 33.50,
                    imageUrl = "https://demo8.1hour.in/media/products/11695.png"
                ),
                Product(
                    name = "COMFORT FABRIC CONDITIONER BLUE 2L",
                    details = "Comfort Fabric Conditioner Blue 2L",
                    price = 33.50,
                    imageUrl = "https://demo8.1hour.in/media/products/11693.png"
                ),
                Product(
                    name = "GLADE AEROSOL LEMON AIRFRESHNER CAN 300Ml",
                    details = "Glade Aerosol Lemon Airfreshner Can 300Ml",
                    price = 42.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14375.png"
                ),
                Product(
                    name = "GLADE AEROSOL LAVENDAR AIRFRESHNER CAN 300Ml",
                    details = "Glade Aerosol Lavendar Airfreshner Can 300Ml",
                    price = 42.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14372.png"
                ),
                Product(
                    name = "DURU FRESH SEN OCEAN BREEZE 150G",
                    details = "Duru Fresh Sen Ocean Breeze 150G",
                    price = 3.75,
                    imageUrl = "https://demo8.1hour.in/media/products/13005.png"
                ),
                Product(
                    name = "AIRPRO SCENTED OIL TEA TREE REFILL",
                    details = "Airpro Scented Oil Tea Tree Refill",
                    price = 57.99,
                    imageUrl = "https://demo8.1hour.in/media/products/7246.png"
                ),
                Product(
                    name = "HARPIC SUPER ACTIVE BLOCK PINK BLOSSOM 38G",
                    details = "Harpic Super Active Block Pink Blossom 38G",
                    price = 10.49,
                    imageUrl = "https://demo8.1hour.in/media/products/8583.png"
                )
            )
        ),
        Category(
            name = "ALCOHOL",
            icon = "https://demo8.1hour.in/media/productsCategory/Alcohol_AaHDOnN.jpg",
            items = listOf(
                Product(
                    name = "BLUE JEANS ENERGY DRINK 250Ml",
                    details = "Blue Jeans Energy Drink 250Ml",
                    price = 12.89,
                    imageUrl = "https://demo8.1hour.in/media/products/13922.png"
                ),
                Product(
                    name = "ARGENTINIAN ANDEAN MALBEC WINE 750Ml",
                    details = "Argentinian Andean Malbec Wine 750Ml",
                    price = 128.99,
                    imageUrl = "https://demo8.1hour.in/media/products/13869.png"
                ),
                Product(
                    name = "ORIGINAL CHOICE DELUXE RHUM 750Ml",
                    details = "Original Choice Deluxe Rhum 750Ml",
                    price = 33.49,
                    imageUrl = "https://demo8.1hour.in/media/products/19836.png"
                ),
                Product(
                    name = "PUSHKIN VODKA 1L",
                    details = "Pushkin Vodka 1L",
                    price = 30.49,
                    imageUrl = "https://demo8.1hour.in/media/products/16366.png"
                ),
                Product(
                    name = "1800 BLANCO TEQUILA 700Ml",
                    details = "1800 Blanco Tequila 700Ml",
                    price = 799.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14804.png"
                ),
                Product(
                    name = "JOSE CUERVO BLUE AGAVE TEQUILA 1L",
                    details = "Jose Cuervo Blue Agave Tequila 1L",
                    price = 72.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14803.png"
                ),
                Product(
                    name = "ABSOLUT VODKA RASPBERRY 1L",
                    details = "Absolut Vodka Raspberry 1L",
                    price = 105.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14744.png"
                ),
                Product(
                    name = "AMARULA CREAM LIQUEUR 1L",
                    details = "Amarula Cream Liqueur 1L",
                    price = 94.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14584.png"
                ),
                Product(
                    name = "AMARULA 2 GLASS PACK WITH GIFT BOX 750Ml",
                    details = "Amarula 2 Glass Pack With Gift Box 750Ml",
                    price = 55.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14583.png"
                ),
                Product(
                    name = "SMIRNOFF ICE 300Ml D573",
                    details = "Smirnoff Ice 300Ml D573",
                    price = 8.99,
                    imageUrl = "https://demo8.1hour.in/media/products/14539.png"
                )
            )
        )
    )
}