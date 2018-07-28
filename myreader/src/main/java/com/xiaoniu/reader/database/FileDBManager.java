package com.xiaoniu.reader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *@author xiaoniu
 * 操作数据库
 */
public class FileDBManager {

    private ReaderSqlHelper helper;

    public FileDBManager(Context context) {
        helper = new ReaderSqlHelper(context);
    }

    /**
     * 插入数据
     * @param values
     * @return
     */
    public boolean insert(ContentValues values){
        long new_id = -1;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            new_id = database.insert(ReaderSqlHelper.TXT_FILE_TABLE, null, values);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }
        return new_id == -1 ? false:true;
    }

    /**
     * 删除数据
     * @param whereClause name=?
     * @param whereArgs new String[]{zhangsan}
     * @return
     */
    public boolean delete(String whereClause, String[] whereArgs){
        /**影响数据库的行数*/
        int affected_rows = 0;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            affected_rows = database.delete(ReaderSqlHelper.TXT_FILE_TABLE, whereClause + "=?", whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }

        return affected_rows > 0?true:false;
    }

    /**
     * 修改
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean update(ContentValues values, String whereClause, String[] whereArgs){

        int affected_rows = 0;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            affected_rows = database.update(ReaderSqlHelper.TXT_FILE_TABLE, values, whereClause, whereArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }
        return affected_rows > 0?true:false;
    }

    /**
     * 查找数据
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    public Cursor query(String[] columns, String selection,
                         String[] selectionArgs, String groupBy, String having,
                         String orderBy, String limit){

        SQLiteDatabase database = null;
        try{
            database = helper.getReadableDatabase();
            return database.query(ReaderSqlHelper.TXT_FILE_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查找数据
     * @param columns
     * @return
     */
    public Cursor query(String[] columns){
        return query(columns, null, null, null, null, null, null);
    }


}
