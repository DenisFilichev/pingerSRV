/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingersrv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denis
 */
public class PingerSRV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        while (true){
            try (ServerSocket srvSocket = new ServerSocket(7000)){
                Socket socket = srvSocket.accept();
                SrvAgent srvagent = new SrvAgent();
                srvagent.setSocket(socket);
                Thread agent = new Thread(srvagent);
                agent.start();
            } catch (IOException ex) {
                Logger.getLogger(PingerSRV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
