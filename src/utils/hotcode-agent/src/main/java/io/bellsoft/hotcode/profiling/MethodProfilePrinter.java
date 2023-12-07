package io.bellsoft.hotcode.profiling;

import java.io.OutputStream;
import java.io.PrintWriter;

public class MethodProfilePrinter {
    private final static String ROW_SEP = "-".repeat(120);
    private final static String HEADER = String.format("| %-7s | %-9s | %-94s |", "COUNT", "%", "METHOD");
    private final static String ROW_FMT = "| %7d | %9.2f | %-94s |";
    
    private final PrintWriter printWriter;
    
    public MethodProfilePrinter(OutputStream output) {
        printWriter = new PrintWriter(output);
    }
    
    public void print(Profile<Method> profile, int topK) {
        printWriter.println(ROW_SEP);
        printWriter.println(HEADER);
        printWriter.println(ROW_SEP);

        for (var m : profile.getTop(topK)) {
            int count = profile.occurrences(m);
            float ratio = 100.0f * count / profile.total();
            printWriter.println(String.format(ROW_FMT, count, ratio, m.signature()));
        }

        printWriter.println(ROW_SEP);
        printWriter.flush();
    }
}
