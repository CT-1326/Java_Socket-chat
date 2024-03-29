import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ReceiveData extends Thread {
    public void run(){
        try {
            if(!SocketData.getInstance().isConnected()){
                System.out.println("\u001B[1m"+"\u001B[31m" + "[Error] ?��버�? ?��결되?��?���??��?��?��?��." + "\u001B[0m");
                return;
            }

            InputStream inputStream = SocketData.getInstance().getSocket().getInputStream();
            while(true){
                ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
                Object object=objectInputStream.readObject();  

                if(object instanceof PaintData){
                    PaintData paintData=(PaintData)object;

                    if (paintData.getSize() == -10) {
                        PaintCanvas.getInstance().clearBackground();
                    } else if (paintData.getSize() > 0) {
                        PaintCanvas.getInstance().addPaint(paintData);
                    }
                }
                else if(object instanceof ChatData){
                    UserInterface.getInstance().AddChat((ChatData)object);
                }
                
            }
            //inputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
