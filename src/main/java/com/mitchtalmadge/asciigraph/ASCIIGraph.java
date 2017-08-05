package com.mitchtalmadge.asciigraph;

import com.mitchtalmadge.asciigraph.util.SeriesUtils;

import java.text.DecimalFormat;

public class ASCIIGraph {

    private final double[] series;

    private int height = 0;
    private int offset = 3;

    private int tickWidth = 7;
    DecimalFormat tickFormat = new DecimalFormat("#0.00");

    private ASCIIGraph(double[] series) {
        this.series = series;
    }

    public static ASCIIGraph fromSeries(double[] series) {
        return new ASCIIGraph(series);
    }

    public ASCIIGraph withHeight(int height) {
        this.height = height;
        return this;
    }

    public ASCIIGraph withOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public ASCIIGraph withTickWidth(int tickWidth) {
        this.tickWidth = tickWidth;
        return this;
    }

    public ASCIIGraph withTickFormat(DecimalFormat tickFormat) {
        this.tickFormat = tickFormat;
        return this;
    }

    public String plot() {

        // ---- SETUP ---- //

        // Get minimum and maximum from series.
        double[] minMax = SeriesUtils.getMinAndMaxValues(this.series);

        // Compute the range of the series.
        double range = Math.abs(minMax[0] - minMax[1]);

        // Decide the height of the graph. If 0, use the range.
        double height = this.height == 0 ? range : this.height;

        // The scale of the range of the graph is determined by the ratio between the range and the desired height.
        double rangeScale = height / range;

        // Determine the scaled min and max based on the range scale.
        minMax[0] = minMax[0] * rangeScale;
        minMax[1] = minMax[1] * rangeScale;

        // Since the graph is made of ASCII characters, it needs whole-number counts of rows and columns.
        int numRows = (int) Math.round(Math.abs(scaledMinMax[0] - scaledMinMax[1]));
        int numCols = series.length + offset;

        // ---- PLOTTING ---- //

        // The graph is initially stored in a 2D array, later turned into Strings.
        // The columns include the width of the tick, 2 spaces for an axis, and the number of columns for the lines.
        char[][] graph = new char[numRows][tickWidth + 2 + numCols];

        // Add the labels and the axis.
        for (int y = 0; y < numRows; y++) {
            // Compute and Format Tick
            char[] tick = formatTick(minMax[1] - y * range / numRows).toCharArray();

            // Insert Tick
            System.arraycopy(tick, 0, graph[y], 0, tick.length);

            // Insert Axis line, with a space between the tick and axis. '┼' is used at the origin.
            graph[y][tick.length + 1] = ' ';
            graph[y][tick.length + 2] = (y == 0) ? '┼' : '┤';
        }

        return convertGraphToString(graph);
    }

    /**
     * Formats the given value as a tick mark on the graph.
     * Pads the tick mark with the correct number of spaces
     * in order to be {@link ASCIIGraph#tickWidth} characters long.
     *
     * @param value The value of the tick mark.
     * @return The formatted tick mark.
     */
    private String formatTick(double value) {
        StringBuilder paddedTick = new StringBuilder();

        String formattedValue = tickFormat.format(value);
        for (int i = 0; i < tickWidth - formattedValue.length(); i++) {
            paddedTick.append(' ');
        }

        return paddedTick.append(formattedValue).toString();
    }

    /**
     * Converts the 2D char array representation of the graph into a String representation.
     *
     * @param graph The 2D char array representation of the graph.
     * @return The String representation of the graph.
     */
    private String convertGraphToString(char[][] graph) {
        StringBuilder stringGraph = new StringBuilder();

        for (char[] row : graph) {
            stringGraph.append(row).append('\n');
        }

        return stringGraph.toString();
    }

}
