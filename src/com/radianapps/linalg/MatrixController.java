package com.radianapps.linalg;

import android.util.Log;
import com.radianapps.linalg.views.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tushar
 * Date: 9/7/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class MatrixController {

    private List<List<Integer>> data;
    private List<Matrix> views;

    public MatrixController() {
        data = new ArrayList<List<Integer>>();
        views = new ArrayList<Matrix>();
    }

    public void resizeMatrix(int newRows, int newCols) {
        int oldRows = data.size();

        if (oldRows < newRows) {
            for (int i = oldRows; i < newRows; ++i) {
                data.add(new ArrayList<Integer>());
            }
        } else {
            data.subList(oldRows - newRows, oldRows).clear();
        }

        for (List<Integer> entries : data) {
            int oldCols = entries.size();
            if (oldCols < newCols) {
                for (int j = entries.size(); j < newCols; ++j) {
                    entries.add(0);
                }
            } else {
                entries.subList(oldCols - newCols, oldCols).clear();
            }
        }

        for (Matrix view : views) {
            view.resize(newRows, newCols);
        }
    }

    public void registerView(Matrix matrix) {
        views.add(matrix);

        int numCols = (!data.isEmpty()) ? data.get(0).size() : 0;
        Log.e("registerView", "a " + data.size() + " b " + numCols);
        matrix.resize(data.size(), numCols);
        matrix.registerController(this);

        for (int rowIndex = 0; rowIndex < data.size(); ++rowIndex) {
            List<Integer> row = data.get(rowIndex);
            for (int colIndex = 0; colIndex < row.size(); ++colIndex) {
                matrix.setCellAt(rowIndex, colIndex, row.get(colIndex));
            }
        }

    }

    public void unregisterView(Matrix matrix) {
        views.remove(matrix);
        matrix.unregisterController();
    }

}
