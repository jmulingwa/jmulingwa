package com.example.eShuttle.responses

data class BookingResponse(
    val amount: String,
    val invoiceNo: String,
    val message: String,
    val status: String
)
