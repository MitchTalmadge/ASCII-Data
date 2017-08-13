package com.mitchtalmadge.asciidata.table;

/**
 * A table created entirely from ASCII characters, such as pipes.
 * Printable to a console, chat server, or anywhere else where text-style tables are convenient.
 *
 * @author MitchTalmadge
 * @author JakeWharton
 */
public class ASCIITable {

    private final String[] headers;
    private final String[][] data;
    private final int columns;
    private final int[] columnWidths;
    private final int emptyWidth;

    /**
     * Creates a new table using the given headers and data.
     *
     * @param headers The headers of the table. Each index is a new column, in order from left to right.
     * @param data    The data of the table, in the format String[row][column]. Newlines are allowed.
     * @return A new ASCIITable instance that can be printed using the {@link ASCIITable#toString()}.
     */
    public static ASCIITable fromData(String[] headers, String[][] data) {
        if (headers == null) throw new NullPointerException("headers == null");
        if (headers.length == 0) throw new IllegalArgumentException("Headers must not be empty.");
        if (data == null) throw new NullPointerException("data == null");
        return new ASCIITable(headers, data);
    }

    private ASCIITable(String[] headers, String[][] data) {
        this.headers = headers;
        this.data = data;

        columns = headers.length;
        columnWidths = new int[columns];
        for (int row = -1; row < data.length; row++) {
            String[] rowData = (row == -1) ? headers : data[row]; // Hack to parse headers too.
            if (rowData.length != columns) {
                throw new IllegalArgumentException(
                        String.format("Row %s's %s columns != %s columns", row + 1, rowData.length, columns));
            }
            for (int column = 0; column < columns; column++) {
                for (String rowDataLine : rowData[column].split("\\n")) {
                    columnWidths[column] = Math.max(columnWidths[column], rowDataLine.length());
                }
            }
        }

        int emptyWidth = 3 * (columns - 1); // Account for column dividers and their spacing.
        for (int columnWidth : columnWidths) {
            emptyWidth += columnWidth;
        }
        this.emptyWidth = emptyWidth;

        if (emptyWidth < EMPTY.length()) { // Make sure we're wide enough for the empty text.
            columnWidths[columns - 1] += EMPTY.length() - emptyWidth;
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        // Append the table's top horizontal divider.
        appendHorizontalDivider(output, '╔', '═', '╤', '╗');

        // Append the headers of the table.
        appendRow(output, headers);

        // Check if the data is empty, in which case, we will only write "(empty)" into the table contents.
        if (data.length == 0) {
            // Horizontal divider below the headers
            appendHorizontalDivider(output, '╠', '═', '╧', '╣');
            // "(empty)" row
            output.append('║').append(pad(emptyWidth, "(empty)")).append("║\n");
            // Horizontal divider at the bottom of the table.
            appendHorizontalDivider(output, '╚', '═', '═', '╝');
            return output.toString();
        }

        // The data is not empty, so iterate over each row.
        for (int row = 0; row < data.length; row++) {

            // The first row has a different style of border than the others.
            if (row == 0)
                appendHorizontalDivider(output, '╠', '═', '╪', '╣');
            else
                appendHorizontalDivider(output, '╟', '─', '┼', '╢');

            // Append the data for the current row.
            appendRow(output, data[row]);
        }

        // Horizontal divider at the bottom of the table.
        appendHorizontalDivider(output, '╚', '═', '╧', '╝');
        return output.toString();
    }

    /**
     * Appends the given data as a row to the output with appropriate borders.
     *
     * @param output The output to append to.
     * @param data   The data of the row to append. Each index corresponds to a column.
     */
    private void appendRow(StringBuilder output, String[] data) {
        // Step 1: Determine the row height from the maximum number of lines out of each cell.
        int rowHeight = 0;
        for (int column = 0; column < columns; column++) {
            // The height of this cell.
            int cellHeight = data[column].split("\\n").length;
            // Choose the greatest.
            rowHeight = Math.max(rowHeight, cellHeight);
        }

        // Step 2: Append the data to the output.
        // Iterate over each line of text, using the row height calculated earlier.
        for (int line = 0; line < rowHeight; line++) {

            // For each column...
            for (int column = 0; column < columns; column++) {

                // Either add the left or the middle borders, depending on the location of the column.
                output.append(column == 0 ? '║' : '│');

                // Split the data on its newlines to determine the contents of each line in the column.
                String[] cellLines = data[column].split("\\n");

                // Decide what to put into this column. Use empty data if there is no specific data for this column.
                String cellLine = line < cellLines.length ? cellLines[line] : "";

                // Pad and append the data.
                output.append(pad(columnWidths[column], cellLine));
            }

            // Add the right border.
            output.append("║\n");
        }
    }

    /**
     * Appends a horizontal divider to the output using the given characters.
     * <p>
     * Example output: (L = Left, F = Fill, M = Middle, R = Right)
     * ╚════╧═══════╧════╧═══════╝
     * LFFFMFFFFFFMFFFMFFFFFFR
     *
     * @param output The output to append to.
     * @param left   The left border character.
     * @param fill   The fill border character.
     * @param middle The middle border character.
     * @param right  The right border character.
     */
    private void appendHorizontalDivider(StringBuilder output, char left, char fill, char middle, char right) {

        // For each column...
        for (int column = 0; column < columns; column++) {
            // Either add the left or the middle borders, depending on the location of the column.
            output.append(column == 0 ? left : middle);

            // For the contents of the column, create a padding of the correct width and replace it with the fill border.
            output.append(pad(columnWidths[column], "").replace(' ', fill));
        }

        // Add the right border
        output.append(right).append('\n');
    }

    /**
     * Pads the given data to the specified width using spaces.
     *
     * @param width The width desired.
     * @param data  The data to pad.
     * @return The data, padded with spaces to the given width.
     */
    private static String pad(int width, String data) {
        return String.format(" %1$-" + width + "s ", data);
    }

}
