package com.radianapps.linalg.displays;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Tushar
 * Date: 9/5/13
 * Time: 10:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class MatrixEntry {
    private int value;
    private TextView field;

    public MatrixEntry(Context context) {
        field = new TextView(context);
    }

    TextView getField() {
        return field;
    }
}
