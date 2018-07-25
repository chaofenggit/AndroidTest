package com.xiaoniu.reader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author xiaoniu
 */
public class ReaderSqlHelper extends SQLiteOpenHelper {
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String PATH = "path";

    private static final String DBNAME = "xiaoniu_reader.db";
    private static final int VERSION = 1;

    public static final String TXT_FILE_TABLE = "txtfile";

    public ReaderSqlHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String accountSQL = "create table "+TXT_FILE_TABLE
                +"("
                +ID   + " integer primary key autoincrement,"
                +NAME + " varchar(128),"
                +PATH + " varchar(255)"
                +")";
        db.execSQL(accountSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
