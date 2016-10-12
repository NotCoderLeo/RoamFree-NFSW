/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.protocols.nfsw.freeroam;

import me.coderleo.roamfree.udp.IProcessor;
import me.coderleo.roamfree.udp.IUdpHello;
import me.coderleo.roamfree.udp.UdpWriter;
import me.coderleo.roamfree.udp.impl.UdpTalk;

public class FreeroamTalk extends UdpTalk
{
    public FreeroamTalk(UdpWriter udpWriter, IProcessor processor, IUdpHello hello)
    {
        super(udpWriter, processor, hello);
    }
    
    @Override
    public byte[] getSyncPacket()
    {
        return new byte[]{0x00};
    }
    
    @Override
    protected void parseSyncPacket() throws Exception
    {
        //
    }
    
    public int getPersonaId()
    {
        return ((FreeroamHello) hello).getPersonaId();
    }
}
