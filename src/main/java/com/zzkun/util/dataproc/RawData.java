package com.zzkun.util.dataproc;


/**
 * Created by kun on 2016/7/13.
 */
public class RawData {

    private Double data;
    private boolean valid;

    public RawData(Double data, boolean valid) {
        this.data = data;
        this.valid = valid;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "RawData{" +
                "data=" + data +
                ", valid=" + valid +
                '}';
    }
}
