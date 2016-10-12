/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree;

import me.coderleo.roamfree.udp.IUdpProtocol;
import me.coderleo.roamfree.udp.UdpDebugger;
import me.coderleo.roamfree.udp.UdpServer;
import me.coderleo.roamfree.udp.protocols.nfsw.freeroam.FreeroamProtocol;
import me.coderleo.roamfree.util.Log;

public class Main
{
    public static void main(String[] args)
    {
        Log.setup();
        
        int port = 9998;
        String protocol = FreeroamProtocol.class.getCanonicalName();
        
        if (args.length > 0 && args.length != 2)
        {
            Log.error("Usage: java -jar <path to jar> <port> <full protocol class name>");
            System.exit(1);
        } else
        {
            port = new Integer(args[0]);
            protocol = args[1];
        }
        
        UdpDebugger.startDebugging();
        Class<?> protocolObj;
        
        try
        {
            protocolObj = Class.forName(protocol);
            IUdpProtocol udpProtocol = (IUdpProtocol) protocolObj.newInstance();
            UdpServer server = new UdpServer(port, udpProtocol);
            
            server.start();
        } catch (ClassNotFoundException e)
        {
            Log.error("Could not find protocol class [%s].", protocol);
            Log.error(e.getMessage());
            System.exit(1);
        } catch (InstantiationException | IllegalAccessException e)
        {
            Log.error(e.getMessage());
            System.exit(1);
        }
        
        Log.info("");
        Log.info("RoamFree UDP Server\n");
        Log.info("Nilzao's UDP server, rewritten by Leo <3\n");
        Log.info("java -jar roamfree-udp.jar <port> <full protocol class name>");
        Log.info("Example:");
        Log.info("  java -jar roamfree-udp.jar 9001 me.coderleo.roamfree.udp.protocols.nfsw.race.RaceProtocol");
        Log.info("  java -jar roamfree-udp.jar 9001 me.coderleo.roamfree.udp.protocols.nfsw.freeroam.FreeroamProtocol");
        Log.info("");
        Log.info("Listening on port %s with protocol %s! GOGOGO!",
                port,
                protocol);
        Log.info("");
    }
}
