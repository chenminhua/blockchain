package com.chenminhua.blockchain

data class Transaction (
        val sender: String,
        val recipient: String,
        val amount: Long
)