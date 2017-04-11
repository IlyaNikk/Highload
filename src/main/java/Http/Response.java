package Http;

import java.net.URLDecoder;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static Http.Status.getStatus;

public class Response {

    private StringBuffer responseAnswer;
    private String directory;
    private String serverDirectory;
    private final String server = "Server: Highload/1.0.0 (UNIX)\r\n";
    private Path index = null;
    private boolean fileExist = false;
    private boolean fileAbsent = false;
    private boolean text = true;
    private int status;

    public Response(String serverDirectory) {
        this.serverDirectory = serverDirectory;
        responseAnswer = new StringBuffer();
    }

    public void methodGet() {
        responseAnswer.append("HTTP/1.1 ");
        checkPath();
        if (fileExist) {
            responseAnswer.append(getStatus(200));
        }
        else if (fileAbsent){
            fileExist = false;
            responseAnswer.append(getStatus(403));
        } else {
            responseAnswer.append(getStatus(404));
        }
        setServerHeader();
        setTimeHeader();
        setConnection();
    }

    public void methodHead() {
        responseAnswer.append("HTTP/1.1 ");
        checkPath();
        if (fileExist) {
            responseAnswer.append(getStatus(200));
        } else {
            responseAnswer.append(getStatus(403));
        }
        setServerHeader();
        setTimeHeader();
        setConnection();
    }

    public void forbiddenMethod() {
        responseAnswer.append("HTTP/1.1 ");
        responseAnswer.append(getStatus(405));
        setServerHeader();
        setTimeHeader();
        setConnection();
    }

    public void setServerHeader() {
        responseAnswer.append(server);
    }

    public void setTimeHeader() {
        final Calendar mCalendar = Calendar.getInstance();
        final String dayWeek = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        final String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        int year = mCalendar.get(Calendar.YEAR);
        year = year % 100;
        responseAnswer.append("Date: " + dayWeek + ", " + day + " " + month + " " + year + " " +
                (new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()) + " GMT\r\n"));
    }

    public void setConnection() {
        responseAnswer.append("Connection: close\r\n");
    }

    public StringBuffer getResponse() {
        return responseAnswer;
    }

    public void setContentLenght() throws Exception {
        final byte[] count = Files.readAllBytes(index);
        responseAnswer.append("Content-Length: " + count.length + "\r\n");
    }

    public void setContentType() {
        final String[] parse = directory.split("\\.");
        responseAnswer.append("Content-Type: ");
        switch (parse[parse.length - 1]) {
            case "txt":
                responseAnswer.append("text/plain \r\n\r\n");
                break;
            case "html":
                responseAnswer.append("text/html \r\n\r\n");
                break;
            case "css":
                responseAnswer.append("text/css \r\n\r\n");
                break;
            case "js":
                responseAnswer.append("text/javascript \r\n\r\n");
                break;
            case "jpg":
                responseAnswer.append("image/jpeg \r\n\r\n");
                text = !text;
                break;
            case "jpeg":
                responseAnswer.append("image/jpeg \r\n\r\n");
                text = !text;
                break;
            case "gif":
                responseAnswer.append("image/gif \r\n\r\n");
                text = !text;
                break;
            case "png":
                responseAnswer.append("image/png \r\n\r\n");
                text = !text;
                break;
            case "swf":
                responseAnswer.append("application/x-shockwave-flash \r\n\r\n");
                text = !text;
                break;
        }
    }

    public void setData() throws Exception {
        if (text) {
            final List<String> lines = Files.readAllLines(index);
            for (String line : lines) {
                responseAnswer.append(line + "\n");
            }
        }
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void checkPath() {
        final String[] check = directory.split("\\.\\.\\/");
        if (check.length > 1) {
            return;
        }
        directory = directory.split("\\?")[0];
        directory = URLDecoder.decode(directory);
        index = Paths.get(serverDirectory + directory);
        if (Files.exists(index)) {
            fileExist = true;
        }
        if (Files.isDirectory(index)) {
            fileExist = false;
            fileAbsent = true;
            index = Paths.get(serverDirectory + directory, "index.html");
            if (Files.exists(index)) {
                directory = directory + "index.html";
                fileExist = true;
            }
        }
    }

    public boolean checkPicture(){
        return text;
    }

    public String getPath(){
        try{
            return index.toString();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public int checkStatus(){
        return status;
    }
}
