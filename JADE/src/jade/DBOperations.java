/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
/**
 *
 * @author Gunars
 */

//Klase DBOperations satur visas ar datubāzi saistītās darbības
//mainīgie: connect - savienojums ar datubāzi,
//          currentDate - pašreizējais datums
public class DBOperations {
    public static Connection connect = null;
    public static  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public static Date date = new Date(); 
    public static String currentDate = dateFormat.format(date);
    
 //processList klase satur infromāciju par procesiem
    public static class processList
    {
        String processName;
        int timeCount_day;
        String Sys_date;
        String processType;
        int timeCount_all;

        private processList(String name, int countday,String sysdate, String type, int countall) {
            processName = name;
            timeCount_day = countday;
            Sys_date = sysdate;
            processType = type;
            timeCount_all = countall;
        }
    }
      
//typeList klase satur informāciju par tipiem    
     public static class typeList
    {
        int ID;
        String typeName;
        int typeTimeCount_day;
        String Sys_date;
        int typeTimeCount_all;

        private typeList(int type_id, String name, int countday,String sysdate, int countall) {
            ID = type_id;
            typeName = name;
            typeTimeCount_day = countday; 
            Sys_date = sysdate;
            typeTimeCount_all = countall;
        }
        
        private typeList( String name, int countday,String sysdate, int countall) {            
            typeName = name;
            typeTimeCount_day = countday; 
            Sys_date = sysdate;
            typeTimeCount_all = countall;
        }
    }

// restrictionsList satur informāciju par ierobežojumiem     
     public static class restrictionsList
    {
        int ID;
        String type_name;
        int limit;
        int used;
        Boolean isUsed;
        
        private restrictionsList(int type_id, String type, int limit_day, int used_day, Boolean is_used) {
            ID = type_id;
            type_name = type;
            limit = limit_day;
            used = used_day;
            isUsed = is_used;
        }
        
        private restrictionsList( String type, int limit_day,int used_day, Boolean is_used) {            
            type_name = type;
            limit = limit_day;
            used = used_day;
            isUsed = is_used;
        }
    }
     
//connectDB metode veido savienojumu ar datubāzi                
    public static void connectDB() 
{
    try {
      Class.forName("org.sqlite.JDBC");
      connect = DriverManager.getConnection("jdbc:sqlite:DB/agent.db");
   
    } catch ( Exception e ) {
      JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      //System.exit(0);
    }
    //System.out.println("Opened database successfully");
}

    //checkDate metode veic pārbaudi, vai mainījies sistēmas datums, un, ja mainījies, tad veic nepieciešamās izmaiņas datubāzē
    private static void checkDate()
    {
        Boolean hasChanged = false;
        try{
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM PROCESS WHERE Sys_date <> '"+currentDate+"';" );
                    
            while ( rs.next() ) 
            {         
            int count = rs.getInt("COUNT(*)");
            
            if (count!=0) 
                { 
                hasChanged = true;
                }
            else
            {
                hasChanged = false;
            }
            }
            stmt.close();
            if (hasChanged == true)
            {
                String receiver = "";
                receiver = getReceiverInfo();
                
                //Ja mainījies datums, tiek nosūtīts e-pasts uz norādīto adresi un tiek veiktas sekojošas izmaiņas
                //datubāzē: tekošo dienu skaitītāji nodzēsti, atjaunota informācija par sistēmas datumu un tiek 
                //mainīta norāde, ka ierobežojumi vēl nav pārpildīti
                if (receiver != null || receiver !="")
                {
                SendEmail.send(receiver);
                }
                stmt.executeUpdate("UPDATE PROCESS SET Count_Day = 0, Sys_date = '"+currentDate+"'");
                stmt.executeUpdate("UPDATE PROCESS_TYPE SET Type_Count_Day = 0, Type_Sys_date = '"+currentDate+"'");
                stmt.executeUpdate("UPDATE RESTRICTIONS SET Used = 0");
                stmt.close();
            }
        }
        catch ( Exception e ) 
        {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }    
    }
    
    //metode insertDB ievieto nolasītos datus datubāzē.
    //processName - aktīvā procesa nosaukums - xxx.exe
    //FullProcName - procesa pilnais nosaukums, izmanto, lai noteiktu interneta adreses no web pārlūka
    public static void insertDB(String processName, String FullProcName)
    {
    try{
        checkDate();
        //Pārbaude, vai aktīvais process ir kāds no interneta pārlūkiem
        if(processName.equals("firefox.exe") || processName.equals("chrome.exe") || processName.equals("iexplore.exe"))
        {
            Boolean added = false;
            List<String> pagesList = getPagesList();
            for (String pagename : pagesList) {
                if(Pattern.compile(Pattern.quote(pagename), Pattern.CASE_INSENSITIVE).matcher(FullProcName).find())
                    {
                        processName = processName + " - " + pagename;
                        added = true;
                    }
            }
            if(added == false)
            {
                processName = processName + " - " + "Cits";
            }
        }
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM PROCESS;" );
        Boolean ifExists = false;
        
        while ( rs.next() ) {         
            String name = rs.getString("NAME");
            if (name == null ? processName == null : name.equals(processName)) { ifExists = true; }
            
        }
        rs.close();
        
        //Ja konkrētais process jau ir datubāzē, tad tā laika skaitītāji tiek palielināti
        if (ifExists == true)
        {
            stmt.executeUpdate("UPDATE PROCESS SET Count_Day=Count_Day+1, Count_All=Count_All+1 where NAME='"+processName+"';");
        }
        //Ja procesa nav datubāzē, tad datubāzē tiek ievietots jauns ieraksts
        else
        {
            if(processName.length()>1)
            {
                stmt.executeUpdate("INSERT INTO PROCESS(NAME, Count_Day, Count_All, Sys_date) VALUES ('"+processName+"',1,1,'"+currentDate+"');");
            }
        }
    
    stmt.close();    
    rs.close();
    
    updateType();
    //System.out.println("Insert in db successfull");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }    
    }   
    
    //metode closeDB atbild par datubāzes savienojuma aizvēršanu
    public static void closeDB()
    {
        try 
        {
            connect.close();
            //System.out.println("DB closed");
        } 
        catch ( Exception e ) 
        {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }    
    }
    
    //metode getProcessList atgriež sarakstu, ar visiem procesiem un to datiem, kas reģistrēti datubāzē
    public static List getProcessList()
    {
    List<processList> proctypeList = new ArrayList<>();
    try
    {
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM PROCESS INNER JOIN PROCESS_TYPE WHERE PROCESS.TYPE_ID = PROCESS_TYPE.PT_ID ORDER BY PROCESS.Count_Day DESC;" );
       
        while ( rs.next() ) 
        {         
            String name = rs.getString("NAME");
            int count_day = rs.getInt("Count_Day");
            String currDate = rs.getString("Sys_date");
            String type = rs.getString("Type_Name");
            int count_all = rs.getInt("Count_All");
            
            processList proctype = new processList(name,count_day,currDate,type,count_all);
            proctypeList.add(proctype);
           
            //System.out.println( "NAME = " + name + " Count = " +count+ " Type:" + type);
        }
        
        stmt.close();    
        rs.close(); 
        
        //System.out.println("Getting process type completed");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    return proctypeList;
    }
    
    //metode deleteProcess izdzēš visu informāciju no datubāzes Process tabulas.
    public static void deleteProcess()
    {
    try
    {
        Statement stmt = connect.createStatement();
        
        stmt.executeUpdate("DELETE FROM PROCESS;");
        stmt.close();
        
        //System.out.println("Process type changed");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode changeProcessType ļauj mainīt katra procesa norādīto tipu
    //processName - procesa nosaukums
    //tName - jaunā tipa nosaukums
    public static void changeProcessType(String processName, String tName )
    {
    try
    {
        List<typeList> typeList = new ArrayList<>();
        typeList = getTypeList();
        Statement stmt = connect.createStatement();
        
        for (DBOperations.typeList type : typeList) {
            if(type.typeName.equals(tName)){
        stmt.executeUpdate("UPDATE PROCESS SET Type_ID='"+type.ID+"' where NAME='"+processName+"';");
            }
        }
        stmt.close();
        
        //System.out.println("Process type changed");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode addType nodrošina jaunu tipu pievienošanu
    //typeName - tipa nosaukums
    public static void addType(String typeName)
    {
    try
    {        
        Statement stmt = connect.createStatement();
        stmt.executeUpdate("INSERT INTO PROCESS_TYPE(Type_Name, Type_Sys_date) VALUES ('"+typeName+"', '"+currentDate+"');");
        
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode deleteType nodrošina tipa dzēšanu no datubāzes. Tipu "Cits" nevar dzēst
    //typeName - tipa nosaukums
    public static void deleteType(String typeName)
    {
    try
    {      
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM PROCESS INNER JOIN PROCESS_TYPE WHERE PROCESS.TYPE_ID = PROCESS_TYPE.PT_ID ORDER BY PROCESS.Count_Day DESC;" );
       
        while ( rs.next() ) 
        {         
            String name = rs.getString("NAME");
            String type = rs.getString("Type_Name");
            
            if (type.equals(typeName))
            {
                Statement stmt2 = connect.createStatement();
                stmt2.executeUpdate("UPDATE PROCESS SET Type_ID=1 where NAME='"+name+"';");
                stmt2.close();
            }
        }
        if(!typeName.equals("Cits"))
        {
        stmt.executeUpdate("DELETE from PROCESS_TYPE where Type_Name='"+typeName+"';");
        }
        stmt.close();
        
        //System.out.println("Type deleted successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode getTypeList atgriež sarakstu ar informāciju par visiem tipiem
    public static List getTypeList()
    {
    List<typeList> typeList = new ArrayList<>();
    try
    {        
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM PROCESS_TYPE ORDER BY Type_Count_Day DESC;" );
        
        while ( rs.next() ) 
        {
            int type_id = rs.getInt("PT_ID");
            String name = rs.getString("TYPE_NAME");
            int count_day = rs.getInt("TYPE_COUNT_DAY"); 
            int count_all = rs.getInt("TYPE_COUNT_ALL"); 
            String currDate = rs.getString("Type_Sys_date");
            typeList type = new typeList(type_id, name,count_day,currDate,count_all);
            typeList.add(type);
            
            //System.out.println( "TYPE NAME = " + name + " Type Count = " +count);
        }
        
        rs.close();
        stmt.close();
        
        //System.out.println("Getting type list completed");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    return typeList;
    }
    
    //metode updateType atjauno informāciju par tipiem. 
    //Nosaka tipiem norādīto procesu laikus un tos sasummējot ieraksta Process_Type tabulā
     public static void updateType()
    {
    try
    {        
        Statement stmt = connect.createStatement();
        
        ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) FROM PROCESS_TYPE;" );
        int counter = 0;
        //while ( rs.next() ) 
        //{   
            counter = rs.getInt("COUNT(*)");
        //}
        rs.close();
        
        rs = stmt.executeQuery( "SELECT  Type_ID, SUM(COUNT_DAY), SUM(COUNT_ALL) FROM PROCESS GROUP BY Type_ID;" );
        int[] id = new int[counter];
        int[] count_day = new int[counter];
        int[] count_all = new int[counter];
        int i = 0;
        while ( rs.next() ) 
        {   
            
            id[i] = rs.getInt("Type_ID");
            count_day[i] = rs.getInt("SUM(COUNT_DAY)"); 
            count_all[i] = rs.getInt("SUM(COUNT_ALL)"); 
            i++;
        }
        rs.close();
        stmt.executeUpdate("UPDATE PROCESS_TYPE SET Type_Count_Day=0, Type_Count_All=0;");
        for(int k = 0; k<counter;k++)
        {
        stmt.executeUpdate("UPDATE PROCESS_TYPE SET Type_Count_Day = '"+count_day[k]+"',Type_Count_All = '"+count_all[k]+"' WHERE PROCESS_TYPE.PT_ID = '"+id[k]+"';");
        }
        
        stmt.close();
        
        //System.out.println("Type count updated");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
     
     //metode addRestriction nodrošina ierobežojumu pievienošanu
     //typeID - tipa ID, kam paredzēts ierobežojums
     //time - ierobežojuma laiks
    public static void addRestriction(int typeID, int time)
    {
    try
    {   
        if(time > 0 )
        {
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM RESTRICTIONS;" );
        
        Boolean hasRestr = false;
        while ( rs.next() ) 
        {
            if(rs.getInt("Type_ID") == typeID)
            {
                hasRestr = true;
            }
        }
        if(hasRestr == false)
        {
        stmt.executeUpdate("INSERT INTO RESTRICTIONS(Type_ID, Time, Used) VALUES ('"+typeID+"', '"+time+"', 0);");
        }
        else
        {
       // stmt.executeUpdate("DELETE from RESTRICTIONS where Type_ID='"+typeID+"';");
       // stmt.executeUpdate("INSERT INTO RESTRICTIONS(Type_ID, Time) VALUES ('"+typeID+"', '"+time+"');");
        stmt.executeUpdate("UPDATE RESTRICTIONS SET Time='"+time+"', Used=0 WHERE Type_ID='"+typeID+"';");
        }
        stmt.close();
        }
        else
        {
           JOptionPane.showMessageDialog(null, "Nederīgs ierobežojuma laiks", "Error", JOptionPane.ERROR_MESSAGE); 
        }
        //System.out.println("Restriction added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
     
    //metode deleteRestriction nodrošina ierobežojumu dzēšanu
    //restr_id - dzēšamā ierobežojuma id
    public static void deleteRestriction(int restr_id)
    {
    try
    {      
        Statement stmt = connect.createStatement();        
        stmt.executeUpdate("DELETE FROM RESTRICTIONS where ID='"+restr_id+"';");        
        stmt.close();
        
        //System.out.println("Restriction deleted successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    } 

    //metode getRestrictions nodrošina ierobežojumu saraksta izgūšanu no datubāzes
    public static List getRestrictions()
    {
    List<restrictionsList> restrList = new ArrayList<>();
    try
    {        
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT R.ID, PT.Type_Name, R.Time , PT.Type_count_day, R.Used FROM RESTRICTIONS R INNER JOIN PROCESS_TYPE PT WHERE R.Type_ID = PT.PT_ID;" );
        
        while ( rs.next() ) 
        {
            int restr_id = rs.getInt("ID");
            String type_name = rs.getString("Type_Name");
            int max_time = rs.getInt("Time"); 
            int used_time = rs.getInt("Type_count_day");
            Boolean is_used = rs.getBoolean("Used");
            restrictionsList list = new restrictionsList(restr_id,type_name,max_time,used_time, is_used);
            restrList.add(list);
            
            //System.out.println( "TYPE NAME = " + name + " Type Count = " +count);
        }        
        rs.close();
        stmt.close();        
        //System.out.println("Getting type list completed");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    return restrList;
    }
     
    //metode checkRestrictions nodrošina ierobežojumu izpildes pārbaudi
    //Gadījumā, ja ierobežojums tiek sasniegts, tiek izvadīts brīdinājuma paziņojums
    public static void checkRestrictions()
    {
        List<restrictionsList> restrList = new ArrayList<>();
        restrList = getRestrictions();
        try{
        for (restrictionsList restriction : restrList) {
            if(restriction.used>= restriction.limit && restriction.isUsed !=true && restriction.limit>0)
            {
            Statement stmt = connect.createStatement();        
            stmt.executeUpdate("UPDATE RESTRICTIONS SET Used=1 where ID='"+restriction.ID+"';");
            stmt.close();
            JDialog dialog = new restrictionWarning(null, false,restriction.type_name, restriction.limit);
            dialog.setVisible(true);
            }
        }
        }
        catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    } 
    }
    
    //metode assUserinfo nodrošina e-pasta pievienošanu, uz kuru tiks sūtīti datora lietošanas dati katras dienas beigās.
    //receiver - saņēmēja e-pasta adrese
    public static void addUserinfo(String receiver)
    {
    try
    {        
        Statement stmt = connect.createStatement();
        deleteUserInfo();
        stmt.executeUpdate("INSERT INTO User_info(Receiver) VALUES ('"+receiver+"');");
        
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode deleteUserInfo nodrošina e-pasta adreses dzēšanu
    public static void deleteUserInfo()
    {
    try
    {        
        Statement stmt = connect.createStatement();
        stmt.executeUpdate("DELETE FROM User_Info");
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode getReceiverInfo nodrošina saņēmēja e-pasta adreses izgūšanu no datubāzes
    public static String getReceiverInfo()
    {
    String receiver = "";
    try
    {   
        
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery("Select * FROM User_Info");
        
        while(rs.next())
        {
            receiver = rs.getString("Receiver");
        }
        
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    return receiver;
    }
    
    //metode getPagesList nodrošina pievienotot weblapu saraksta izgūšanu no datubāzes
    public static List getPagesList()
    {    
    List<String> pagesList = new ArrayList<>();
    try
    {   
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery("Select * FROM Webpage");
        
        while(rs.next())
        {
            pagesList.add(rs.getString("Name"));
        }
        
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    return pagesList;
    }
    
    //metode addPage nodrošina weblapas nosaukuma pievienošanu
    //name - weblapas nosaukums
    public static void addPage(String name)
    {    
    try
    {  
        Statement stmt = connect.createStatement();
        stmt.executeUpdate("INSERT INTO Webpage(Name) VALUES ('"+name+"');");
        
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode deletePages nodrošina weblapas nosaukuma dzēšanu no datubāzes
    //name - weblapas nosaukums
    public static void deletePage(String name)
    {    
    try
    {  
        Statement stmt = connect.createStatement();
        stmt.executeUpdate("DELETE FROM Webpage Where Name = '"+name+"';");
        
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    }
    
    //metode getTotalTime nodrošina pie datora kopējā pavadītā laika iegūšanu
    public static int getTotalTime()
    {    
    int time = 0;
    try
    {  
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT SUM(Count_All) FROM PROCESS;");
        time = rs.getInt("SUM(Count_All)");
        rs.close();
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    return time;
    }
    
    //metode getTotalTime nodrošina pie datora šodien pavadītā laika iegūšanu
     public static int getTotalTimeToday()
    {    
    int time = 0;
    try
    {  
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT SUM(Count_Day) FROM PROCESS;");
        time = rs.getInt("SUM(Count_Day)");
        rs.close();
        stmt.close();
        //System.out.println("Type added successfully");
    }
    catch ( Exception e ) 
    {
        JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //System.exit(0);
    }  
    return time;
    }
   
    //metode getTimeFormat nodrošina sekunžu parvēršanu laika formātā HH:MM:SS
    //seconds - sekundes
    public static String getTimeFormat( int seconds)
    {
        String time = null;
        
        int hr = seconds/3600;
        int rem = seconds%3600;
        int mn = rem/60;
        int sec = rem%60;
        String hrStr = (hr<10 ? "0" : "")+hr;
        String mnStr = (mn<10 ? "0" : "")+mn;
        String secStr = (sec<10 ? "0" : "")+sec; 
        
        time = hrStr + ":" + mnStr + ":" + secStr;
        return time;
    }
    
    //metode getTimeFormat nodrošina sekunžu parvēršanu laika formātā HH:MM:SS
    //seconds - sekundes
    public static int getSeconds(int hh, int mm, int ss)
    {
        int seconds = 0;
        seconds = hh*3600+mm*60+ss;
        return seconds;
    }
    
}
