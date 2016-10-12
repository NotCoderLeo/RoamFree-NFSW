/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.packet;

import lombok.Data;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Data
public class UdpDataPacket
{
    private final DatagramPacket receivePacket;
    private final DatagramSocket serverSocket;
    private byte[] dataBytes;
    
    public UdpDataPacket(DatagramPacket receivePacket, DatagramSocket serverSocket)
    {
        this.receivePacket = receivePacket;
        this.serverSocket = serverSocket;
        int length = receivePacket.getLength();
        dataBytes = new byte[length];
        System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), dataBytes, 0, receivePacket.getLength());
    }
    
    public void replaceDataBytes(byte[] newData)
    {
        dataBytes = newData;
    }
    
    public byte[] getDataBytes()
    {
        return dataBytes;
    }
    
    public String getDataString()
    {
        return new String(getDataBytes());
    }
    
    public int getPort()
    {
        return receivePacket.getPort();
    }
    
    public InetAddress getAddress()
    {
        return receivePacket.getAddress();
    }
}
