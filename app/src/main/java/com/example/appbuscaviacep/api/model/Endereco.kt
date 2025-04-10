package com.example.appbuscaviacep.api.model

import com.google.gson.annotations.SerializedName

class Endereco {
    data class Endereco(
        val bairro: String,
        val estado: String,
        @SerializedName("localidade")
        val cidade: String,
        val logradouro: String
    )
}