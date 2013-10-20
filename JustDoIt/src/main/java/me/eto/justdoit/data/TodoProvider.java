package me.eto.justdoit.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import me.eto.justdoit.Contract;

/**
 * Created by eto on 10/5/13.
 */
public class TodoProvider extends ContentProvider {

    private TodoOpenHelper mDb;

    @Override
    public boolean onCreate() {
        mDb = new TodoOpenHelper(getContext());
        return true; // se riesco a creare il content provider
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
        int match = Constants.MATCHER.match(uri);
        switch (match) {
            case Constants.Match.ALL_TODOS:
                sqb.setTables(Contract.Todos.PATH);
                break;
            case Constants.Match.ONE_TODO:
                sqb.setTables(Contract.Todos.PATH);
                sqb.appendWhere(Contract.Todos.Fields.ID + " = " + ContentUris.parseId(uri));
                break;
        }
        SQLiteDatabase db = mDb.getReadableDatabase();
        final Cursor cursor = sqb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = Constants.MATCHER.match(uri);
        String table;
        switch (match) {
            case Constants.Match.ALL_TODOS:
                // puoi fare insert
                table = Contract.Todos.PATH;
                break;
            case Constants.Match.ONE_TODO:
            default:
                throw new UnsupportedOperationException("Can't insert at specific position");
        }
        SQLiteDatabase db = mDb.getWritableDatabase();
        long id = db.insert(table, null, values);
        if (id != -1) {
            Uri newUri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(newUri,
                    null,true /*sync*/);
            return newUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = Constants.MATCHER.match(uri);
        String table;
        switch (match) {
            case Constants.Match.ALL_TODOS:
                table = Contract.Todos.PATH;
                if (selection == null) {
                    throw new UnsupportedOperationException("Can't delete all todos");
                }
                break;
            case Constants.Match.ONE_TODO:
                table = Contract.Todos.PATH;
                selection = composeWhere(ContentUris.parseId(uri), selection);
                break;
            default:
                throw new UnsupportedOperationException("Can't insert at specific position");
        }
        SQLiteDatabase db = mDb.getWritableDatabase();
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static String composeWhere(long id, String select) {
        String myWhere = "_id = " + id;
        if (TextUtils.isEmpty(select)) {
            return myWhere;
        } else {
            return "(" + myWhere + ") AND (" + select + ")";
        }
    }
}
