package com.radianapps.linalg.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.radianapps.linalg.MatrixController;
import com.radianapps.linalg.R;

/**
 * Created with IntelliJ IDEA.
 * User: Tushar
 * Date: 9/9/13
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 */

public class MatrixIME extends RelativeLayout {

    private MatrixController matrixController;
    final private int DECIMAL = -1;

    public MatrixIME(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.inputmethod, this, true);

        setupNumPad();
    }

    private void numericInput(int number) {
        broadcastNumericInput(number);
    }

    private void setupNumPad() {
        ViewGroup numPad = (ViewGroup) findViewById(R.id.numPad);

        OnClickListener clickHandler = new OnClickListener() {
            @Override
            public void onClick(View v) {
                numericInput(numPadButtonToInt(v));
            }
        };
    }

    private int numPadButtonToInt(View v) {
        int number = 0;
        switch(v.getId()) {
            case R.id.numberZero:
                number = 0;
                break;
            case R.id.numberOne:
                number = 1;
                break;
            case R.id.numberTwo:
                number = 2;
                break;
            case R.id.numberThree:
                number = 3;
                break;
            case R.id.numberFour:
                number = 4;
                break;
            case R.id.numberFive:
                number = 5;
                break;
            case R.id.numberSix:
                number = 6;
                break;
            case R.id.numberSeven:
                number = 7;
                break;
            case R.id.numberEight:
                number = 8;
                break;
            case R.id.numberNine:
                number = 9;
                break;
            case R.id.numberDecimal:
                number = DECIMAL;
                break;
        }

        return number;
    }

    private void broadcastNumericInput(int number) {
        if (matrixController != null) {
            // TODO:    matrixController.numericInput(number);
        }
    }

    private void registerController(MatrixController matrixController) {
        this.matrixController = matrixController;
    }

    private void unregisterController() {
        this.matrixController = null;
    }
}
