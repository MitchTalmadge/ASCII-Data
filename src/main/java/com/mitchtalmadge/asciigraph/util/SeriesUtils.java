package com.mitchtalmadge.asciigraph.util;

import java.util.Arrays;

public class SeriesUtils {

    /**
     * From the series, determines the minimum and maximum values.
     *
     * @param series The series.
     * @return An array of size 2, containing the minimum value of the series at index 0, and the maximum at index 1.
     */
    public static double[] getMinAndMaxValues(double[] series) {
        double[] results = new double[2];

        Arrays.stream(series).forEach(value -> {
            results[0] = Math.min(results[0], value);
            results[1] = Math.max(results[1], value);
        });

        return results;
    }

}
