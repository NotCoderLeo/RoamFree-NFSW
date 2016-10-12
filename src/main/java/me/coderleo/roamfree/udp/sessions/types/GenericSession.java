/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.sessions.types;

import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.sessions.IUdpSession;

/**
 * Represents a session that stores effectively no data.
 * This should be used only if the other two types can not be created.
 */
public class GenericSession implements IUdpSession
{
    @Override
    public int getSessionId()
    {
        return 0;
    }
    
    @Override
    public boolean supports(IUdpTalk udpTalk)
    {
        return false;
    }
    
    @Override
    public void put(IUdpTalk udpTalk)
    {
        
    }
    
    @Override
    public void broadcast(IUdpTalk udpTalk, byte[] data)
    {
        
    }
}
