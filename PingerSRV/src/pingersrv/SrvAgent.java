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
    
    @Override
    public void run (){
        try (ServerSocket srvSocket = new ServerSocket(7000)){
            socket = srvSocket.accept();
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            waitClient(oos, ois);
        } catch (IOException ex) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(SrvAgent.class.getName()).log(Level.SEVERE, null, ex1);
            }
            run();
        }
    }
    
    public void waitClient (ObjectOutputStream oos, ObjectInputStream ois){
        SrvClient srvclient;
        while (true){
            srvclient = new SrvClient(oos, ois);
            Thread client = new Thread(srvclient);
            client.start();
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
