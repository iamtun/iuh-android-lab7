package fit.android.lab7.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fit.android.lab7.model.Customer;
import fit.android.lab7.model.Tour;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tour_manager";
    private static final String TABLE_NAME = "tours";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String TABLE_CUSTOMER = "customers";
    private static final String KEY_ID_CUSTOMER = "id";
    private static final String KEY_NAME_CUSTOMER = "name_customer";
    private static final String KEY_ID_TOUR_CUSTOMER = "id_tour";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TOURS_TABLE = "create table " + TABLE_NAME + "(" +  KEY_ID + " integer primary key, "
                                                                        + KEY_NAME + " text " + ")";
        String CREATE_CUSTOMER_TOUR = "create table " + TABLE_CUSTOMER + "( "  + KEY_ID_CUSTOMER + " integer primary key, "
                                                    + KEY_NAME_CUSTOMER + " text, " + KEY_ID_TOUR_CUSTOMER + " integer not null)";
        db.execSQL(CREATE_TOURS_TABLE);
        db.execSQL(CREATE_CUSTOMER_TOUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL("drop table if exists customers");
        onCreate(db);
    }

    //=================================== STUDENT CODE =============================================
    //=================================== TOURS ====================================================
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

    public int getIdLastTour() {
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor == null) {
            return 0;
        }else {
            cursor.moveToLast();
            return Integer.parseInt(cursor.getString(0));
        }
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

    //=================================== CUSTOMER =================================================
    public List<Customer> getAllCustomerInTour(int id_tour) {
        List<Customer> listCustomer = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUSTOMER, new String[]{KEY_ID_CUSTOMER, KEY_NAME_CUSTOMER, KEY_ID_TOUR_CUSTOMER},
                        KEY_ID_TOUR_CUSTOMER + "=?", new String[]{String.valueOf(id_tour)},
                    null, null, null, null);
        if(cursor.moveToFirst()) {
            do{
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(cursor.getString(0)));
                customer.setName(cursor.getString(1));
                customer.setId_tour(Integer.parseInt(cursor.getString(2)));

                listCustomer.add(customer);
            }while (cursor.moveToNext());
        }

        return listCustomer;
    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CUSTOMER, customer.getId());
        values.put(KEY_NAME_CUSTOMER, customer.getName());
        values.put(KEY_ID_TOUR_CUSTOMER, customer.getId_tour());

        db.insert(TABLE_CUSTOMER, null, values);
        db.close();
    }

    public int getCustomerCountInTour(int id_tour) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUSTOMER, new String[]{KEY_ID_CUSTOMER, KEY_NAME_CUSTOMER, KEY_ID_TOUR_CUSTOMER},
                KEY_ID_TOUR_CUSTOMER + "=?", new String[]{String.valueOf(id_tour)},
                null, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getCustomerCount() {
        String sql = "select * from " + TABLE_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    public int getIdLastCustomer() {
        String sql = "select * from " + TABLE_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor == null) {
            return 0;
        }else {
            cursor.moveToLast();
            return Integer.parseInt(cursor.getString(0));
        }
    }

    public void deleteCustomer(int id_customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMER, KEY_ID + "= ?", new String[]{String.valueOf(id_customer)});
        db.close();
    }
}
