package com.ismael.manageproductsprovider.utils;

import android.util.SparseBooleanArray;

/**
 * Created by usuario on 16/12/16.
 */
public class SelectionUtils {
    SparseBooleanArray sparseBoolean;

    public SelectionUtils(SparseBooleanArray sparseBoolean) {

        this.sparseBoolean = sparseBoolean;
    }

    public SparseBooleanArray getSparseBoolean() {
        return sparseBoolean;
    }

    public void setSparseBoolean(SparseBooleanArray sparseBoolean) {
        this.sparseBoolean = sparseBoolean;
    }

    public boolean isPositionChecker(int position) {
        Boolean result = sparseBoolean.get(position);
        return result==null?false:result;
    }
}
