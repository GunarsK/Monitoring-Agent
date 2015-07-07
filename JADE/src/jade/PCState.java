/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade;

/**
 *
 * @author Gunars
 */
import java.util.Arrays;
import java.util.List;

import com.sun.jna.*;
import com.sun.jna.win32.*;

/**
  * klase, kas nosaka, vai ar datoru notiek aktīvas darbības
  * @author ochafik
  */
public class PCState {
     
    // iegūst sistēmas stāvokli atkarībā no pēdējās ievades(peles vai klaviatūras darbības)
     public static State getState() 
     {
         if (!System.getProperty("os.name").contains("Windows")) {
             System.err.println("ERROR: Only implemented on Windows");
             System.exit(1);
         }
         State state = State.UNKNOWN;

             int idleSec = getIdleTime() / 1000;
             State newState = null;
             
             if(idleSec < 30)
             {
                newState = State.ONLINE;
             }
             else
             {
                 if(idleSec > 5 * 60)
                 {
                    newState = State.AWAY;
                 }
                 else
                 {
                     newState = State.IDLE;
                 }
             }
             if (newState != state) {
                 state = newState;               
             }
             return state;
     }
    
    public interface Kernel32 extends StdCallLibrary {
         Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class);

         /**
          * Iegūst laiku milisekundēs, kopš sistēma sāka strādāt
          * @see http://msdn2.microsoft.com/en-us/library/ms724408.aspx
          */
         public int GetTickCount();
     };

     public interface User32 extends StdCallLibrary {
         User32 INSTANCE = (User32)Native.loadLibrary("user32", User32.class);
         /**
          * Satur pēdējā ievades notikuma laiku
          * @skatīt http://msdn.microsoft.com/library/default.asp?url=/library/en-us/winui/winui/windowsuserinterface/userinput/keyboardinput/keyboardinputreference/keyboardinputstructures/lastinputinfo.asp
          */
         public static class LASTINPUTINFO extends Structure {
             public int cbSize = 8;

             public int dwTime;

            @SuppressWarnings("rawtypes")
            @Override
            protected List getFieldOrder() {
                return Arrays.asList(new String[] { "cbSize", "dwTime" });
            }
         }
         //iegūst pēdējā ievades notikuma laiku
         public boolean GetLastInputInfo(LASTINPUTINFO result);
     };

      //iegūst milisekundes, kas pagājušas kopš pēdējās aktivitātes(peles vai klaviatūras) ar datoru
     public static int getIdleTime() {
         User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
         User32.INSTANCE.GetLastInputInfo(lastInputInfo);
         return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
     }

     //saraksts, kas norāda uz patreizējo datora stāvokli
     enum State {
         UNKNOWN, ONLINE, IDLE, AWAY
     };
}
