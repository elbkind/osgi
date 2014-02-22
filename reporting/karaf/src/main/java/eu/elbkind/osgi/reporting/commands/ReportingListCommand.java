package eu.elbkind.osgi.reporting.commands;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import eu.elbkind.osgi.reporting.api.Reporting;

@Command(scope = "${reporting.scope}", name = "list", description = "List all monitors.")
public class ReportingListCommand extends OsgiCommandSupport {

    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    protected Object doExecute() throws Exception {
        System.out.println("\n\nList of active reports");
        System.out.println("-----------------------");

        Set<Reporting> monitors = new TreeSet<>(new Comparator<Reporting>() {

            @Override
            public int compare(Reporting value, Reporting compare) {
                return value.getId().compareTo(compare.getId());
            }

        });

        Collection<ServiceReference<Reporting>> result = this.bundleContext.getServiceReferences(Reporting.class, null);
        for (ServiceReference<Reporting> reference : result) {
            Reporting monitor = this.bundleContext.getService(reference);
            if (monitor != null) {
                monitors.add(monitor);
            }
        }

        for(Reporting monitor : monitors) {
            System.out.println(monitor.getId());
        }
        System.out.println();
        System.out.println();

        return null;
    }
}
