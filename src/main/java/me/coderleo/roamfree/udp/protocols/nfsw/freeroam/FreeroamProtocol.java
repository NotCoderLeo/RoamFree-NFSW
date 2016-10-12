/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.protocols.nfsw.freeroam;

import me.coderleo.roamfree.udp.IUdpHello;
import me.coderleo.roamfree.udp.IUdpProtocol;

public class FreeroamProtocol implements IUdpProtocol
{
    @Override
    public IUdpHello getNewHelloInstance()
    {
        return new FreeroamHello();
    }
    
    @Override
    public int getMaxPacketSize()
    {
        return 2048;
    }
    
    @Override
    public boolean compatibleWith(byte[] data)
    {
        return data.length >= 7;
    }
}
