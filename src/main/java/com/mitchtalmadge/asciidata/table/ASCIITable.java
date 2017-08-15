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
    private final int columnsCount;
    private final int[] columnWidths;
    private final int emptyWidth;
    private final String emptyMessage = "(empty)";

    /**
     * Creates a new table using the given headers and data.
     *
     * @param headers The headers of the table. Each index is a new column, in order from left to right.
     * @param data    The data of the table, in the format String[row][column]. Newlines are allowed.
     * @return A new ASCIITable instance that can be printed using the {@link ASCIITable#toString()}.
     */
    public static ASCIITable fromData(String[] headers, String[][] data) {
        // Ensure we have headers.
        if (headers == null)
            throw new IllegalArgumentException("The table headers array is null.");
        if (headers.length == 0)
            throw new IllegalArgumentException("No headers were supplied.");

        // Set the data to empty if it is null.
        if (data == null)
            data = new String[0][0];

        // Create an ASCIITable instance.
        return new ASCIITable(headers, data);
    }

    /**
     * Constructs a new ASCIITable from the given headers and data.
     *
     * @param headers The headers of the table.
     * @param data    The data of the table.
     */
    private ASCIITable(String[] headers, String[][] data) {
        this.headers = headers;
        this.data = data;

        // The number of columns in the table is equivalent to the number of headers.
        columnsCount = headers.length;

        // Calculate the max width of each column in the table.
        columnWidths = new int[columnsCount];
        for (int row = 0; row < data.length + 1; row++) {
            // The first row is for the headers.
            String[] rowData = row == 0 ? headers : data[row - 1];

            // Make sure we don't have too many columns.
            if (rowData.length > columnsCount)
                throw new IllegalArgumentException("There are too many columns at row: " + (row - 1));

            // Iterate over each column in the row to get its width, and compare it to the maximum.
            for (int column = 0; column < rowData.length; column++) {
                // Check the length of each line in the cell.
                for (String rowDataLine : rowData[column].split("\\n"))
                    // Compare to the current max width.
                    columnWidths[column] = Math.max(columnWidths[column], rowDataLine.length());
            }
        }

        // Determine the width of everything including borders.
        // This is to be used in case there is no data and we must write the empty message to the table.

        // Account for borders
        int emptyWidth = 3 * (columnsCount - 1);
        // Add the width of each column.
        for (int columnWidth : columnWidths) {
            emptyWidth += columnWidth;
        }
        this.emptyWidth = emptyWidth;

        // Make sure we have enough space for the empty message.
        if (emptyWidth < emptyMessage.length()) {
            columnWidths[columnsCount - 1] += emptyMessage.length() - emptyWidth;
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        // Append the table's top horizontal divider.
        appendHorizontalDivider(output, '╔', '═', '╤', '╗');

        // Append the headers of the table.
        appendRow(output, headers);

        // Check if the data is empty, in which case, we will only write the empty message into the table contents.
        if (data.length == 0) {
            // Horizontal divider below the headers
            appendHorizontalDivider(output, '╠', '═', '╧', '╣');
            // Empty message row
            output.append('║').append(pad(emptyWidth, emptyMessage)).append("║\n");
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
        for (int column = 0; column < columnsCount; column++) {
            // The height of this cell.
            int cellHeight = data[column].split("\\n").length;
            // Choose the greatest.
            rowHeight = Math.max(rowHeight, cellHeight);
        }

        // Step 2: Append the data to the output.
        // Iterate over each line of text, using the row height calculated earlier.
        for (int line = 0; line < rowHeight; line++) {

            // For each column...
            for (int column = 0; column < columnsCount; column++) {

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
        for (int column = 0; column < columnsCount; column++) {
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
