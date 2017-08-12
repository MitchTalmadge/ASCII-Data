package com.mitchtalmadge.asciidata.graph.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SeriesUtilsTest {

    @Test
    public void testMinAndMaxValues() {
        double[] series = new double[]{12.5, 11.4, 28.9, -2.3, -5.245, -7.8827, -7.8828, 0, 2.4};

        assertArrayEquals(new double[]{-7.8828, 28.9}, SeriesUtils.getMinAndMaxValues(series), 0.0001);
    }

    @Test
    public void testMinAndMaxValuesWithSeriesLengthOne() {
        double[] series = new double[]{1};

        assertArrayEquals(new double[] {1, 1}, SeriesUtils.getMinAndMaxValues(series), 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinAndMaxValuesWithSeriesLengthZero() {
        SeriesUtils.getMinAndMaxValues(new double[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinAndMaxValuesWithNullSeries() {
        SeriesUtils.getMinAndMaxValues(null);
    }

}