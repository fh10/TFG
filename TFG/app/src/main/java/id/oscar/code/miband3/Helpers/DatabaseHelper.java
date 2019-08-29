package id.oscar.code.miband3.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="database.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,4 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE usuarios (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT, nombre TEXT, apellidos TEXT, fecha_nac TEXT, sexo INTEGER, nivel INTEGER," +
                " peso REAL, altura REAL, pasos INTEGER, calorias INTEGER, recorrido INTEGER, zancada REAL)");
        sqLiteDatabase.execSQL("CREATE TABLE historico (ID INTEGER PRIMARY  KEY AUTOINCREMENT, userId INTEGER, fecha TEXT, pasos INTEGER, calorias INTEGER, recorrido INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS historico");
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password, String nombre, String apellidos, String fecha, int sexo, float peso,float altura)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int nivel = 0,calorias = 0,recorrido = 0,pasos = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        contentValues.put("nombre",nombre);
        contentValues.put("apellidos",apellidos);
        contentValues.put("fecha_nac",fecha);
        contentValues.put("peso",peso);
        contentValues.put("altura",altura);
        contentValues.put("sexo",sexo);
        contentValues.put("nivel",nivel);

        if(sexo == 0)
        {
            contentValues.put("zancada",altura*0.413);
        }
        else
        {
            contentValues.put("zancada",altura*0.415);
        }

        contentValues.put("pasos",pasos);
        contentValues.put("recorrido",recorrido);
        contentValues.put("calorias",calorias);
        long res = db.insert("usuarios",null,contentValues);
        db.close();
        return  res;
    }

    public long addEjercicio(Integer user, String fecha, int pasos, int recorrido,int calorias)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId",user);
        contentValues.put("fecha",fecha);
        contentValues.put("pasos",pasos);
        contentValues.put("recorrido",recorrido);
        contentValues.put("calorias",calorias);
        long res = db.insert("historico",null,contentValues);
        db.close();
        return  res;
    }

    public boolean checkUser(String username, String password)
    {
        String[] columns = {"ID"};
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username=?" + " and " + "password=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("usuarios",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public Usuario getUser(String username)
    {
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username=?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query("usuarios",null,selection,selectionArgs,null,null,null);
        Usuario user = new Usuario();
        cursor.moveToFirst();
        //user.setId(cursor.getInt(0));
        user.setUsername(cursor.getString(1));
        user.setNombre(cursor.getString(3));
        user.setApellidos(cursor.getString(4));
        user.setFecha_nac(cursor.getString(5));
        user.setSexo(cursor.getInt(6));
        user.setNivel(cursor.getInt(7));
        user.setPeso(cursor.getFloat(8));
        user.setAltura(cursor.getFloat(9));
        user.setPasos(cursor.getInt(10));
        user.setCalorias(cursor.getInt(11));
        user.setRecorrido(cursor.getInt(12));
        user.setZancada(cursor.getInt(13));
        cursor.close();
        db.close();
        return user;
    }

    public Cursor historicoEjercicios(String user)
    {
        SQLiteDatabase db = getReadableDatabase();
        String selection = "userId =?";
        String[] selectionArgs = { user };
        Cursor cursor = db.query("historico",null,selection,selectionArgs,null,null,"ID DESC");
        int count = cursor.getCount();
        db.close();
        return cursor;
    }
}