import org.apache.commons.cli.*;
import server.Server;

/**
 * Created by ilya on 3/4/17.
 */
public class main {

    private static String dir = ".";
    private static int port = 8080;
    private static int threadsNumber = 4;

    public static void main(String[] args) throws InterruptedException {
        parseCommandLine(args);

        new Server(dir, port, threadsNumber).run();
    }

    public static void parseCommandLine(String[] args) {
        final Options option = new Options();

        option.addOption("r", true, "directory");
        option.addOption("c", true, "Threads Number");
        option.addOption("p", true, "Port");

        final CommandLineParser params = new PosixParser();
        final CommandLine cmd;
        try {
            cmd = params.parse(option, args);
        } catch (ParseException e) {
            return;
        }

        final String parseDir = cmd.getOptionValue("r");
        if(parseDir != null){
            dir = parseDir;
        }

        String parseNumber = cmd.getOptionValue("c");
        if(parseNumber != null){
            threadsNumber = Integer.parseInt(parseNumber);
        }

        parseNumber = cmd.getOptionValue("p");
        if(parseNumber != null){
            port = Integer.parseInt(parseNumber);
        }
    }
}
