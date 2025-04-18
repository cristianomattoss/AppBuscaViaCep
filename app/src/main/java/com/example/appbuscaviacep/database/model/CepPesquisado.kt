package com.example.appbuscaviacep.database.model

data class CepPesquisado(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val estado: String
)