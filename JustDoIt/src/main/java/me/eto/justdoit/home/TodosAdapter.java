package me.eto.justdoit.home;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import me.eto.justdoit.Contract;
import me.eto.justdoit.R;

/**
 * Created by eto on 10/8/13.
 */
public class TodosAdapter extends CursorAdapter {

    private final static int INVALID_COLUMN_INDEX = -1;

    private final LayoutInflater fInflater;
    private final IndexCache     fIndexCache;

    public TodosAdapter(Context context) {
        super(context,/*Cursor*/ null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fInflater = LayoutInflater.from(context);
        fIndexCache = new IndexCache();
    }

    /*
     * Cursor adapter implements getView for us
     * it handles the recycling of the view, (but not the viewholder)
     * and the mechanics of moving the cursor in the right spot.
     * Ignoring the implementation details it does something similar
     * to the following commented code:
     *
     *   @Override
     *   public View getView(int position, View convertView, ViewGroup parent) {
     *       moveCursorToTheRightPosition(position);
     *       if (convertView == null){
     *           convertView = newView(context,cursor,parent);
     *       }
     *       bindView(convertView,context,cursor);
     *       return convertView;
     *   }
     */


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = fInflater.inflate(R.layout.todo_row, viewGroup, false);
        ViewHolder h = new ViewHolder();
        h.todoTitle = (TextView) v.findViewById(R.id.tv_todo_title);
//        h.todoStatus = (CheckBox) v.findViewById(R.id.chek_done);
        v.setTag(h);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //we are just on the right spot
        //we get the view holder
        final ViewHolder h = (ViewHolder) view.getTag();

        // we get the columns values
        final String todoText = cursor.getString(fIndexCache.title);
        final boolean isTodoDone = (cursor.getInt(fIndexCache.status) & 1) != 0;

        // and set it in the ui
        h.todoTitle.setText(todoText);
//        h.todoStatus.setChecked(isTodoDone);
    }

    /**
     * We have an overriden version of
     * swapCursor that caches column indexes
     *
     * @param cursor
     * @return
     */
    @Override
    public Cursor swapCursor(Cursor cursor) {
        Cursor old = super.swapCursor(cursor);
        initColumnIndexes(cursor);
        return old;
    }

    /**
     * We cache the column indexes
     *
     * @param cursor
     */
    private void initColumnIndexes(Cursor cursor) {
        if (cursor != null) {
            fIndexCache.id = cursor.getColumnIndexOrThrow(
                    Contract.Todos.Fields.ID);
            fIndexCache.status = cursor.getColumnIndexOrThrow(
                    Contract.Todos.Fields.STATUS);
            fIndexCache.title = cursor.getColumnIndexOrThrow(
                    Contract.Todos.Fields.TITLE);
        } else {
            fIndexCache.id = INVALID_COLUMN_INDEX;
            fIndexCache.status = INVALID_COLUMN_INDEX;
            fIndexCache.title = INVALID_COLUMN_INDEX;
        }
    }


    private static final class ViewHolder {

        TextView todoTitle;
        CheckBox todoStatus;
    }

    private static final class IndexCache {

        private int id     = INVALID_COLUMN_INDEX;
        private int title  = INVALID_COLUMN_INDEX;
        private int status = INVALID_COLUMN_INDEX;
    }
}
