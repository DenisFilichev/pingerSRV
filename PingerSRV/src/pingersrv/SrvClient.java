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
    
    public SrvClient (Socket socket, ObjectOutputStream oosAgent, ObjectInputStream oisAgent){
        this.socket = socket;
        this.oosAgent = oosAgent;
        this.oisAgent = oisAgent;
        System.out.println("Экземпляр клиента создан");
    }
    
    @Override
    public void run (){
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ){
            System.out.println("Потоки ввода вывода созданы");
            String ip;
            String line;
            while (true){
                System.out.println("Ждем IP от клиента");
                ip = (String)ois.readObject();
                oosAgent.writeObject(ip);
                line = "";
                while (!(line.equals("END"))) {// reading output stream of the command
                    line = (String)oisAgent.readObject();
                    oos.writeObject(line);
                }
            }
        } catch (IOException ex) {} catch (ClassNotFoundException ex) {
            Logger.getLogger(SrvClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
