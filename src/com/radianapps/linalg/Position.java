package com.radianapps.linalg;

/**
* Created by Tushar on 12/24/13.
*/

public class Position {
    private boolean valid;
    public int x;
    public int y;

    public Position() {
        valid = false;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position p) {
        this.x = p.x;
        this.y= p.y;
        this.valid = p.valid;
    }

    public boolean inLimits(int x, int y) {
        return valid &&
               ((this.x <= x) && (this.x >= 0)) &&
               ((this.y <= y) && (this.y >= 0));
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "Position -- x: " + this.x + " y: " + this.y;
    }
}
