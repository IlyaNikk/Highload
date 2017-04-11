package Http;

public class Status {

    static public String getStatus(int status) {
        switch (status) {
            case 403:
                return "403 Forbidden\r\n";
            case 404:
                return "404 Not Found\r\n";
            case 405:
                return "405 Method Not Allowed\r\n";
        }
        return "200 OK\r\n";
    }


}
