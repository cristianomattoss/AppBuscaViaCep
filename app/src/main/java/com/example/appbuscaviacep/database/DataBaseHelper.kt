package com.example.appbuscaviacep.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context): SQLiteOpenHelper(
    context, "dadosCep.db", null, 1
) {

    companion object {
        const val TABELAS_DADOS_CEP = "dados_cep"
        const val CEP = "cep"
        const val LOGRADOURO = "logradouro"
        const val BAIRRO = "bairro"
        const val CIDADE = "cidade"
        const val ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $TABELAS_DADOS_CEP (" +
                "$CEP TEXT," +
                "$LOGRADOURO TEXT," +
                "$BAIRRO TEXT," +
                "$CIDADE TEXT," +
                "$ESTADO TEXT" +
                ");"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}