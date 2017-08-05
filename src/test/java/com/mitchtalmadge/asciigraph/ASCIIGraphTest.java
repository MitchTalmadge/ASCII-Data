package com.mitchtalmadge.asciigraph;

import org.junit.Test;

public class ASCIIGraphTest {

    private static final double[] sinWaveSeries = new double[120];

    static {
        // Compute Sin Wave Series
        for (int i = 0; i < sinWaveSeries.length; i++)
            sinWaveSeries[i] = 15 * Math.sin(i * ((Math.PI * 4) / sinWaveSeries.length));
    }

    @Test
    public void testBasicGraph() {
        System.out.println(ASCIIGraph.fromSeries(sinWaveSeries).withHeight(11).plot());
    }

}