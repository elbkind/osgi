package eu.elbkind.osgi.reporting.commands;

import java.util.Collection;
import java.util.List;

import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import eu.elbkind.osgi.reporting.api.Reporting;


public class ReportingCompleter implements Completer {

    private BundleContext bundleContext;

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public int complete(String buffer, int cursor, List<String> candidates) {
        try {
            StringsCompleter delegate = new StringsCompleter();
            Collection<ServiceReference<Reporting>> result = this.bundleContext.getServiceReferences(Reporting.class, null);
            for (ServiceReference<Reporting> reference : result) {
                Reporting reporter = this.bundleContext.getService(reference);
                if(reporter != null) {
                    delegate.getStrings().add(reporter.getId());
                }
            }
            return delegate.complete(buffer, cursor, candidates);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
