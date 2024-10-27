package com.example.design.creational

class Order(
    val productName: String,
    val quantity: Int,
    val price: Double,
    val address: String?,
    val discountCode: String?,
    val giftMessage: String?
)

fun main() {
    val order = Order("Laptop", 1, 1500.0, "123 Main St", null, "Happy Birthday!")
    println("Order: ${order.productName}, Quantity: ${order.quantity}, Price: ${order.price}")
}
