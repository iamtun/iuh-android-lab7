package fit.android.lab7.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fit.android.lab7.model.Tour;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tour_manager";
    private static final String TABLE_NAME = "tours";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TOURS_TABLE = "create table " + TABLE_NAME + "(" +  KEY_ID + " integer primary key, "
                                                                        + KEY_NAME + " text " + ")";
        db.execSQL(CREATE_TOURS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);

        onCreate(db);
    }

    //=================================== STUDENT CODE =============================================
    public List<Tour> getAllTours() {
        List<Tour> tourList = new ArrayList<>();

        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do  {
                Tour tour = new Tour();
                tour.setId(Integer.parseInt(cursor.getString(0)));
                tour.setNameTour(cursor.getString(1));

                tourList.add(tour);
            }while (cursor.moveToNext());
        }

        return tourList;
    }

    public void addTour(Tour tour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, tour.getId());
        values.put(KEY_NAME, tour.getNameTour());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Tour getTour(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME}, KEY_ID +  "=?",
                        new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Tour tour = new Tour(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return tour;
    }

    public int updateTour(Tour tour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, tour.getNameTour());

        int res = db.update(TABLE_NAME, values, KEY_ID + "= ?", new String[]{String.valueOf(tour.getId())});
        return res;
    }

    public void deleteTour(Tour tour) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "= ?", new String[]{String.valueOf(tour.getId())});
        db.close();
    }

    public int getToursCount() {
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}
