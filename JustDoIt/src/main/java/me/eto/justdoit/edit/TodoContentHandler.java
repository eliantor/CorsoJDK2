package me.eto.justdoit.edit;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import me.eto.justdoit.Contract;

/**
 * Created by eto on 10/5/13.
 */
class TodoContentHandler extends AsyncQueryHandler {

    private final static int INSERT_TODO = 1;

    public TodoContentHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        if (token == INSERT_TODO) {

            Context globalContext = (Context) cookie;
            Toast.makeText(globalContext, "Inserted todo, you can find it at: " + uri,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void startInsertTodo(Context context, String title, String description) {
        ContentValues v = new ContentValues();
        v.put(Contract.Todos.Fields.TITLE, title);
        v.put(Contract.Todos.Fields.DESCRIPTION, description);

        /*
         global context é unico per tutta l'app e non sparisce mai fino
         a che non é morto anche il processo. Quindi nessun problema a passarlo ad
         altri thread
         */
        Context globalContext = context.getApplicationContext();

        this.startInsert(INSERT_TODO, globalContext, Contract.Todos.CONTENT_URI, v);
    }
}
