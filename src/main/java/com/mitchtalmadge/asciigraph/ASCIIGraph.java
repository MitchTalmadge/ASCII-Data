package com.mitchtalmadge.asciigraph;

import com.mitchtalmadge.asciigraph.util.SeriesUtils;

import java.text.DecimalFormat;

public class ASCIIGraph {

    private final double[] series;

    private int height = 0;

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

        // Since the graph is made of ASCII characters, it needs whole-number counts of rows and columns.
        int numRows = (int) Math.abs(Math.round(minMax[1] * rangeScale) - Math.round(minMax[0] * rangeScale));
        // For columns, add the width of the tick marks, 2 spaces for the axis, the offset, and the length of the series.
        int numCols = tickWidth + 2 + series.length;


        // ---- PLOTTING ---- //

        // The graph is initially stored in a 2D array, later turned into Strings.
        char[][] graph = new char[numRows][numCols];

        // Ticks and Axis
        drawTicksAndAxis(graph, minMax);


        return convertGraphToString(graph);
    }

    /**
     * Adds the Tick Marks and Axis to the Graph on the left hand side.
     *
     * @param graph  The 2D char array representation of the graph.
     * @param minMax The minimum and maximum values of the y-axis in a length 2 array.
     */
    private void drawTicksAndAxis(char[][] graph, double[] minMax) {
        double range = Math.abs(minMax[0] - minMax[1]);

        // Add the labels and the axis.
        for (int row = 0; row < graph.length; row++) {

            double y = determineYValueAtRow(row, graph.length, minMax);

            // Compute and Format Tick
            char[] tick = formatTick(y).toCharArray();

            // Insert Tick
            System.arraycopy(tick, 0, graph[row], 0, tick.length);

            // Insert Axis line, with a space between the tick and axis. '┼' is used at the origin.
            graph[row][tick.length + 1] = ' ';
            graph[row][tick.length + 2] = (y == 0) ? '┼' : '┤';
        }
    }

    /**
     * Determines the y-axis value corresponding to the given row.
     *
     * @param row     The row.
     * @param numRows The total number of rows.
     * @param minMax  The minimum and maximum values of the y-axis in a length 2 array.
     * @return The y-axis value at the given row.
     */
    private double determineYValueAtRow(int row, int numRows, double[] minMax) {
        double range = Math.abs(minMax[1] - minMax[0]);

        // Compute the current y value by starting with the maximum and subtracting how far down we are in rows.
        // Splitting the range into chunks based on the number of rows gives us how much to subtract per row.
        // (-1 from the number of rows because it is a length, and the last row index is actually numRows - 1).
        return minMax[1] - (row * (range / (numRows - 1)));
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
