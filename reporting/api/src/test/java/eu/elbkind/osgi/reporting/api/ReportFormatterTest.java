package eu.elbkind.osgi.reporting.api;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.Assert;

import org.junit.Test;


public class ReportFormatterTest {

    @Test
    public void test() {
        ReportFormatter formater = new ReportFormatter(7);
        String[] headers = new String[] {"longerThan7", "shorter", "anither"};
        String[][] rows = new String[][] {{"row 1-1", "row 1-2", "row 1-3"}};

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer);

        formater.format(out, headers, rows);
        out.close();

        String result = new String(buffer.toByteArray());

        String expected = "longerThan7 | shorter | anither\n"//
                        + "------------+---------+--------\n"//
                        + "row 1-1     | row 1-2 | row 1-3\n";

        Assert.assertEquals(expected, result);
    }

}
