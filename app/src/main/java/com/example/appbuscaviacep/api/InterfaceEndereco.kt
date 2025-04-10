package com.example.appbuscaviacep.api

import com.example.appbuscaviacep.api.model.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceEndereco {

    @GET("{cep}/json/")
    suspend fun buscarEndereco(
        @Path("cep") cep: String
    ): Response<Endereco>

}