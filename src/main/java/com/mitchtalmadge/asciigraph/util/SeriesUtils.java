package com.mitchtalmadge.asciigraph.util;

import java.util.Arrays;

public class SeriesUtils {

    /**
     * From the series, determines the minimum and maximum values.
     *
     * @param series The series.
     * @return An array of size 2, containing the minimum value of the series at index 0, and the maximum at index 1.
     * @throws IllegalArgumentException If the series does not contain at least one value, or is null.
     */
    public static double[] getMinAndMaxValues(double[] series) {
        if (series == null || series.length == 0)
            throw new IllegalArgumentException("The series must have at least one value.");

        // Initialize results with the largest value in the minimum spot, and smallest value in the maximum spot.
        double[] results = new double[]{Double.MAX_VALUE, Double.MIN_VALUE};

        // Find min and max.
        Arrays.stream(series).forEach(value -> {
            // Compare value to the current min and max.
            results[0] = Math.min(results[0], value);
            results[1] = Math.max(results[1], value);
        });

        return results;
    }

}
