package com.chenminhua.blockchain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.security.MessageDigest
import java.util.*

data class Block(
        val index: Long = 0,
        val timestamp: Date? = null,
        val transactions: List<Transaction>,
        val proof: Long,
        val previous: String
) {
    fun digest(): String {
        val mapper = jacksonObjectMapper()
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(mapper.writeValueAsString(this).toByteArray()).toString()
    }
}