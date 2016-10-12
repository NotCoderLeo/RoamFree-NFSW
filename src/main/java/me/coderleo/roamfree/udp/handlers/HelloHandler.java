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
import me.coderleo.roamfree.udp.packet.UdpDataPacket;

public class HelloHandler
{
    public static IUdpTalk startTalk(IUdpProtocol protocol, UdpDataPacket packet)
    {
        return protocol.getNewHelloInstance().startTalking(packet);
    }
}