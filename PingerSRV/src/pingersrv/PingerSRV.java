/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingersrv;

/**
 *
 * @author denis
 */
public class PingerSRV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SrvAgent srvagent = new SrvAgent();
        Thread agent = new Thread(srvagent);
        agent.start();
    }
    
}
