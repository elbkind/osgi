package eu.elbkind.osgi.reporting.commands;

import java.io.PrintStream;
import java.util.Collection;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import eu.elbkind.osgi.reporting.api.Reporting;

/**
 * Queries the specified report.
 *
 * @author Mark Vollmann
 *
 */
@Command(scope = "${reporting.scope}", name = "report", description = "Dunp report.")
public class ReportingDetailCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "reporter", description = "The name of all reporting instances", required = true, multiValued = true)
    private String name;

    private BundleContext bundleContext;

    private String colorOut;
    private String colorErr;


    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public void setColorOut(String colorOut) {
        this.colorOut = String.format("\u001B[%sm", colorOut);
    }

    public void setColorErr(String colorErr) {
        this.colorErr = String.format("\u001B[%sm", colorErr);
    }

    @Override
    protected Object doExecute() throws Exception {
        PrintStream out = new ColoredPrintStream(System.out, this.colorOut);
        PrintStream err = new ColoredPrintStream(System.err, this.colorErr);
        report(out, err, this.bundleContext.getServiceReferences(Reporting.class, null));

        return null;
    }

    void report(PrintStream out, PrintStream err, Collection<ServiceReference<Reporting>> result) throws InvalidSyntaxException {
        out.print(this.colorOut);
        err.print(this.colorErr);
        Reporting target = null;

        if (this.name != null) {
            String lookupName = this.name.substring(1, this.name.length() - 1);
            for (ServiceReference<Reporting> reference : result) {
                Reporting current = this.bundleContext.getService(reference);
                if (current != null) {
                    if (lookupName != null && lookupName.equals(current.getId())) {
                        target = current;
                        break;
                    }
                }
            }
        }
        if (target != null) {
            out.printf("Report %s\n", this.name);
            target.report(out, err);
        } else {
            err.printf("Could not get reference to report named [%s]\n", this.name);
        }

        out.println();
        out.println();
    }

}
