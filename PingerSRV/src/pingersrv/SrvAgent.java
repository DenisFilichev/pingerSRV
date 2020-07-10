/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingersrv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denis
 */
public class SrvAgent implements Runnable {
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    SrvClient srvClient;
    OIS oisRead;
    
    public void getSrvClient (SrvClient srvClient){
        this.srvClient = srvClient;
    }
    
    public void setSocket (Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run (){
        try (Socket socket = this.socket;
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())
                ){
            while (true){
                System.out.println("Ждем клиента");
                try (ServerSocket srvSocket = new ServerSocket(7001)){
                Socket socketclient = srvSocket.accept();
                    System.out.println("Клиент подключился");
                SrvClient srvclient = new SrvClient(socketclient, oos, ois);
                Thread client = new Thread(srvclient);
                client.start();
                }
            }
        } catch (IOException ex) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(SrvAgent.class.getName()).log(Level.SEVERE, null, ex1);
            }
            //run();
        }
    }
    
    /*public void ping (Object ip, BufferedWriter oosToClient){
        try (ObjectOutputStream oos = this.oos;
            ObjectInputStream ois = this.ois){
            oos.writeObject((String) ip);
            String values = "";
            while ((values = (String)ois.readObject()) != null){
                oosToClient.write(values);
                System.out.println("Отправка успешна");
            }
        } catch (IOException ex) {
            System.out.println("Связь разорвана");
            run();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SrvAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}

class OIS {
    BufferedReader ois;
    
    public OIS (BufferedReader ois){
        this.ois = ois;
    }
    
    /*public Object read () throws IOException, ClassNotFoundException{
        Object o = (Object)ois.readLine();
        return o;
    }*/
}
