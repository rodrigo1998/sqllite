package com.rodrigo_oliveira.sqllite.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rodrigo Oliveira on 13/04/18.
 * ra 816114365
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper (Context context){
        super (context, "previsao.db", null, 1);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        /*private String descricao, diaDaSemana, nomeCidade;
        private double min, max;
        private int humidade;*/
        String createTable =

                "CREATE TABLE previsao (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR(200), dia_semana VARCHAR(200), nome_cidade VARCHAR(200), min REAL, max REAL, humidade INTEGER, icone VARCHAR(10));";
        db.execSQL(createTable);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
