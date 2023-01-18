import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
  
    ArrayList<String> words = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "use /add or /search";
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            String list = "Search Results: ";
            if(parameters[0].equals("s")){
                for(int i=0;i<words.size();i++){
                    if(words.get(i).contains(parameters[1])){
                        list = list+words.get(i)+", ";
                    }
                }
                return list;
            }else{
                return "Missing search term";
            }
                
        } else if (url.getPath().equals("/add")){
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")){
                words.add(parameters[1]);
                return parameters[1] + " added to list";
            }else{
                return "Missing term to add";
            }
        } else {
           
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
