package com.mitchtalmadge.asciidata.table.formats;

/**
 * Determines the characters that make up an ASCIITable.
 * Default is {@link UTF8TableFormat}.
 * @author MitchTalmadge
 */
public abstract class TableFormatAbstract {

    // ============ Corners

    /**
     * @return The top left corner of the table. Ex: ╔
     */
    public abstract char getTopLeftCorner();

    /**
     * @return The top right corner of the table. Ex: ╗
     */
    public abstract char getTopRightCorner();

    /**
     * @return The bottom left corner of the table. Ex: ╚
     */
    public abstract char getBottomLeftCorner();

    /**
     * @return The bottom right corner of the table. Ex: ╝
     */
    public abstract char getBottomRightCorner();

    // ============ Top and Bottom Edges

    /**
     * @return The character used when dividing columns on the top edge of the table. Ex: ╤
     */
    public abstract char getTopEdgeBorderDivider();

    /**
     * @return The character used when dividing columns on the bottom edge of the table. Ex: ╧
     */
    public abstract char getBottomEdgeBorderDivider();

    // ============ Left and Right Edges

    /**
     * @param underHeaders True if the border is directly between the headers of the table and the first row.
     * @return The character used when dividing rows on the left edge of the table. Ex: ╠ under the headers, and ╟ everywhere else.
     */
    public abstract char getLeftEdgeBorderDivider(boolean underHeaders);

    /**
     * @param underHeaders True if the border is directly between the headers of the table and the first row.
     * @return The character used when dividing rows on the right edge of the table. Ex: ╣ under the headers, and ╢ everywhere else.
     */
    public abstract char getRightEdgeBorderDivider(boolean underHeaders);

    // ============ Fills

    /**
     * @param edge         True if the border is on the top or bottom edge of the table.
     * @param underHeaders True if the border is directly between the headers of the table and the first row.
     * @return The character used for horizontal stretches in the table. Ex: ═ for edges and under the headers, and ─ everywhere else.
     */
    public abstract char getHorizontalBorderFill(boolean edge, boolean underHeaders);

    /**
     * @param edge True if the border is on the left or right edge of the table.
     * @return The character used for vertical stretches in the table. Ex: ║ for edges, and │ everywhere else.
     */
    public abstract char getVerticalBorderFill(boolean edge);

    // ============ Crosses

    /**
     * @param underHeaders True if the border is directly between the headers of the table and the first row.
     * @param emptyData    True if the table has no data. In this case it can be more aesthetically pleasing to use a
     *                     bottom edge divider to provide a flat surface for the bottom of the border.
     *                     (The cross will only appear under the headers at this point, so underHeaders is true when emptyData is true).
     * @return The character used where horizontal and vertical borders intersect. Ex: ╪ under the headers, ╧ when table has no data, and ┼ everywhere else.
     */
    public abstract char getCross(boolean underHeaders, boolean emptyData);

}
