package com.tech.sachintyagi.simdetection.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SachinTyagi on 9/20/17.
 */

public class SIMDBManger extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SIMDBManger";

    // Contacts table name
    private static final String TABLE_CONTACTS = "SIMInfo";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SIM_ID = "sim_id";
    private static final String KEY_ICC = "icc";

    public SIMDBManger(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SIM_ID + " TEXT,"
                + KEY_ID + " INTEGER ," + KEY_SIM_ID + " TEXT,"
                + KEY_ICC + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addContact(SIMinfo contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, contact.get_id());
        values.put(KEY_SIM_ID, contact.get_sim_no()); // Contact Name
        values.put(KEY_ICC, contact.get_icc()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    // Getting single contact
    public SIMinfo getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_SIM_ID, KEY_ICC }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SIMinfo contact = new SIMinfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<SIMinfo> getAllContacts(String value , String filterName) {


        String filter = "";
        if (filterName.contains("sub"))
            filter = KEY_ICC;
        else if (filterName.contains("sim_id"))  // for samsung
            filter = KEY_SIM_ID;
        else
            filter = KEY_ID;

        List<SIMinfo> contactList = new ArrayList<SIMinfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " where "+ filter + " = " + value ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SIMinfo contact = new SIMinfo();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_sim_no(cursor.getString(1));
                contact.set_icc(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    ////////

    public List<SIMinfo> getAllContacts() {
        List<SIMinfo> contactList = new ArrayList<SIMinfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SIMinfo contact = new SIMinfo();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_sim_no(cursor.getString(1));
                contact.set_icc(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    ///////


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    // Updating single contact
    public int updateContact(SIMinfo contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SIM_ID, contact.get_sim_no());
        values.put(KEY_ICC, contact.get_icc());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
    }


    // Deleting single contact
    public void deleteContact(SIMinfo contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
        db.close();
    }

    // Deleting single contact
    public void deleteContactAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,null,null);
        db.close();
    }

}