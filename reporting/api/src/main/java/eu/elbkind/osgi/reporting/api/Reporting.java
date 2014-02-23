package eu.elbkind.osgi.reporting.api;

import java.io.PrintStream;

/**
 * Reporting interface used by karaf commands
 *
 * @author Mark Vollmann
 */
public interface Reporting {

    /**
     * Unique id of the reporting instance. It is strongly advised to use a dot seperated it like my.package.my.class
     *
     * @return Not null
     */
    String getId();

    /**
     * Short description of what this instance does
     *
     * @return Not null
     */
    String getDescription();

    /**
     * Callback method. Reports usually get written to the out stream, in error cases to the error stream.
     *
     * The methods {@link PrintStream#print(String)} and methods using this method will produce colored output.
     *
     * @param out
     *            PrintStream for normal output
     * @param err
     *            PrintStream for error messages
     */
    void report(PrintStream out, PrintStream err);
}
