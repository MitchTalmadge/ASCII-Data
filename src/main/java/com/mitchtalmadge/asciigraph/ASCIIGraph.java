package com.mitchtalmadge.asciigraph;

import com.mitchtalmadge.asciigraph.util.SeriesUtils;

import java.text.DecimalFormat;

class ASCIIGraph {

    /**
     * The data series, with index being the x-axis and value being the y-axis.
     */
    private double[] series;

    /**
     * The minimum value in the series.
     */
    private double min;

    /**
     * The maximum value in the series.
     */
    private double max;

    /**
     * The range of the data in the series.
     */
    private double range;

    /**
     * The number of rows in the graph.
     */
    private int numRows;

    /**
     * The number of columns in the graph, including the axis and ticks.
     */
    private int numCols;

    /**
     * How wide the ticks are. Ticks are left-padded with spaces to be this length.
     */
    private int tickWidth = 8;

    /**
     * How the ticks should be formatted.
     */
    private DecimalFormat tickFormat = new DecimalFormat("###0.00");

    /**
     * The index at which the axis starts.
     */
    private int axisIndex;

    /**
     * Ths index at which the line starts.
     */
    private int lineIndex;

    private ASCIIGraph(double[] series) {
        this.series = series;
    }

    /**
     * Calculates the instance fields used for plotting.
     */
    private void calculateFields() {
        // Get minimum and maximum from series.
        double[] minMax = SeriesUtils.getMinAndMaxValues(this.series);
        this.min = minMax[0];
        this.max = minMax[1];
        this.range = min - max;

        // Since the graph is made of ASCII characters, it needs whole-number counts of rows and columns.
        this.numRows = numRows == 0 ? (int) Math.round(max - min) + 1 : numRows;
        // For columns, add the width of the tick marks, the width of the axis, and the length of the series.
        this.numCols = tickWidth + (axisIndex - tickWidth) + series.length;

        axisIndex = tickWidth + 1;
        lineIndex = axisIndex + 1;
    }

    /**
     * Creates an ASCIIGraph instance from the given series.
     *
     * @param series The series of data, where index is the x-axis and value is the y-axis.
     * @return A new ASCIIGraph instance.
     */
    public static ASCIIGraph fromSeries(double[] series) {
        return new ASCIIGraph(series);
    }

    /**
     * Determines the number of rows in the graph.
     * By default, the number of rows will be equal to the range of the series + 1.
     *
     * @param numRows The number of rows desired. If 0, uses the default.
     * @return This instance.
     */
    public ASCIIGraph withNumRows(int numRows) {
        this.numRows = numRows;
        return this;
    }

    /**
     * Determines the minimum width of the ticks on the axis.
     * Ticks will be left-padded with spaces if they are not already this length.
     * Defaults to 8.
     *
     * @param tickWidth The width of the ticks on the axis.
     * @return This instance.
     */
    public ASCIIGraph withTickWidth(int tickWidth) {
        this.tickWidth = tickWidth;
        return this;
    }

    /**
     * Determines how the ticks will be formatted.
     * Defaults to "###0.00".
     *
     * @param tickFormat The format of the ticks.
     * @return This instance.
     */
    public ASCIIGraph withTickFormat(DecimalFormat tickFormat) {
        this.tickFormat = tickFormat;
        return this;
    }

    /**
     * Plots the graph and returns it as a String.
     *
     * @return The string representation of the graph, using new lines.
     */
    public String plot() {
        calculateFields();

        // ---- PLOTTING ---- //

        // The graph is initially stored in a 2D array, later turned into Strings.
        char[][] graph = new char[numRows][numCols];

        // Fill the graph with space characters.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < graph[row].length; col++) {
                graph[row][col] = ' ';
            }
        }

        // Draw the ticks and graph.
        drawTicksAndAxis(graph);

        // Draw the line.
        drawLine(graph);

        // Convert the 2D char array graph to a String using newlines.
        return convertGraphToString(graph);
    }

    /**
     * Adds the tick marks and axis to the graph.
     *
     * @param graph The graph.
     */
    private void drawTicksAndAxis(char[][] graph) {
        // Add the labels and the axis.
        for (int row = 0; row < graph.length; row++) {

            double y = determineYValueAtRow(row);

            // Compute and Format Tick
            char[] tick = formatTick(y).toCharArray();

            // Insert Tick
            System.arraycopy(tick, 0, graph[row], 0, tick.length);

            // Insert Axis line. '┼' is used at the origin.
            graph[row][axisIndex] = (y == 0) ? '┼' : '┤';
        }
    }

    /**
     * Adds the line to the graph.
     *
     * @param graph The graph.
     */
    private void drawLine(char[][] graph) {
        // The row closest to y when x = 0.
        int initialRow = determineRowAtYValue(series[0]);
        // Modify the axis to show the start.
        graph[initialRow][axisIndex] = '┼';

        for (int x = 0; x < series.length - 1; x++) {
            // The start and end locations of the line.
            int startRow = determineRowAtYValue(series[x]);
            int endRow = determineRowAtYValue(series[x + 1]);

            if (startRow == endRow) { // The line is horizontal.
                graph[startRow][lineIndex + x] = '─';
            } else { // The line has slope.
                // Draw the curved lines.
                graph[startRow][lineIndex + x] = (startRow < endRow) ? '╮' : '╯';
                graph[endRow][lineIndex + x] = (startRow < endRow) ? '╰' : '╭';

                // If the rows are more than 1 row apart, we need to fill in the gap with vertical lines.
                int lowerRow = Math.min(startRow, endRow);
                int upperRow = Math.max(startRow, endRow);
                for (int row = lowerRow + 1; row < upperRow; row++) {
                    graph[row][lineIndex + x] = '│';
                }
            }
        }
    }

    /**
     * Determines the row closest to the given y-axis value.
     *
     * @param yValue The value of y.
     * @return The closest row to the given y-axis value.
     */
    private int determineRowAtYValue(double yValue) {
        // ((yValue - min) / range) creates a ratio -- how deep the y-value is into the range.
        // Multiply that by the number of rows to determine how deep the y-value is into the number of rows.
        // Then invert it buy subtracting it from the number of rows, since 0 is actually the top.

        // 1 is subtracted from numRows since it is a length, and we start at 0.
        return (numRows - 1) - (int) Math.round(((yValue - min) / range) * (numRows - 1));
    }

    /**
     * Determines the y-axis value corresponding to the given row.
     *
     * @param row The row.
     * @return The y-axis value at the given row.
     */
    private double determineYValueAtRow(int row) {
        // Compute the current y value by starting with the maximum and subtracting how far down we are in rows.
        // Splitting the range into chunks based on the number of rows gives us how much to subtract per row.
        // (-1 from the number of rows because it is a length, and the last row index is actually numRows - 1).
        return max - (row * (range / (numRows - 1)));
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
