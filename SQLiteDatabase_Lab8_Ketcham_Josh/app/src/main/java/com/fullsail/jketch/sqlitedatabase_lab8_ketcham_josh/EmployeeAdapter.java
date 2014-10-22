package com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;


public class EmployeeAdapter extends ResourceCursorAdapter {

    public EmployeeAdapter(Context c, Cursor  cur) {
        super(c, R.layout.employee_item_layout, cur, 0);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String FirstName = cursor.getString(1);
        String LastName = cursor.getString(2);

        TextView tv = (TextView) view.findViewById(R.id.firstName);
        tv.setText(FirstName);

        tv = (TextView) view.findViewById(R.id.lastName);
        tv.setText(LastName);

    }
}
