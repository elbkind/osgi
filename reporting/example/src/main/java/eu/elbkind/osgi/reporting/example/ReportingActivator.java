package eu.elbkind.osgi.reporting.example;

import java.io.PrintStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import eu.elbkind.osgi.reporting.api.ReportFormatter;
import eu.elbkind.osgi.reporting.api.Reporting;

/**
 * Activator that registers itself as {@link Reporting } component.
 *
 * @author Mark Vollmann
 *
 */
public class ReportingActivator implements BundleActivator, Reporting {

    private ServiceRegistration<Reporting> reference;

    private BundleContext bundleContext;


    @Override
    public void start(BundleContext context) throws Exception {
        this.bundleContext = context;
        this.reference = context.registerService(Reporting.class, this, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (this.reference != null && this.reference.getReference() != null) {
            this.bundleContext.ungetService(this.reference.getReference());
            ;
        }
    }

    @Override
    public String getId() {
        return "reportimg.example.bundlecontext";
    }

    @Override
    public String getDescription() {
        return "Component added via bundleContext";
    }

    @Override
    public void report(PrintStream out, PrintStream err) {
        err.println("This is an error outout\n");

        new ReportFormatter(3).format(out, new String[] { "header 1", "header 2" }, new String[][] { { "row 1-1", "row 1-2" },
                { "row 2-1", "row 2-2" } });

        err.println("\nThis is another error outout");
    }

}
