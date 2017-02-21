package extern;

/**
 * A simple server to run on a Raspberry Pi that serves manymouse information to client.
 * 
 * Original description:
 *  Test app to demonstrate usage of the Java bindings.
 *  Please see the file LICENSE.txt in the source's root directory.
 *  This file written by Ryan C. Gordon.
 *  
 *  Edited by Michael Wolf, FRC Team 5066 "Singularity"
 */

//TODO switch from using += to build strings to StringBuilder

import java.io.*;
import java.net.*;

public class ServerManyMouse
{
    //netowrking constants
    public static final int PORT = 9999;
    
    //singularity variabls
    static final int SLEEP_TIME = 5;
    static long startTime;
    static long eventTime;
    static String currentln;
    static String result;
    
    //manymouse variables
    static ManyMouseEvent event;
    static int mice;
    
    public static void main(String args[])
    {
        mice = ManyMouse.Init();
        System.out.println("ManyMouse.Init() reported " + mice + ".");
        for (int i = 0; i < mice; i++)
            System.out.println("Mouse #" + i + ": " + ManyMouse.DeviceName(i));
        System.out.println();

        // Allocate one that PollEvent fills in so we aren't spamming the
        //  memory manager with throwaway objects for each event.
        event = new ManyMouseEvent();
        
        //Setup server
        
        ServerSocket mouseServer = null;
        String line;
        BufferedReader is;
        PrintWriter os;
        Socket clientSocket = null;
        
        //Setup timestamps
        startTime = System.nanoTime();
        eventTime = System.nanoTime();
        
        //init current line variable (used for message concatenation process)
        currentln = "";
        result = "";
        
        //Try to open server on port 9999
        try{
            mouseServer = new ServerSocket(PORT);
        }
        catch(IOException e) {
            System.out.println(e);
        }
        System.out.println("Server created on port " + PORT);
        
        //Create socket object to listen and accept a connection, then open input and output streams
        System.out.println("Waiting for socket connection...");
        try{
            clientSocket = mouseServer.accept();
            System.out.println("Attempting to open io streams");
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintWriter(clientSocket.getOutputStream(), false);
            
            System.out.println("Streams opened.");
            
            os.println("Hello client");
            
            //Mouse data gathering and sending loop
            while(mice > 0) { // report events until process is killed.
                String res = mouseLoop();
                if(res!=null){
                    System.out.print(res);
                    os.print(res); 
                    os.flush();
                }
            }
            ManyMouse.Quit();
            
            
        } catch(IOException e){
            System.out.println(e);
        }
    }
    
    public static String mouseLoop() {
        if (!ManyMouse.PollEvent(event)){ //sleep if no event found
            try { Thread.sleep(SLEEP_TIME); } catch (InterruptedException e) {}
            return null;
        }
        else //if event found
        {
            eventTime = (System.nanoTime() - startTime) / 1000;
            result = "";
            
            do{ //repeat this code, then repeat again if ManyMouse.PollEvent(event) is still true
                
                currentln = "";
                
                currentln += "Mouse #";
                currentln += event.device;
                currentln += " ";
                

                switch (event.type)
                {
                    case ManyMouseEvent.ABSMOTION:
                        System.out.print("absolute motion ");
                        if (event.item == 0) // x axis
                            System.out.print("X axis ");
                        else if (event.item == 1) // y axis
                            System.out.print("Y axis ");
                        else
                            System.out.print("? axis ");  // error?
                        System.out.print(event.value);
                        break;

                    case ManyMouseEvent.RELMOTION:
                        currentln += "relative motion ";
                        if (event.item == 0) // x axis
                            currentln += "X axis ";
                        else if (event.item == 1) // y axis
                            currentln += "Y axis ";
                        else
                            currentln += "? axis ";  // error?
                        currentln += event.value;
                        break;

                    case ManyMouseEvent.BUTTON:
                        currentln += "mouse button ";
                        currentln += event.item;
                        if (event.value == 0)
                            currentln += " up";
                        else
                            currentln += " down";
                        break;

                    case ManyMouseEvent.SCROLL:
                        currentln += "scroll wheel ";
                        if (event.item == 0)
                        {
                            if (event.value > 0)
                                currentln += "up";
                            else
                                currentln += "down";
                        }
                        else
                        {
                            if (event.value > 0)
                                currentln += "right";
                            else
                                currentln += "left";
                        }
                        
                        break;

                    case ManyMouseEvent.DISCONNECT:
                        currentln += "disconnected";
                        mice--;
                        break;

                    default:
                        System.out.print("Unknown event: ");
                        System.out.print(event.type);
                        break;
                } // switch
                currentln += " " + eventTime + " ms\n";
                result += currentln;
                //System.out.print(currentln);
            } while(ManyMouse.PollEvent(event));
            return result;
        } // if
    } 
} // TestManyMouse

// end of TestManyMouse.java ...


