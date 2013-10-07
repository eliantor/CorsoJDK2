package me.eto.justdoit;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;

/**
 * Created by eto on 10/5/13.
 * Definisce la struttura e l'API
 * della nostra applicazione.
 * Tramite costanti statiche
 */
public final class Contract {

    private Contract() {
        throw new AssertionError();
    }

    /**
     * Action da utilizzare per creare un todo.
     */
    public final static String INTENT_ACTION_ADD_TODO   =
            "me.eto.justdoid.intent.action.ADD_TODO";
    public final static String INTENT_EXTRA_TITLE       = "title";
    public final static String INTENT_EXTRA_DESCRIPTION = "description";


    //content://me.eto.justdoit.provider/todos/1
    //content://me.eto.justdoit.provider/todos?after=1000231

    public final static String AUTHORITY = "me.eto.justdoit.provider";

    private final static Uri ROOT_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    /**
     *
     */
    public final static class Todos {

        private Todos() {
            throw new AssertionError();
        }

        //content://me.eto.justdoit.provider/todos
        public final static String PATH        = "todos";
        public final static Uri    CONTENT_URI =
                ROOT_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static Uri singleTodo(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public final static int DONE    = 1;
        public final static int PENDING = 0;

        public static Cursor getAllTodos(ContentResolver resolver, String selection,
                                         String[] selectionArgs) {
            return resolver.query(CONTENT_URI, null, selection, selectionArgs, null);
        }

        public static ContentValues getTodoById(ContentResolver resolver, long id) {
            Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
            Cursor cursor = resolver.query(uri, null, null, null, null);
            try {
                if (cursor.moveToFirst()) {
                    ContentValues vals = new ContentValues();
                    DatabaseUtils.cursorIntToContentValues(cursor, Fields.ID, vals);
                    DatabaseUtils.cursorIntToContentValues(cursor, Fields.TITLE, vals);
                    DatabaseUtils.cursorIntToContentValues(cursor, Fields.DESCRIPTION, vals);
                    //... ecc;
                    return vals;
                }
                return null;
            } finally {
                cursor.close();
            }
        }


        public final static class Fields {

            public final static String ID          = "_id";
            public final static String TITLE       = "title";
            public final static String DATE        = "date";
            public final static String DESCRIPTION = "description";
            public final static String USER        = "user";
            public final static String STATUS      = "done";
        }


    }


}
