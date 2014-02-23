package eu.elbkind.osgi.reporting.commands;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import eu.elbkind.osgi.reporting.api.ReportFormatter;
import eu.elbkind.osgi.reporting.api.Reporting;

/**
 * Lists all registered components with the {@link Reporting} interface.
 *
 * @author Mark Vollmann
 */
@Command(scope = "${reporting.scope}", name = "list", description = "List all reports.")
public class ReportingListCommand extends OsgiCommandSupport {

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
        out.println("\nList of active reports");
        out.println("-----------------------\n");

        Set<Reporting> reports = new TreeSet<>(new Comparator<Reporting>() {

            @Override
            public int compare(Reporting value, Reporting compare) {
                return value.getId().compareTo(compare.getId());
            }

        });

        Collection<ServiceReference<Reporting>> result = this.bundleContext.getServiceReferences(Reporting.class, null);
        for (ServiceReference<Reporting> reference : result) {
            Reporting monitor = this.bundleContext.getService(reference);
            if (monitor != null) {
                reports.add(monitor);
            }
        }

        String[] headers = new String[] { "ID", "Dexcription" };
        List<String[]> rows = new ArrayList<>(reports.size());
        for (Reporting reporter : reports) {
            rows.add(new String[] { reporter.getId(), reporter.getDescription() });
        }

        new ReportFormatter().format(System.out, headers, rows.toArray(new String[rows.size()][]));
        out.println();
        out.println();

        return null;
    }
}
