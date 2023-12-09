package br.ufrn.petclinic.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.petclinic.models.Pet;

public class PetRepository extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PetsDB";
    private static final String TABLE_PETS = "pets";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_APPOINTMENT_DATE = "appointment_date";
    private static final String KEY_PICTURE_PATH = "picture_path";

    public PetRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PETS_TABLE = "CREATE TABLE " + TABLE_PETS + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_APPOINTMENT_DATE + " TEXT,"
                + KEY_PICTURE_PATH + " TEXT" + ")";
        db.execSQL(CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        onCreate(db);
    }

    public void addPet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, pet.getId());
        values.put(KEY_NAME, pet.getName());
        values.put(KEY_TYPE, pet.getType());
        values.put(KEY_APPOINTMENT_DATE, pet.getAppointmentDate());
        values.put(KEY_PICTURE_PATH, pet.getPicturePath());
        db.insert(TABLE_PETS, null, values);
        db.close();
    }

    public Pet getPet(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PETS, new String[] { KEY_ID,
                        KEY_NAME, KEY_TYPE, KEY_APPOINTMENT_DATE, KEY_PICTURE_PATH },
                KEY_ID + "=?", new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Pet pet = new Pet(cursor.getString(0),
                cursor.getString(1), cursor.getString(3),
                cursor.getString(4));
        pet.setType(cursor.getString(2));

        cursor.close();
        return pet;
    }

    public List<Pet> getAllPets() {
        List<Pet> petList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PETS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet(cursor.getString(0),
                        cursor.getString(1), cursor.getString(3),
                        cursor.getString(4));
                pet.setType(cursor.getString(2));
                petList.add(pet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return petList;
    }

    public int updatePet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, pet.getName());
        values.put(KEY_TYPE, pet.getType());
        values.put(KEY_APPOINTMENT_DATE, pet.getAppointmentDate());
        values.put(KEY_PICTURE_PATH, pet.getPicturePath());

        return db.update(TABLE_PETS, values, KEY_ID + " = ?",
                new String[] { pet.getId() });
    }

    public void deletePet(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PETS, KEY_ID + " = ?",
                new String[] { id });
        db.close();
    }
}