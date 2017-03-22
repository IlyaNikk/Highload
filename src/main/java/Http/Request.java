package Http;


public class Request {

    private Response response;
    private StringBuffer responseAnswer;
    private String serverDirectory ;

    public Request(String msg, String directory) {
        serverDirectory = directory;
        response = new Response(serverDirectory);
        final String[] information = msg.split("\\n");
        final String[] separateWords = information[0].split(" ");
        response.setDirectory(separateWords[1]);
        switch (separateWords[0]) {
            case "GET":
                response.methodGet();
                try {
                    response.setContentLenght();
                    response.setContentType();
                    response.setData();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case "HEAD":
                response.methodHead();
                try{
                    response.setContentLenght();
                    response.setContentType();
                } catch (Exception e){
                    System.out.println(e);
                }
                break;
            default:
                response.forbiddenMethod();
                break;
        }
        responseAnswer = response.getResponse();
    }

    public String getResponse() {
        return responseAnswer.toString();
    }

}
