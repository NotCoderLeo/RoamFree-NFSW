/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp;

import me.coderleo.roamfree.udp.packet.UdpDataPacket;
import me.coderleo.roamfree.util.HexUtil;
import me.coderleo.roamfree.util.Log;

public class UdpDebugger
{
    private static boolean isDebugging = false;
    
    public static void startDebugging()
    {
        isDebugging = true;
        Log.info("Now debugging UDP packets!");
    }
    
    public static void stopDebugging()
    {
        isDebugging = false;
        Log.info("No longer debugging UDP packets.");
    }
    
    public static void debugReceivePacket(UdpDataPacket packet)
    {
        if (isDebugging)
        {
            if (isReadablePackets(packet))
            {
                String str = packet.getDataString();
                boolean probablyFreeroam = str.contains("01:01:01:01:");
                
                Log.debug("From [%s]: [%s] | %s freeroam%s", packet.getPort(), str.trim(), probablyFreeroam ? "Probably" : "",
                        probablyFreeroam ? "?" : "");
                byte[] hex2Byte = HexUtil.hexStringToByteArray(str);
                packet.replaceDataBytes(hex2Byte);
            } else
            {
                Log.debug("From [%s]: [%s] (Unreadable)",
                        packet.getPort(),
                        HexUtil.byteArrayToHexString(packet.getDataBytes()));
            }
        }
    }
    
    public static void debugSendPacket(UdpDataPacket dataPacket)
    {
        if (isDebugging)
        {
            byte[] dataBytes = dataPacket.getDataBytes();
            if (isReadablePackets(dataBytes))
            {
                String string = new String(dataBytes);
                Log.debug("Sending: [" + string.trim() + "]");
            } else
            {
                String byteArrayToHexString = "from-srv: {" + HexUtil.byteArrayToHexString(dataBytes) + "}\n";
                Log.debug("Sending: [" + byteArrayToHexString.trim() + "]");
                dataPacket.replaceDataBytes(byteArrayToHexString.getBytes());
            }
        }
    }
    
    private static boolean isReadablePackets(UdpDataPacket dataPacket)
    {
        return isReadablePackets(dataPacket.getDataBytes());
    }
    
    private static boolean isReadablePackets(byte[] dataBytes)
    {
        if (dataBytes.length > 1)
        {
            if (dataBytes[1] < 0x20)
            {
                return false;
            }
        }
        
        return true;
    }
}
