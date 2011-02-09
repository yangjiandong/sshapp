package org.springside.modules.charts;

public class ChartException extends RuntimeException {
    public ChartException() {
    }

    public ChartException(String s) {
        super(s);
    }

    public ChartException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ChartException(Throwable throwable) {
        super(throwable);
    }
}
