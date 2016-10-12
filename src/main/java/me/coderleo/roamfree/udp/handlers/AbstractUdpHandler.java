/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.handlers;

import me.coderleo.roamfree.udp.IUdpProtocol;
import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.UdpTalkers;
import me.coderleo.roamfree.udp.packet.UdpDataPacket;

public class AbstractUdpHandler
{
    private IUdpProtocol udpProtocol;
    
    public AbstractUdpHandler(IUdpProtocol udpProtocol)
    {
        this.udpProtocol = udpProtocol;
    }
    
    public void handle(UdpDataPacket dataPacket, IUdpProtocol protocol)
    {
        if (!protocol.compatibleWith(dataPacket.getDataBytes()))
            throw new IllegalArgumentException("Protocol [" + protocol.getClass().getCanonicalName() + "] is not compatible with packet!");
        
        IUdpTalk talk = UdpTalkers.get(dataPacket.getPort());
        
        if (talk == null)
            startTalk(dataPacket);
        else if (!talk.isSyncStarted())
        {
        } else
            talk.broadcast(dataPacket.getDataBytes());
    }
    
    private IUdpTalk startTalk(UdpDataPacket dataPacket)
    {
        int port = dataPacket.getPort();
        IUdpTalk udpTalk = UdpTalkers.get(port);
        
        if (udpTalk == null)
        {
            udpTalk = HelloHandler.startTalk(udpProtocol, dataPacket);
            
            if (udpTalk != null)
            {
                UdpTalkers.put(port, udpTalk);
            }
        }
        
        return udpTalk;
    }
    
    public int getMaxPacketSize()
    {
        return udpProtocol.getMaxPacketSize();
    }
}
