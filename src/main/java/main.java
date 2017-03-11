import org.apache.commons.cli.*;
import server.Server;

/**
 * Created by ilya on 3/4/17.
 */
public class main {

    private static String dir;
    private static final int Port = 8080;
    private static int cpuNumber = 0;

    public static void main(String[] args) throws InterruptedException {
        parseCommandLine(args);

        System.out.println("LOLOLoloollolllol");
        new Server(dir, Port, cpuNumber).run();
    }

    public static void parseCommandLine(String[] args) {
        Options option = new Options();

        option.addOption("r", true, "directory");
        option.addOption("c", true, "CPU Number");

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

        final String parseNumber = cmd.getOptionValue("c");
        if(parseNumber != null){
            cpuNumber = Integer.parseInt(parseNumber);
        }
    }
}
