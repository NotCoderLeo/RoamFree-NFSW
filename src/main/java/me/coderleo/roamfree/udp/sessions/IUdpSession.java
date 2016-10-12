/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.sessions;

import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.impl.UdpTalk;

import java.util.List;

public interface IUdpSession
{
    int getSessionId();
    
    boolean supports(IUdpTalk udpTalk);
    
    void put(IUdpTalk udpTalk);
    
    void broadcast(IUdpTalk udpTalk, byte[] data);
    
    default void broadcastSyncPackets()
    {
        throw new UnsupportedOperationException("Unimplemented: broadcastSyncPackets()");
    }
    
    default List<UdpTalk> getSortedTalkers()
    {
        throw new UnsupportedOperationException("Unimplemented: getSortedTalkers()");
    }
    
    default long getDiffTime()
    {
        throw new UnsupportedOperationException("Unimplemented: getDiffTime()");
    }
    
    default byte getNumberOfClients()
    {
        throw new UnsupportedOperationException("Unimplemented: getNumberOfClients()");
    }
    
    default boolean isFull()
    {
        throw new UnsupportedOperationException("Unimplemented: isFull()");
    }
}
