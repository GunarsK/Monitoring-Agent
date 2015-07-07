/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;
import static jade.GetProcess.Kernel32.OpenProcess;
import static jade.GetProcess.Kernel32.PROCESS_QUERY_INFORMATION;
import static jade.GetProcess.Kernel32.PROCESS_VM_READ;
import static jade.GetProcess.Psapi.GetModuleBaseNameW;
import static jade.GetProcess.User32DLL.GetForegroundWindow;
import static jade.GetProcess.User32DLL.GetWindowTextW;
import static jade.GetProcess.User32DLL.GetWindowThreadProcessId;

import javax.swing.JOptionPane;

/**
 *
 * @author Gunars
 */
public class GetProcess 
{
    private static final int MAX_TITLE_LENGTH = 1024;    
    
    public static String getProcess() 
    {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        
        PointerByReference pointer = new PointerByReference();
        GetWindowThreadProcessId(GetForegroundWindow(), pointer);
        Pointer process = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pointer.getValue());
        GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
        //System.out.println("Active window process: " + Native.toString(buffer));
        
        return Native.toString(buffer);
    }
    
    public static String getProcName()
    {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        //System.out.println("Active window title: " + Native.toString(buffer));
        return Native.toString(buffer);
    }

    static class Psapi 
    {
        static { Native.register("psapi"); }
        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    static class Kernel32 
    {
        static { Native.register("kernel32"); }
        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;
        public static native int GetLastError();
        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    static class User32DLL 
    {
        static { Native.register("user32"); }
        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
        public static native HWND GetForegroundWindow();
        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
    }
    
 }
