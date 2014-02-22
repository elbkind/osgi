package eu.elbkind.osgi.reporting.example;

import java.io.PrintStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import eu.elbkind.osgi.reporting.api.Reporting;

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
        if(this.reference != null && this.reference.getReference() != null) {
            this.bundleContext.ungetService(this.reference.getReference());;
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
        out.println("This is a reportable registered via bundlecontext");
        err.println("This is an error outout");
    }



}