package com.example.infs3605wasteapplicationt13a_04;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "pantryPalDB.db";
    private static final int DB_VERSION = 6;
    private static final String TAG = "DBHandler";
    private static DBHandler dbHandler;
    private SQLiteDatabase db;


    public DBHandler(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBHandler instanceOfDatabase(Context context){
        if(dbHandler == null) {
            dbHandler = new DBHandler(context);
        }
        return dbHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create ingredientItem table
        String ingredientItemQuery = "CREATE TABLE IF NOT EXISTS INGREDIENT_ITEMS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "ITEM_NAME TEXT NOT NULL," +
                "ENTRY_DATE TEXT NOT NULL," +
                "EXPIRY_DATE TEXT NOT NULL," +
                "ICON_ID INTEGER NOT NULL," +
                "QUANTITY TEXT NOT NULL)";
        db.execSQL(ingredientItemQuery);
        Log.d(TAG, "INGREDIENT_ITEMS table created");

        addPresetIngredientItems(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i !=i1){
            dropTable("INGREDIENT_ITEMS", db);
            onCreate(db);
        }
    }

    public void dropTable(String tableName, SQLiteDatabase db){
        SQLiteStatement stmt = db.compileStatement("DROP TABLE IF EXISTS " + tableName);
        stmt.execute();
    }

    private void addPresetIngredientItems(SQLiteDatabase db) {
        //set up fake dates
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String today = df.format(date);

        try{
            cal.setTime(df.parse(today));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //1 week before
        cal.add(Calendar.DAY_OF_MONTH, -7);
        String minus7Days = df.format(cal.getTime());

        //1.5 weeks before
        cal.add(Calendar.DAY_OF_MONTH, -4);
        String minus11Days = df.format(cal.getTime());

        //Banana expiry date
        cal.add(Calendar.DAY_OF_MONTH, 9);
        String bananaExpiry = df.format(cal.getTime());

        //chocolate expiry date
        cal.add(Calendar.DAY_OF_MONTH, 188);
        String chocolateExpiry = df.format(cal.getTime());

        addIngredientItem(db, "Lindt Dark Chocolate", minus11Days, chocolateExpiry, R.drawable.chocolate_icon, "250g");
        addIngredientItem(db, "Pink Lady Apples", minus11Days, today, R.drawable.apple_icon, "5");
        addIngredientItem(db, "Banana", minus7Days, bananaExpiry, R.drawable.banana_icon, "7");

    }

    public void addIngredientItem(SQLiteDatabase db, String itemName, String entryDate, String expiryDate, int iconId, String quantity){
        SQLiteStatement stmt = db.compileStatement("INSERT INTO INGREDIENT_ITEMS" +
                "(ITEM_NAME, ENTRY_DATE, EXPIRY_DATE, ICON_ID, QUANTITY) VALUES (?, ?, ?, ?, ?)");
        stmt.bindString(1, itemName);
        stmt.bindString(2, entryDate);
        stmt.bindString(3, expiryDate);
        stmt.bindLong(4, iconId);
        stmt.bindString(5, quantity);

        stmt.executeInsert();
        Log.d(TAG, itemName + " row added");
    }

    public ArrayList<IngredientItem> getItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<IngredientItem> results = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM INGREDIENT_ITEMS", null);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            results.add(new IngredientItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5)
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    public ArrayList<IngredientItem> getItemsByDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<IngredientItem> results = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM INGREDIENT_ITEMS WHERE ENTRY_DATE = ?", new String[] {date});

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            results.add(new IngredientItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5)
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    public IngredientItem getItemByName(String itemName){
        IngredientItem item = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM INGREDIENT_ITEMS WHERE ITEM_NAME = ?", new String[] {itemName});

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            item = new IngredientItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5)
            );
            cursor.moveToNext();
        }
        return item;
    }

    public void deleteItem(IngredientItem item){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {Integer.toString(item.getId())};
        db.delete("INGREDIENT_ITEMS", whereClause, whereArgs);

    }

}
