/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp;

import me.coderleo.roamfree.udp.impl.UdpTalk;

import java.util.Comparator;

public class UdpTalkPingComparator implements Comparator<UdpTalk>
{
    @Override
    public int compare(UdpTalk udpTalk1, UdpTalk udpTalk2)
    {
        return udpTalk2.compareTo(udpTalk1);
    }
}