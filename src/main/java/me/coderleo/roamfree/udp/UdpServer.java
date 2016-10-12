/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp;

import me.coderleo.roamfree.udp.handlers.AbstractUdpHandler;
import me.coderleo.roamfree.udp.managers.ProtocolManager;
import me.coderleo.roamfree.udp.packet.UdpDataPacket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Represents an instance of a UDP server.
 * <p>
 * Handles packets and accepts connections from NFSW.
 *
 * @author leorblx, original by Nilzao.
 */
public class UdpServer
{
    private DatagramSocket serverSocket;
    private int port;
    private AbstractUdpHandler handler;
    
    public UdpServer(int port, IUdpProtocol protocol)
    {
        this.port = port;
        handler = new AbstractUdpHandler(protocol);
    }
    
    public void start()
    {
        try
        {
            serverSocket = new DatagramSocket(port);
            
            new UdpSrvReceive(serverSocket, handler).start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public DatagramSocket getServerSocket()
    {
        return serverSocket;
    }
    
    private static class UdpSrvReceive extends Thread
    {
        private DatagramSocket serverSocket;
        private AbstractUdpHandler udpHandler;
        
        UdpSrvReceive(DatagramSocket serverSocket, AbstractUdpHandler udpHandler)
        {
            this.serverSocket = serverSocket;
            this.udpHandler = udpHandler;
        }
        
        public void run()
        {
            int packetMaxSize = udpHandler.getMaxPacketSize();
            byte[] receiveData = new byte[packetMaxSize];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try
            {
                while (true)
                {
                    serverSocket.receive(receivePacket);
                    UdpDataPacket dataPacket = new UdpDataPacket(receivePacket, serverSocket);
//                    UdpDebug.debugReceivePacket(dataPacket);
                    UdpDebugger.debugReceivePacket(dataPacket);
                    ProtocolManager.getInstance().handlePacket(dataPacket);
//                    udpHandler.handle(dataPacket);
                    for (int i = 0; i < receiveData.length; i++)
                    {
                        receiveData[i] = 0;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                serverSocket.close();
            }
        }
    }
}
