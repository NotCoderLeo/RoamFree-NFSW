/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp;

import me.coderleo.roamfree.udp.packet.UdpDataPacket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpWriter
{
    private int port;
    
    private InetAddress address;
    
    private DatagramSocket serverSocket;
    
    private UdpDataPacket dataPacket;
    
    public UdpWriter(UdpDataPacket dataPacket)
    {
        this.dataPacket = dataPacket;
        address = dataPacket.getAddress();
        port = dataPacket.getPort();
        serverSocket = dataPacket.getServerSocket();
    }
    
    public void sendPacket(byte[] dataPacket)
    {
        this.dataPacket.replaceDataBytes(dataPacket);
        this.sendPacket(this.dataPacket);
    }
    
    public void sendPacket(UdpDataPacket dataPacket)
    {
        try
        {
            byte[] sendData = dataPacket.getDataBytes();
//            UdpDebug.debugSendPacket(dataPacket);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
            serverSocket.send(sendPacket);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public int getPort()
    {
        return port;
    }
    
    public InetAddress getAddress()
    {
        return address;
    }
    
}