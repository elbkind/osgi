# OSGI Tools

## Reporting

* **reporting/api** Offers an interface to write report data to a printstream
* **reporting/karaf** Offers two commands to list all available reports and to call a report
* **reporting/example** Shows a basic example for the reporting api

### Reporting interface
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


### Command reporting:list
	karaf@root> reporting:list  

	List of active reports  
	-----------------------  

	ID                              | Dexcription  
	--------------------------------+----------------------------------  
	reportimg.example.bundlecontext | Component added via bundleContext  


### Command reporting:report <report-id>
	karaf@root> reporting:report reportimg.example.bundlecontext  
	Report [reportimg.example.bundlecontext]  
	This is an error outout  

	header 1 | header 2  
	---------+---------  
	row 1-1  | row 1-2  
	row 2-1  | row 2-2  

	This is another error outout  