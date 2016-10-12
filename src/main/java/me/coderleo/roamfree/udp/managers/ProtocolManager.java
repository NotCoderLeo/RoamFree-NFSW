/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.managers;

import com.google.common.base.Preconditions;
import me.coderleo.roamfree.udp.IUdpProtocol;
import me.coderleo.roamfree.udp.handlers.AbstractUdpHandler;
import me.coderleo.roamfree.udp.packet.UdpDataPacket;

import java.util.LinkedList;
import java.util.List;

public class ProtocolManager
{
    private static ProtocolManager ourInstance = new ProtocolManager();
    
    public static ProtocolManager getInstance()
    {
        return ourInstance;
    }
    
    private final List<IUdpProtocol> protocols;
    private AbstractUdpHandler handler;
    
    private ProtocolManager()
    {
        protocols = new LinkedList<>();
    }
    
    public List<IUdpProtocol> getProtocols()
    {
        return protocols;
    }
    
    public void handlePacket(UdpDataPacket packet)
    {
        protocols.stream().filter(p -> p.compatibleWith(packet.getDataBytes())).forEach(p -> handle(packet, p));
    }
    
    private void handle(UdpDataPacket packet, IUdpProtocol protocol)
    {
        Preconditions.checkNotNull(handler, "Handler is null!");
        
        handler.handle(packet, protocol);
    }
}
