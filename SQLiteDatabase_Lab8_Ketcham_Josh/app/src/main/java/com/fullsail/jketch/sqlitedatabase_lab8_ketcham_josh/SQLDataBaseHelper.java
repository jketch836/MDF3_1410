package com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLDataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_FILENAME = "employee.db";
    private static final int DATABASE_VERSION = 1;

    public static final String EMPLOYEE_ID = "_id";
    public static final String EMPLOYEE_FIRST_NAME = "FirstName";
    public static final String EMPLOYEE_LAST_NAME = "LastName";
    public static final String EMPLOYEE_HIRE_DATE = "HireDate";
    public static final String EMPLOYEE_HOURLY_PAY = "HourlyPay";
    public static final String EMPLOYEE_IMAGE = "Image";
    public static final String ROW_ID = "ROW_ID";

    public static final String EMPLOYEE_TABLE = "Employee";

    public static final String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + EMPLOYEE_TABLE + " ( " +
            EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            EMPLOYEE_FIRST_NAME + " TEXT , " +
            EMPLOYEE_LAST_NAME + " TEXT , " +
            EMPLOYEE_HIRE_DATE + " DATETIME , " +
            EMPLOYEE_HOURLY_PAY + " REAL , " +
            EMPLOYEE_IMAGE + " TEXT );";


    public SQLDataBaseHelper (Context c) {
        super (c,DATABASE_FILENAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_EMPLOYEE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addEmployee(String firstName, String lastName, String hDate, float hPay, String eImage) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues employeeValues = new ContentValues();

        employeeValues.put(EMPLOYEE_FIRST_NAME, firstName);
        employeeValues.put(EMPLOYEE_LAST_NAME, lastName);
        employeeValues.put(EMPLOYEE_HIRE_DATE, hDate);
        employeeValues.put(EMPLOYEE_HOURLY_PAY, hPay);
        employeeValues.put(EMPLOYEE_IMAGE, eImage);

        db.insert(EMPLOYEE_TABLE, null, employeeValues);

    }

    public void removeEmployee(long row) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete(EMPLOYEE_TABLE, ROW_ID + "=" + row, null);

    }


    public Cursor getAllRecords (){

        SQLiteDatabase db = getReadableDatabase();

        return db.query(EMPLOYEE_TABLE, null, null, null, null, null, null);

    }

}
