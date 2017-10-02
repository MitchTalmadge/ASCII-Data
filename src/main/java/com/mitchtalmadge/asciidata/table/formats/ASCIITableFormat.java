package com.mitchtalmadge.asciidata.table.formats;

/**
 * An ASCIITable Format using only ASCII characters.
 *
 * @author bitsofinfo
 * @author MitchTalmadge
 */
public class ASCIITableFormat extends TableFormatAbstract {

    @Override
    public char getTopLeftCorner() {
        return '+';
    }

    @Override
    public char getTopRightCorner() {
        return '+';
    }

    @Override
    public char getBottomLeftCorner() {
        return '+';
    }

    @Override
    public char getBottomRightCorner() {
        return '+';
    }

    @Override
    public char getTopEdgeBorderDivider() {
        return '+';
    }

    @Override
    public char getBottomEdgeBorderDivider() {
        return '+';
    }

    @Override
    public char getLeftEdgeBorderDivider(boolean underHeaders) {
        return '|';
    }

    @Override
    public char getRightEdgeBorderDivider(boolean underHeaders) {
        return '|';
    }

    @Override
    public char getHorizontalBorderFill(boolean edge, boolean underHeaders) {
        if (edge || underHeaders)
            return '=';
        else
            return '-';
    }

    @Override
    public char getVerticalBorderFill(boolean edge) {
        return '|';
    }

    @Override
    public char getCross(boolean underHeaders, boolean emptyData) {
        if (emptyData)
            return '=';
        else
            return '|';
    }
}
