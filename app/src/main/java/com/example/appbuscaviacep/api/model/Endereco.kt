package com.example.appbuscaviacep.api.model

import com.google.gson.annotations.SerializedName

data class Endereco(
        val cep: String,
        val bairro: String,
        val estado: String,
        @SerializedName("localidade")
        val cidade: String,
        val logradouro: String
)