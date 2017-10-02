package com.mitchtalmadge.asciidata.table.formats;

/**
 * The default, UTF-8 based table format for {@link com.mitchtalmadge.asciidata.table.ASCIITable}.
 * Uses double pipes in most cases.
 *
 * @author JakeWharton
 * @author MitchTalmadge
 */
public class UTF8TableFormat extends TableFormatAbstract {

    @Override
    public char getTopLeftCorner() {
        return '╔';
    }

    @Override
    public char getTopRightCorner() {
        return '╗';
    }

    @Override
    public char getBottomLeftCorner() {
        return '╚';
    }

    @Override
    public char getBottomRightCorner() {
        return '╝';
    }

    @Override
    public char getTopEdgeBorderDivider() {
        return '╤';
    }

    @Override
    public char getBottomEdgeBorderDivider() {
        return '╧';
    }

    @Override
    public char getLeftEdgeBorderDivider(boolean underHeaders) {
        if (underHeaders)
            return '╠';
        else
            return '╟';
    }

    @Override
    public char getRightEdgeBorderDivider(boolean underHeaders) {
        if (underHeaders)
            return '╣';
        else
            return '╢';
    }

    @Override
    public char getHorizontalBorderFill(boolean edge, boolean underHeaders) {
        if (edge || underHeaders)
            return '═';
        else
            return '─';
    }

    @Override
    public char getVerticalBorderFill(boolean edge) {
        if (edge)
            return '║';
        else
            return '│';
    }

    @Override
    public char getCross(boolean underHeaders, boolean emptyData) {
        if (underHeaders) {
            if (emptyData)
                return '╧';
            else
                return '╪';
        } else
            return '┼';
    }
}
