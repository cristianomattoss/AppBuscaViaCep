package com.example.appbuscaviacep.database

import android.content.ContentValues
import android.content.Context
import com.example.appbuscaviacep.database.model.CepPesquisado

class DadosCepDAO(context: Context) {

    private val escrita = DataBaseHelper(context).writableDatabase
    private val leitura = DataBaseHelper(context).readableDatabase

    fun salvar(cepPesquisado: CepPesquisado){

        val dados = ContentValues()
        dados.put(DataBaseHelper.CEP, cepPesquisado.cep)
        dados.put(DataBaseHelper.LOGRADOURO, cepPesquisado.logradouro)
        dados.put(DataBaseHelper.BAIRRO, cepPesquisado.bairro)
        dados.put(DataBaseHelper.CIDADE, cepPesquisado.cidade)
        dados.put(DataBaseHelper.ESTADO, cepPesquisado.estado)

        escrita.insert(
            DataBaseHelper.TABELAS_DADOS_CEP,
            null,
            dados
        )
    }

    fun listar(): MutableList<CepPesquisado> {

        val listaCepsPesquisados = mutableListOf<CepPesquisado>()

        val sql = "SELECT * FROM ${DataBaseHelper.TABELAS_DADOS_CEP}"
        val cursor = leitura.rawQuery(sql, null)

        val indiceCep = cursor.getColumnIndex(DataBaseHelper.CEP)
        val indiceLogradouro = cursor.getColumnIndex(DataBaseHelper.LOGRADOURO)
        val indiceBairro = cursor.getColumnIndex(DataBaseHelper.BAIRRO)
        val indiceCidade = cursor.getColumnIndex(DataBaseHelper.CIDADE)
        val indiceEstado = cursor.getColumnIndex(DataBaseHelper.ESTADO)

        while (cursor.moveToNext()) {

            val cep = cursor.getString(indiceCep)
            val logradouro = cursor.getString(indiceLogradouro)
            val bairro = cursor.getString(indiceBairro)
            val cidade = cursor.getString(indiceCidade)
            val estado = cursor.getString(indiceEstado)

            listaCepsPesquisados.add(CepPesquisado(cep, logradouro, bairro, cidade, estado))

        }

        return listaCepsPesquisados
    }

    fun limparTabela() {
        escrita.delete(DataBaseHelper.TABELAS_DADOS_CEP, null, null)
    }
}