package Http;

public class Request {

    private Response response;
    private StringBuffer responseAnswer;
    private String serverDirectory ;
    private String filePath;
    private boolean picture;

    public Request(String msg, String directory) {
        serverDirectory = directory;
        response = new Response(serverDirectory);
        final String[] information = msg.split("\\n");
        final String[] separateWords = information[0].split(" ");
        response.setDirectory(separateWords[1]);
        switch (separateWords[0]) {
            case "GET":
                response.methodGet();
                if(response.checkStatus() <= 300) {
                    try {
                        response.setContentLenght();
                        response.setContentType();
                        response.setData();
                    } catch (Exception e) {}
                }
                break;
            case "HEAD":
                response.methodHead();
                if(response.checkStatus() <= 300) {
                    try {
                        response.setContentLenght();
                        response.setContentType();
                    } catch (Exception e) {}
                }
                break;
            default:
                response.forbiddenMethod();
                break;
        }
        responseAnswer = response.getResponse();
        picture = response.checkPicture();
        filePath = response.getPath();
    }

    public String getResponse() {
        return responseAnswer.toString();
    }

    public boolean checkPicture(){
        return picture;
    }

    public String getPath(){
        return filePath;
    }
}
