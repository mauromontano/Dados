package com.example.satiro.dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class DBAdapter {

    /**
     *  Variables locales
     */

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private ArrayList<MiEntidad> lista = new ArrayList<MiEntidad>();
    private final Context ct;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BDResultados";
    private static final String KEY_ROWID = "_id";
    private static final String KEY_NAME = "nombre";
    private static final String KEY_POINTS = "puntaje";
    private static final String DATABASE_TABLE= "Resultados";
    static String tabla= "CREATE TABLE if not exists Resultados(_id integer primary key autoincrement, nombre text, puntaje integer)";

    static String[] arrIds ;

    /**
     *  Constructor
     */

    public DBAdapter(Context c) {
        ct = c;
        DBHelper = new DatabaseHelper(ct);
    }

    /**
     *  Abre la base de datos
     */

    public DBAdapter open() throws SQLException{
        db= DBHelper.getWritableDatabase();
        return this;
    }

    /**
     *  Cierra la base de datos
     */

    public void close(){
        DBHelper.close();
    }

    /**
     * Inserta una entrada en la base de datos
     */

    public boolean insertar(String n , Integer p ){
        ContentValues content = new ContentValues();
        content.put(KEY_NAME,n);
        content.put(KEY_POINTS,p);
        return (db.insert(DATABASE_TABLE,null,content)) > 0;
    }

    /**
     *  Elimina una entrada por su id de la base de datos
     */

    public boolean eliminar(long rowId){
        return true;
    }

    /**
     *  Devuelve todas las entradas de la base de datos
     */

    public ArrayList<MiEntidad> verTodos(){
        this.open();
        Cursor c = ordenarMejorAPeor();
        arrIds= new String[c.getCount()];
        int i= 0;
        if (c.moveToFirst()) {
            do {
                MiEntidad e = new MiEntidad(c.getString(0),c.getInt(1));
                lista.add(e);
                arrIds[i]=e.getNombre();
                i++;
            } while (c.moveToNext());
        }
        this.close();
        return lista;
    }


    public void eliminarTodos(){
        ct.deleteDatabase(DATABASE_NAME);
    }

    public Cursor ordenarMejorAPeor(){
        String[] puntajes= new String[]{KEY_NAME,KEY_POINTS};
        Cursor c= db.query(DATABASE_TABLE,puntajes,null,null,null,null,KEY_POINTS + " ASC");
        return c;
    }



    public Cursor getEntrada(long rowId) throws SQLException{
        Cursor mCursor=
                db.query(true,DATABASE_TABLE,new String[]{KEY_ROWID,KEY_NAME,KEY_POINTS},KEY_ROWID + "=" + rowId,null,null,null,null,null);
        if(mCursor != null)
            mCursor.moveToFirst();
        return mCursor;
    }

    public void updateEntrada(int pos,String name, int puntos,String nameAnterior) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_POINTS, puntos);
        db.update(DATABASE_TABLE,args,KEY_NAME+ "=?",new String[]{nameAnterior});
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(tabla);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + DATABASE_TABLE);
            onCreate(db);
        }
    }

}