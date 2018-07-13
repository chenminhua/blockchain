package com.chenminhua.blockchain

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

@Component
class BlockChain {
    private val chain: MutableList<Block> = mutableListOf(newBlock(1, "100"))
    private var currentTransactions: MutableList<Transaction> = ArrayList()
    private var nodeIdentifier = UUID.randomUUID().toString()

    private val md = MessageDigest.getInstance("SHA-256")

    fun getChain() = this.chain

    fun mine(): Block {
        val lastBlock = chain.last()
        val proof = proofOfWork(lastBlock.proof.toString())
        newTransaction("0", nodeIdentifier, 1)
        val previousHash = chain.last().digest()
        return newBlock(proof, previousHash)
    }

    fun newTransaction(transaction: Transaction): Long {
        currentTransactions.add(transaction)
        return chain.last().index + 1
    }

    // 增加一个新的区块
    private fun newBlock(proof: Long, previous_hash: String? = null): Block {
        val block = Block(
                (chain.size + 1).toLong(),
                Date(),
                currentTransactions,
                proof,
                previous_hash ?: (chain[chain.size-1]).digest()
        )
        currentTransactions = ArrayList()
        chain.add(block)
        return block
    }

    // 加入一个新的交易，返回将包含这个交易的区块
    private fun newTransaction(sender: String, recipient: String, amount: Long): Long {
        val newTransaction = Transaction(sender = sender, recipient = recipient, amount = amount)
        currentTransactions.add(newTransaction)
        return chain.last().index + 1
    }

    private fun proofOfWork(lastProof: String): Long {
        var proof: Long = 0
        while (!validProof(lastProof, proof)) {
            proof += 1
        }
        return proof
    }

    private fun validProof(lastProof: String, proof: Long): Boolean {
        val guess = "$lastProof$proof"
        return md.digest(guess.toByteArray()).toString().endsWith("0000")
    }
}

