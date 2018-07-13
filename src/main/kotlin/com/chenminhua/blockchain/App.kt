package com.chenminhua.blockchain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.web.bind.annotation.*

@SpringBootApplication
open class App

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}

@RestController
@RequestMapping("/")
class BaseController {

    private var blockChain = BlockChain()

    @GetMapping("/mine")
    fun mine(): Block {
        return blockChain.mine()
    }

    @PostMapping("/transactions/new")
    fun newTransaction(@RequestBody transaction: Transaction): String {
        val index = blockChain.newTransaction(transaction)
        return "message: transaction will be added to block $index"
    }

    @GetMapping("/chain")
    fun getChain() = blockChain.getChain()
}



