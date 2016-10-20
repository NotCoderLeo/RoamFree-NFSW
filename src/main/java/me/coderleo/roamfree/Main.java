/*
 * RoamFree UDP Server
 *
 * Written by leorblx (Original UDP server by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree;

import me.coderleo.roamfree.util.Log;

public class Main
{
    public static void main(String[] args)
    {
        Log.setup();
        
        int port = 9998;
        
        if (args.length > 0 && args.length != 1)
        {
            Log.error("Usage: java -jar <path to jar> <port>");
            System.exit(1);
        } else if (args.length > 0)
        {
            try
            {
                port = new Integer(args[0]);
                
                if (port < 1025)
                {
                    Log.error("No system ports.");
                    System.exit(1);
                    return;
                }
            } catch (NumberFormatException e)
            {
                Log.error("Invalid port. Ports must be integers.");
                System.exit(1);
            }
        }
        
        Log.info("");
        Log.info("RoamFree UDP Server\n");
        Log.info("Nilzao's UDP server, rewritten by leorblx\n");
        Log.info("java -jar roamfree-udp.jar <port>");
        Log.info("Example:");
        Log.info("  java -jar roamfree-udp.jar 9001");
        Log.info("");
        Log.info("Listening on port %s with all protocols! GOGOGO!", port);
        Log.info("");
    }
}
