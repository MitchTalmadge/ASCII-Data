package com.mitchtalmadge.asciidata.graph;

import com.mitchtalmadge.asciidata.TestUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ASCIIGraphTest {

    private static final double[] sinWaveSeries = new double[120];
    private static final double[] randomWaveSeries = new double[]{
            10, 16, 15, 14, 13, 7, 12, 16, 6, 8, 5, 18, 9, 13, 6, 16, 4, 5, 2, 8, 6, 15, 19, 10, 1,
            17, 2, 9, 19, 6, 10, 19, 13, 4, 10, 10, 14, 10, 10, 9, 8, 16, 14, 12, 14, 11, 3, 13, 18, 15,
            10, 18, 6, 2, 2, 19, 12, 11, 5, 7, 6, 11, 17, 3, 14, 3, 10, 12, 19, 5, 6, 16, 2, 8, 9,
            9, 12, 5, 2, 17, 12, 5, 1, 5, 7, 7, 15, 19, 5, 9}; // 90 Random numbers

    static {
        // Compute Sin Wave Series
        for (int i = 0; i < sinWaveSeries.length; i++)
            sinWaveSeries[i] = 15 * Math.sin(i * ((Math.PI * 4) / sinWaveSeries.length));


    }

    @Test
    public void testSinWaveFullHeight() throws IOException {
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("graphs/sinWaveFullHeight.txt")),
                TestUtils.commonizeLineEndings(ASCIIGraph.fromSeries(sinWaveSeries).plot())
        );
    }

    @Test
    public void testSinWaveHalfHeight() throws IOException {
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("graphs/sinWaveHalfHeight.txt")),
                TestUtils.commonizeLineEndings(ASCIIGraph.fromSeries(sinWaveSeries).withNumRows(15).plot())
        );
    }

    @Test
    public void testRandomWave() throws IOException {
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("graphs/randomWave.txt")),
                TestUtils.commonizeLineEndings(ASCIIGraph.fromSeries(randomWaveSeries).plot())
        );
    }

}