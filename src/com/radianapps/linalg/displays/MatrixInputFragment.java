package com.radianapps.linalg.displays;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.SherlockFragment;
import com.radianapps.linalg.MatrixController;
import com.radianapps.linalg.R;
import com.radianapps.linalg.views.Matrix;

/**
 * Created with IntelliJ IDEA.
 * User: Tushar
 * Date: 9/5/13
 * Time: 12:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class MatrixInputFragment extends SherlockFragment {
    private Spinner rowSpinner, colSpinner;
    private Matrix matrix;
    private MatrixSpinnerAdapter rowAdapter, colAdapter;
    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            onDimensionChanged((Integer) rowSpinner.getSelectedItem(), (Integer) colSpinner.getSelectedItem());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private MatrixController matrixController = new MatrixController();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_matrixinput, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rowSpinner = (Spinner) view.findViewById(R.id.rowSpinner);
        colSpinner = (Spinner) view.findViewById(R.id.colSpinner);
        matrix = (Matrix) view.findViewById(R.id.matrixHolder);

        rowAdapter = new MatrixSpinnerAdapter();
        colAdapter = new MatrixSpinnerAdapter();

        rowSpinner.setAdapter(rowAdapter);
        colSpinner.setAdapter(colAdapter);

        rowSpinner.setOnItemSelectedListener(spinnerListener);
        colSpinner.setOnItemSelectedListener(spinnerListener);
        matrixController.registerView(matrix);
    }

    private void onDimensionChanged(int rows, int columns) {
        Toast.makeText(getActivity(), "rows: " + rows + " columns: " + columns, Toast.LENGTH_SHORT).show();
        matrixController.resizeMatrix(rows, columns);
    }

    private class MatrixSpinnerAdapter implements SpinnerAdapter {
        final int MAX_DIMENSION = 5;

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView retView;
            if (convertView == null) {
                convertView = new TextView(getActivity());
            }
            retView = (TextView) convertView;

            retView.setText(String.valueOf(position + 1));

            return retView;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public int getCount() {
            return MAX_DIMENSION - 1; // 0 is not a valid value, so sub 1!
        }

        @Override
        public Object getItem(int position) {
            return position + 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return getCount() == 0;
        }
    }
}
