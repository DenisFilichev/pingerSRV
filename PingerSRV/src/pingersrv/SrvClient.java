/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingersrv;

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
public class SrvClient implements Runnable {
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ObjectOutputStream oosAgent;
    ObjectInputStream oisAgent;
    
    public SrvClient (ObjectOutputStream oosAgent, ObjectInputStream oisAgent){
        this.oosAgent = oosAgent;
        this.oisAgent = oisAgent;
    }
    
    public void run (){
        try (ServerSocket srvSocket = new ServerSocket(7001)){
            socket = srvSocket.accept();
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            ping();
        } catch (IOException ex) {}
    }
    
    private void ping (){
        try (ObjectOutputStream oos = this.oos;
                ObjectInputStream ois = this.ois){
            String ip;
            String line;
            while (true){
                ip = (String)ois.readObject();
                oosAgent.writeObject(ip);
                oosAgent.flush();
                line = "";
                while (!(line.equals("END"))) {// reading output stream of the command
                    line = (String)oisAgent.readObject();
                    oos.writeObject(line);
                    oos.flush();
                }
            }
            
            /*Object ip = (Object)ois.readObject();
            System.out.println("IP адрес получен");
            srvAgent.ping(ip, oos);
            System.out.println("IP отправлен в SrvAgent");*/
            
            /*oos.writeObject(ip);
                System.out.println("IP отправлен агенту");
                while (true){
                    line = (String)ois.readObject();
                    System.out.println(line);
                }*/
            } catch (IOException ex) {
            System.out.println("Связь разорвана");
            run();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SrvClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
