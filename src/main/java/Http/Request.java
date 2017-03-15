package Http;

/**
 * Created by ilya on 3/15/17.
 */
public class Request {

    private String[] allowedMethods = {"GET", "HEAD"};

    public Request(String msg) {
        System.out.println("in request message:");
        System.out.println(msg);
        final String[] information = msg.split("\\n");
        for (int i = 0; i < information.length; i++) {
            System.out.println(information[i] + '\n');
            switch(i){
                case 0:
                    checkFirstLine(information[i]);
                    break;

            }
        }
    }

    public void checkFirstLine(String line){
        boolean methodCheck = false;
        final String[] separateWords = line.split(" ");
        for(String method: allowedMethods){
            if(method.equals(separateWords[0])){
                methodCheck = true;
            }
        }
        if(methodCheck) {
            for (String word : separateWords) {
                System.out.println(word + '\n');
            }
            if (separateWords[0].equals("GET")) {
                new Response().methodGet();
            } else if (separateWords[0].equals("HEAD")) {
                new Response().methodHead();
            }
        }
    }

}
