package com.ismael.manageproductsprovider.db;



import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;


import com.ismael.manageproductsprovider.ManageProductApplication;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que guarda el esquema de la
 */

public final class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 21;
    public static final String DATABASE_NAME="ManageProduct.db";
    private static DatabaseHelper databaseHelper;
    private AtomicInteger mOpenCounter;
    private SQLiteDatabase mDatabase;

    private DatabaseHelper() {
        super(ManageProductApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION); //Es null porque no vamos a usar cursores
        mOpenCounter = new AtomicInteger();
    }

    public static synchronized DatabaseHelper getInstance(){
        if(databaseHelper == null)
            databaseHelper = new DatabaseHelper(); //Se usa el de la aplicación por si la activity fuese null

        return databaseHelper;
    }

    public synchronized SQLiteDatabase openDatabase(){
        if(mOpenCounter.incrementAndGet() == 1)
            mDatabase = getWritableDatabase();

        return mDatabase;
    }

    public synchronized void closeDatabase(){
        if(mOpenCounter.decrementAndGet() == 0)
            mDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.execSQL(DatabaseContract.CategoryEntry.SQL_CREATE_ENTRIE);
            sqLiteDatabase.execSQL(DatabaseContract.PharmacyEntry.SQL_CREATE_ENTRIE);
            sqLiteDatabase.execSQL(DatabaseContract.InvoiceStatusEntry.SQL_CREATE_ENTRIE);
            sqLiteDatabase.execSQL(DatabaseContract.ProductEntry.SQL_CREATE_ENTRIE);
            sqLiteDatabase.execSQL(DatabaseContract.InvoiceEntry.SQL_CREATE_ENTRIE);
            sqLiteDatabase.execSQL(DatabaseContract.InvoiceLineEntry.SQL_CREATE_ENTRIE);
            //insertData(sqLiteDatabase);
            sqLiteDatabase.setTransactionSuccessful();
        } catch(SQLiteException ex){
            Log.wtf("DATABASE ERROR", ex.getLocalizedMessage());
        } finally {
            sqLiteDatabase.endTransaction();
            insertData(sqLiteDatabase);
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.execSQL(DatabaseContract.InvoiceLineEntry.SQL_DELETE_ENTRIES);
            sqLiteDatabase.execSQL(DatabaseContract.InvoiceEntry.SQL_DELETE_ENTRIES);
            sqLiteDatabase.execSQL(DatabaseContract.ProductEntry.SQL_DELETE_ENTRIES);
            sqLiteDatabase.execSQL(DatabaseContract.InvoiceStatusEntry.SQL_DELETE_ENTRIES);
            sqLiteDatabase.execSQL(DatabaseContract.PharmacyEntry.SQL_DELETE_ENTRIES);
            sqLiteDatabase.execSQL(DatabaseContract.CategoryEntry.SQL_DELETE_ENTRIES);
            this.onCreate(sqLiteDatabase);
            sqLiteDatabase.setTransactionSuccessful();
        }

        finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly())
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                db.setForeignKeyConstraintsEnabled(true);
            else
                db.execSQL("PRAGMA foreign_keys = ON");
    }

    public SQLiteDatabase getDatabase(){
        return getWritableDatabase();
    }

    private void insertData(SQLiteDatabase db) {
        db.execSQL("insert into category values (1, 'Pastillas')");
        db.execSQL("insert into category values (2, 'Jarabe')");
        db.execSQL("insert into category values (3, 'Sobres')");
        db.execSQL("insert into category values (4, 'Pomada')");

        db.execSQL("insert into pharmacy values (1, 'A84752351', 'C/Competa, 12', '952707070')");
        db.execSQL("insert into pharmacy values (2, 'A56825132', 'C/Ayala, 23', '952707071')");
        db.execSQL("insert into pharmacy values (3, 'A89562158', 'C/La Unión, 4', '9527708514')");
        db.execSQL("insert into pharmacy values (4, 'B58963214', 'C/Victoria, 36', '952709632')");


        db.execSQL("insert into product values(1, 'Ibuprofeno', 'Ibuprofeno 500', 'Bayern', '1', 3.99, 160, 'https://www.laproff.com/img/product/Ibuprofeno.jpg', 1)");
        db.execSQL("insert into invoicestatus values (1, 'Activo');");
        db.execSQL("insert into invoicestatus values (0, 'Finalizado');");

        db.execSQL("insert into invoice values (1, 'PedidoIbuprofeno', 3, '27/02/2017', 1)");
        db.execSQL("insert into invoice values (2, 'PedidoParacetamol', 1, '22/02/2017', 1)");
        db.execSQL("insert into invoice values (3, 'PedidoMultiple', 4, '03/03/2017', 1)");
        db.execSQL("insert into invoiceline values (1, 1, 1, 'Ibuprofeno', 202, 805.98)");

    }
}
