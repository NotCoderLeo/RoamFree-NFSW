/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp;

import me.coderleo.roamfree.udp.packet.UdpDataPacket;

public interface IUdpHello
{
    IUdpTalk startTalking(UdpDataPacket dataPacket);
    
    byte getSessionClientId();
    
    byte getNumberOfClients();
    
    int getSessionId();
    
    byte[] getHelloPacket();
}
