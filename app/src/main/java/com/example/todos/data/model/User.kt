package com.example.todos.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a User from JSONPlaceholder API
 * Uses @SerializedName for proper JSON parsing with Gson
 */
data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("address")
    val address: Address,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("website")
    val website: String,

    @SerializedName("company")
    val company: Company
)

/**
 * Nested data class for user's address
 */
data class Address(
    @SerializedName("street")
    val street: String,

    @SerializedName("suite")
    val suite: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("zipcode")
    val zipcode: String,

    @SerializedName("geo")
    val geo: Geo
)

/**
 * Nested data class for geographical coordinates
 */
data class Geo(
    @SerializedName("lat")
    val lat: String,

    @SerializedName("lng")
    val lng: String
)

/**
 * Nested data class for user's company
 */
data class Company(
    @SerializedName("name")
    val name: String,

    @SerializedName("catchPhrase")
    val catchPhrase:  String,

    @SerializedName("bs")
    val bs: String
)