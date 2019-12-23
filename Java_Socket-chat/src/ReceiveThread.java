import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ReceiveThread extends Thread {
    private Socket socket;

    public ReceiveThread(Socket socket){
        this.socket=socket;
    }

    public void run(){
        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());

            while(true){
                Object object =objectInputStream.readObject(); 
                SendObject(object);
            }
        }catch (Exception e){
            e.printStackTrace();
            UserList.list.remove(this.socket);
        }
    }

    public void SendObject(Object object){
        try {
            for (int i = 0; i < UserList.list.size(); i++) {
                //if(UserList.list.get(i).equals(socket)) continue;
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(UserList.list.get(i).getOutputStream());
                objectOutputStream.writeObject(object);  
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
