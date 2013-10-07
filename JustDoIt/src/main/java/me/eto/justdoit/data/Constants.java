package me.eto.justdoit.data;

/**
 * Created by eto on 10/5/13.
 */

import android.content.UriMatcher;

import me.eto.justdoit.Contract;

import static me.eto.justdoit.Contract.*;

class Constants {

    final static String DB_NAME    = "todos.db";
    final static int    DB_VERSION = 1;

    public static final String CREATE_TODOS = "CREATE TABLE " + Todos.PATH + " (" +
            Todos.Fields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Todos.Fields.TITLE + " TEXT," +
            Todos.Fields.DESCRIPTION + " TEXT," +
            Todos.Fields.DATE + " INTEGER," +
            Todos.Fields.STATUS + " INTEGER," +
            Todos.Fields.USER + " TEXT)";

    public static final String DROP_TODOS = "DROP TABLE " + Todos.PATH;


    static class Match {

        final static int ALL_TODOS = 1;
        final static int ONE_TODO  = 2;
    }

    final static UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(Contract.AUTHORITY, Todos.PATH, Match.ALL_TODOS);
        MATCHER.addURI(Contract.AUTHORITY, Todos.PATH + "/#", Match.ONE_TODO);
    }
}
