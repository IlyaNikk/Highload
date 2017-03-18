package Http;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ilya on 3/15/17.
 */
public class Response {

    private StringBuffer responseAnswer;
    private String server = "Server: Highload/1.0.0 (UNIX)\n";

    public Response(){
        responseAnswer = new StringBuffer();
    }

    public void methodGet(){
        System.out.println("Get Response");
        responseAnswer.append("HTTP/1.1 200 OK \n");
        setServerHeader();
        setTimeHeader();
        setConnection();
    }

    public void methodHead(){
        System.out.println("Head Response") ;
        responseAnswer.append("HTTP/1.1 200 OK \n");
        setServerHeader();
        setTimeHeader();
        setConnection();
    }

    public void setServerHeader(){
        responseAnswer.append(server);
    }

    public void setTimeHeader(){
        final Calendar mCalendar = Calendar.getInstance();
        final String dayWeek = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        final String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        final int year = mCalendar.get(Calendar.YEAR);
        responseAnswer.append("Date: " + dayWeek + ", " + day + " " + month + " " + year +  " " +
                (new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()) + " GMT\n"));
    }

    public void setConnection(){
        responseAnswer.append("Connection: close\n");
    }

    public StringBuffer getResponse(){
        return responseAnswer;
    }

    public void setContentLenght(String directory)throws Exception{
        final Path index = Paths.get('.' + directory,"index.html");
        final byte[] count = Files.readAllBytes(index);
        responseAnswer.append("Content-Length: " + count.length + "\n");
    }

    public void setContentType(){
        responseAnswer.append("Content-Type: text/html; charset=utf-8 \n");
    }

    public void setData(String directory) throws Exception{
        final Path index = Paths.get('.' + directory,"index.html");
        List<String> lines = Files.readAllLines(index);
        responseAnswer.append("\n\n");
        for (String line: lines ) {
            responseAnswer.append(line + "\n");
        }
    }
}
