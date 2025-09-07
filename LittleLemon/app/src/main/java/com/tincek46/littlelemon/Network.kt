package com.tincek46.littlelemon


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String, // Keep as String as per JSON, can be converted later
    val image: String,
    val category: String
)

