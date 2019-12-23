import java.io.Serializable;

public class ChatData implements Serializable {
    String name;
    String message;

    public ChatData(String message){
        this.message=message;
    }

    public ChatData(String name, String message){
        this.name=name;
        this.message=message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}