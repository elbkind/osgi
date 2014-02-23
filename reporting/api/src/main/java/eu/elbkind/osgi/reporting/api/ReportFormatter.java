package eu.elbkind.osgi.reporting.api;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Utility class to ease tabular output. Example:<br/>
 * columns1   | column2 | column...<br/>
 * -----------+---------+--------  <br/>
 * row1 col1  | col2    |......    <br/>
 * row2 col1  | col2    |......
 *
 *
 * @author Mark Vollmann
 *
 */
public class ReportFormatter {

    private static final String[] BLANKS;
    private static final String[] SEPS;

    static {
        int len = 100;
        BLANKS = new String[len];
        SEPS = new String[len];
        BLANKS[0] = "";
        SEPS[0] = "";
        for (int i = 1; i < 100; i++) {
            BLANKS[i] = BLANKS[i - 1] + " ";
            SEPS[i] = SEPS[i - 1] + "-";
        }
    }

    private final int defaultColumnWidth;


    public ReportFormatter() {
        this(10);
    }

    public ReportFormatter(int defaultColumnSize) {
        this.defaultColumnWidth = defaultColumnSize;
    }

    /**
     * Prints a table
     *
     * @param out PrintStream to write to, not null
     * @param headers Array of not null values
     * @param rows Array of not null values, row length must always be headers length
     */
    public void format(PrintStream out, String[] headers, String[]... rows) {
        int[] columnWidth = computeEachColumnWidth(this.defaultColumnWidth, headers, rows);

        printRow(out, columnWidth, headers);
        printTableSeperatir(out, headers, columnWidth);

        for (String[] row : rows) {
            printRow(out, columnWidth, row);
        }
    }

    private void printTableSeperatir(PrintStream out, String[] headers, int[] columnWidth) {
        String[] tableSeperator = new String[headers.length];
        for(int i=0;i< columnWidth.length;i++) {
            tableSeperator[i] = SEPS[columnWidth[i]];
        }
        printRow(out, "-+-", columnWidth, tableSeperator);
    }

    private int[] computeEachColumnWidth(int defaultWidth, String[] headers, String[]... rows) {
        int[] result = new int[headers.length];
        Arrays.fill(result, this.defaultColumnWidth);

        for (int i = 0; i < headers.length; i++) {
            result[i] = Math.max(result[i], headers[i].length());

            for (String[] row : rows) {
                if (row.length != headers.length) {
                    throw new IllegalArgumentException(String.format("Length %s of row %s differs from header length %s ", i,
                            row.length, headers.length));
                }
                result[i] = Math.max(result[i], row[i].length());
            }
        }
        return result;
    }

    private void printRow(PrintStream out, int[] columnWidth, String... columns) {
        printRow(out, " | ", columnWidth, columns);
    }

    private void printRow(PrintStream out, String sep, int[] columnWidth, String... columns) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i< columnWidth.length;i++) {
            String entry = expandStringUpTo(columnWidth[i], columns[i]);
            builder.append(sep).append(entry);
        }

        out.println(builder.substring(sep.length()));
    }

    private String expandStringUpTo(int len, String in) {
        int diff = len - in.length();
        if (diff == 0) {
            return in;
        }

        return in + BLANKS[diff];
    }
}
