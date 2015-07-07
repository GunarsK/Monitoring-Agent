/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade;

import jade.PCState.State;
import jade.core.Agent;
import jade.core.behaviours.*;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Gunars
 */

//Aģenta klase
public class MonitoringAgent extends Agent
{
    public static int onlineTime = 0;
    //setup() metode inicializē aģentu. Metode tiek mantota no Agent klases, tāpēc tiek pārrakstīta
    @Override
    protected void setup() 
    {
        //pieslēgšanās datubāzei
        DBOperations.connectDB();
        //lietotāja grafiskā interfeisa inicializācija
        getGUI();
        //aģents izpilda norādītās darbības ik pēc sekundes(1000ms)
        addBehaviour(new TickerBehaviour(this, 1000) 
        {
           @Override
            protected void onTick() 
                {
                State state =  PCState.getState();
                if(!(state == State.AWAY))
                    {
                        onlineTime++;
                        DBOperations.insertDB(GetProcess.getProcess(), GetProcess.getProcName());
                        DBOperations.checkRestrictions();
                                        
                        if(onlineTime > 60*60)
                            {
                                JFrame frame = new rest_jForm();
                                frame.setVisible(true);
                            }      
                    }
                else
                    {
                        onlineTime = 0;
                    }
                }
           } );
    }
    
    // Metode, kas izpilda norādītas darbības aģenta darbības pārtrukšanas gadījumā.
    @Override
    protected void takeDown() 
    {
        DBOperations.closeDB();
        //System.out.println("Monitoring agent "+getAID().getName()+" terminating.");
    }
    
    //Grafiskā lietotāja interfeisa inicializācija
    private void getGUI()
    {
        JFrame frame = new mainForm();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Your Monitoring Agent");
        String icoPath = "./images/eye.gif";
        ImageIcon img = new ImageIcon(icoPath);
        
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final TrayIcon trayIcon = new TrayIcon(img.getImage());
        final SystemTray tray = SystemTray.getSystemTray();
       
       trayIcon.addMouseListener(new MouseAdapter() 
       {
            public void mouseClicked(MouseEvent e) { 
                frame.setVisible(true);
            }
       }); 
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "TrayIcon could not be added.", "Alert", JOptionPane.ERROR_MESSAGE);
            }
    }
}
