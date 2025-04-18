package com.example.appbuscaviacep.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context): SQLiteOpenHelper(
    context, "dadosCep.db", null, 2
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

        val trigger ="CREATE TRIGGER limitar_historico " +
                    "AFTER INSERT ON $TABELAS_DADOS_CEP " +
                    "WHEN (SELECT COUNT(*) FROM $TABELAS_DADOS_CEP) >= 10 " +
                    "BEGIN " +
                    "DELETE FROM $TABELAS_DADOS_CEP " +
                    "WHERE rowid = (SELECT MIN(rowid) FROM $TABELAS_DADOS_CEP);" +
                    "END;"

        db?.execSQL(trigger)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TRIGGER IF EXISTS limitar_historico")
        db?.execSQL("DROP TABLE IF EXISTS $TABELAS_DADOS_CEP")
        onCreate(db)
    }
}