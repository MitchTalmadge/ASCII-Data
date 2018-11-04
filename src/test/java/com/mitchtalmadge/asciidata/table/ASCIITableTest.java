package com.mitchtalmadge.asciidata.table;

import com.mitchtalmadge.asciidata.TestUtils;
import com.mitchtalmadge.asciidata.table.formats.ASCIITableFormat;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ASCIITableTest {

    /**
     * Tests a simple table with example data.
     */
    @Test
    public void testSimpleTable() throws IOException {

        String[] headers = new String[]{"ID", "Name", "Email"};
        String[][] data = new String[][]{
                {"123", "Alfred Alan", "aalan@gmail.com"},
                {"223", "Alison Smart", "asmart@gmail.com"},
                {"256", "Ben Bessel", "benb@outlook.com"},
                {"374", "John Roberts", "johnrob@company.com"},
        };

        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/utf8/simpleTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).toString())
        );
        // ASCII Table Format
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/ascii/simpleTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).withTableFormat(new ASCIITableFormat()).toString())
        );

    }


    /**
     * Tests tables with no data.
     */
    @Test
    public void testEmptyTables() throws IOException {
        String[] headers = new String[]{"ID", "Name", "Email"};
        String[][] emptyData = new String[0][0];

        // Not null data, but empty
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/utf8/emptyTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, emptyData).toString())
        );
        // ASCII Table Format
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/ascii/emptyTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, emptyData).withTableFormat(new ASCIITableFormat()).toString())
        );

        // Null data
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/utf8/emptyTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, null).toString())
        );
        // ASCII Table Format
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/ascii/emptyTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, null).withTableFormat(new ASCIITableFormat()).toString())
        );
    }

    /**
     * Tests a table with too few columns in the data array.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotEnoughColumns() throws IOException {

        String[] headers = new String[]{"ID", "Name", "Email"};
        String[][] badData = new String[][]{
                {"123", "Alfred Alan", "aalan@gmail.com"},
                {"223", "Alison Smart", "asmart@gmail.com"},
                {"256", "Ben Bessel"}, // Missing column
                {"374", "John Roberts", "johnrob@company.com"},
        };

        ASCIITable.fromData(headers, badData);
    }

    /**
     * Tests a table with newlines in the data.
     */
    @Test
    public void testMultipleLines() throws IOException {

        String[] headers = new String[]{"ID", "Name", "Email"};
        String[][] data = new String[][]{
                {"123", "Alfred\nAlan", "aalan@gmail.com"},
                {"223", "Alison\nSmart", "asmart@gmail.com"},
                {"256", "Ben\nBessel", "benb@outlook.com"},
                {"374", "John\nRoberts", "johnrob@company.com"},
        };

        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/utf8/multiLineTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).toString())
        );
        // ASCII Table Format
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/ascii/multiLineTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).withTableFormat(new ASCIITableFormat()).toString())
        );
    }

    /**
     * Tests a table which has other tables nested within it.
     */
    @Test
    public void testNestedTables() throws IOException {

        // Construction
        String[] nestedHeaders = new String[]{"First", "Last"};
        String[] names = new String[]{"Alfred Alan", "Alison Smart", "Ben Bessel", "John Roberts"};
        ASCIITable[] nestedTables = new ASCIITable[4];
        ASCIITable[] nestedTablesASCIIFormat = new ASCIITable[4];
        for (int i = 0; i < names.length; i++) {
            nestedTables[i] = ASCIITable.fromData(nestedHeaders, new String[][]{names[i].split(" ")});
            nestedTablesASCIIFormat[i] = ASCIITable.fromData(nestedHeaders, new String[][]{names[i].split(" ")}).withTableFormat(new ASCIITableFormat());
        }

        // Insertion
        String[] headers = new String[]{"ID", "Name", "Email"};
        String[][] data = new String[][]{
                {"123", nestedTables[0].toString(), "aalan@gmail.com"},
                {"223", nestedTables[1].toString(), "asmart@gmail.com"},
                {"256", nestedTables[2].toString(), "benb@outlook.com"},
                {"374", nestedTables[3].toString(), "johnrob@company.com"},
        };
        String[][] dataASCIIFormat = new String[][]{
                {"123", nestedTablesASCIIFormat[0].toString(), "aalan@gmail.com"},
                {"223", nestedTablesASCIIFormat[1].toString(), "asmart@gmail.com"},
                {"256", nestedTablesASCIIFormat[2].toString(), "benb@outlook.com"},
                {"374", nestedTablesASCIIFormat[3].toString(), "johnrob@company.com"},
        };

        // Testing
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/utf8/nestedTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).toString())
        );
        // ASCII Table Format
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/ascii/nestedTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, dataASCIIFormat).withTableFormat(new ASCIITableFormat()).toString())
        );

    }


    /**
     * Tests a table with custom null data.
     */
    @Test
    public void testNullDataTablesWithCustomNullValue() throws IOException {

        String[] headers = new String[]{"ID", "Name", "Email"};
        String[][] data = new String[][]{
                {"123", "Alfred Alan", "aalan@gmail.com"},
                {"223", null, "asmart@gmail.com"},
                {"256", "Ben Bessel", "benb@outlook.com"},
                {"374", "John Roberts", "johnrob@company.com"},
        };

        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/utf8/nullDataTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).toString())
        );
        // ASCII Table Format
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/ascii/customNullDataTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).withNullValue("n/a").withTableFormat(new ASCIITableFormat()).toString())
        );

    }

    /**
     * Tests a table with default null data.
     */
    @Test
    public void testNullDataTablesWithDefaultNullValue() throws IOException {

        String[] headers = new String[]{"ID", "Name", "Email"};
        String[][] data = new String[][]{
                {"123", "Alfred Alan", "aalan@gmail.com"},
                {"223", null, "asmart@gmail.com"},
                {"256", "Ben Bessel", "benb@outlook.com"},
                {"374", "John Roberts", "johnrob@company.com"},
        };

        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/utf8/nullDataTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).toString())
        );
        // ASCII Table Format
        assertEquals(
                TestUtils.commonizeLineEndings(TestUtils.readFileToString("tables/ascii/customNullDataTable.txt")),
                TestUtils.commonizeLineEndings(ASCIITable.fromData(headers, data).withNullValue("n/a").withTableFormat(new ASCIITableFormat()).toString())
        );

    }
}