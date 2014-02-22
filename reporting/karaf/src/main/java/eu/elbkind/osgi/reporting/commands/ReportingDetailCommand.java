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

@Command(scope = "${reporting.scope}", name = "report", description = "Dunp report.")
public class ReportingDetailCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "reporter", description = "The name of all reporting instances", required = true, multiValued = true)
    private String name;

    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    protected Object doExecute() throws Exception {
        PrintStream out = System.out;
        PrintStream err = System.err;
        report(out, err, this.bundleContext.getServiceReferences(Reporting.class, null));

        return null;
    }

    void report(PrintStream out, PrintStream err, Collection<ServiceReference<Reporting>> result) throws InvalidSyntaxException {
        out.println();
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
            out.printf("Monitor %s\n", this.name);
            target.report(out, err);
        } else {
            err.printf("Could not get reference to monitor named [%s]\n", this.name);
        }

        out.println();
        out.println();
    }

}
