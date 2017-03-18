package Http;

import io.netty.channel.ChannelFuture;

/**
 * Created by ilya on 3/15/17.
 */
public class Request {

    private Response response;
    private StringBuffer responseAnswer;

    public Request(String msg) {
        response = new Response();
        System.out.println("in request message:");
        System.out.println(msg);
        final String[] information = msg.split("\\n");
        for(String line: information){
            final String[] separateWords = line.split(" ");
            switch(separateWords[0]){
                case "GET":
                    response.methodGet();
                    try{
                        response.setContentLenght(separateWords[1]);
                        response.setContentType();
                        response.setData(separateWords[1]);
                    } catch (Exception e){
                        System.out.println(e);
                    }
                    responseAnswer = response.getResponse();
                    break;
                case "HEAD" :
                    response.methodHead();
                    responseAnswer = response.getResponse();
                    break;
            }
        }
        System.out.println(responseAnswer);
   }

   public String getResponse(){
       return responseAnswer.toString();
   }

}
