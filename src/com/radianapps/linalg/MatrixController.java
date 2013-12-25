package com.radianapps.linalg;

import com.radianapps.linalg.views.Matrix;
import com.radianapps.linalg.views.MatrixIME;

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

    private Position focus = new Position();

    private List<List<MatrixData>> matrix;
    private List<Matrix> views;

    public MatrixController() {
        matrix = new ArrayList<List<MatrixData>>();
        views = new ArrayList<Matrix>();
    }

    public void resizeMatrix(int newRows, int newCols) {
        boolean focusChanged = !focus.inLimits(newRows - 1, newCols - 1);
        if (focusChanged) {
            focus.x = 0;
            focus.y = 0;
            focus.setValid(true);
        }

        for (Matrix view : views) {
            view.resize(newRows, newCols);
            if (focusChanged) {
                view.setFocus(focus);
            }
        }

        int oldRows = numRows();

        if (oldRows <= newRows) {
            for (int i = oldRows; i < newRows; ++i) {
                matrix.add(new ArrayList<MatrixData>());
            }
        } else {
            for (int i = oldRows; i > newRows; --i) {
                matrix.remove(i - 1);
            }
        }

        for (int rowInd = 0; rowInd < newRows; ++rowInd) {
            List<MatrixData> entries = matrix.get(rowInd);
            int oldCols = entries.size();
            if (oldCols <= newCols) {
                for (int colInd = entries.size(); colInd < newCols; ++colInd) {
                    MatrixData data = new MatrixData();
                    entries.add(data);
                    putDataInMatrix(new Position(rowInd, colInd), data);
                }
            } else {
                for (int j = entries.size(); j > newCols; --j) {
                    entries.remove(j - 1);
                }
            }
        }
    }

    private MatrixData getDataFromMatrix(Position position) {
        return matrix.get(position.x).get(position.y);
    }

    private MatrixData getFocusData() {
        return getDataFromMatrix(focus);
    }

    private void putDataInMatrix(Position position, MatrixData data) {
        matrix.get(position.x).set(position.y, data);
        for (Matrix view : views) {
            view.setCellAt(position, data);
        }
    }

    private void putFocusData(MatrixData data) {
        putDataInMatrix(focus, data);
    }

    public void numericInput(int number) {
        MatrixData data = getFocusData();
        data.appendToStrData(number);
        putFocusData(data);
    }

    public void navInput(MatrixIME.Nav nav) {
        int maxCol = numCols() - 1;
        int maxRow = numRows() - 1;

        switch (nav) {
            case UP:
                if (focus.x > 0) {
                    --focus.x;
                } else {
                    focus.x = maxRow;
                }
                break;
            case DOWN:
                if (focus.x < maxRow) {
                    ++focus.x;
                } else {
                    focus.x = 0;
                }
                break;
            case RIGHT:
                if (focus.y < maxCol) {
                    ++focus.y;
                } else {
                    focus.y = 0;
                }
                break;
            case LEFT:
                if (focus.y > 0) {
                    --focus.y;
                } else {
                    focus.y = maxCol;
                }
                break;
        }

        for (Matrix view : views) {
            view.setFocus(focus);
        }
    }

    private int numCols() {
        return (!matrix.isEmpty()) ? matrix.get(0).size() : 0;
    }

    private int numRows() {
        return matrix.size();
    }

    public void registerView(Matrix matrix) {
        views.add(matrix);

        matrix.resize(numRows(), numCols());
        matrix.registerController(this);

        for (int rowIndex = 0; rowIndex < this.matrix.size(); ++rowIndex) {
            List<MatrixData> row = this.matrix.get(rowIndex);
            for (int colIndex = 0; colIndex < row.size(); ++colIndex) {
                matrix.setCellAt(new Position(rowIndex, colIndex), row.get(colIndex));
            }
        }

        if (focus.inLimits(numRows() - 1, numCols() - 1)) {
            matrix.setFocus(focus);
        }
    }

    public void unregisterView(Matrix matrix) {
        views.remove(matrix);
        matrix.unregisterController();
    }

    public static class MatrixData {
        private double numData;
        private String strData;
        private boolean hasDecimalPoint;

        public MatrixData() {
            strData = "0";
        }

        public void setStrData(String strData) {
            try {
                numData = Double.parseDouble(strData);
                this.strData = strData;
                hasDecimalPoint = this.strData.contains(".");
            } catch (NumberFormatException e) {
                // Do nothing
            }
        }

        public void setNumData(double numData) {
            this.numData = numData;
            strData = Double.toString(numData);
            hasDecimalPoint = this.strData.contains(".");
        }

        public void appendToStrData(int newChar) {
            if (newChar == MatrixIME.DECIMAL && !hasDecimalPoint) {
                strData += ".";
                numData = Double.parseDouble(strData);
                hasDecimalPoint = true;
            } else if (newChar != MatrixIME.DECIMAL) {
                strData += newChar;
                numData = Double.parseDouble(strData);
            }
        }

        public double getNumData() {
            return numData;
        }

        public String getStrData() {
            return strData;
        }

        public boolean hasDecimalPoint() {
            return hasDecimalPoint;
        }

    }

}
