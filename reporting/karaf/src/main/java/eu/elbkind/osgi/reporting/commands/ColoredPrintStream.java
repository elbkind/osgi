package eu.elbkind.osgi.reporting.commands;

import java.io.OutputStream;
import java.io.PrintStream;


public class ColoredPrintStream extends PrintStream{

    private final String color;

    public ColoredPrintStream(OutputStream out, String color) {
        super(out);
        this.color = color;
    }

    @Override
    public void print(String s) {
        super.print(this.color);
        super.print(s);
    }


}
