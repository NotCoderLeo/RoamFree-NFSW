/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp;

import java.util.HashMap;

public class UdpTalkers
{
    private static HashMap<Integer, IUdpTalk> udpTalkers = new HashMap<Integer, IUdpTalk>();
    
    public static IUdpTalk put(Integer key, IUdpTalk value)
    {
        return udpTalkers.put(key, value);
    }
    
    public static IUdpTalk remove(Integer key)
    {
        return udpTalkers.remove(key);
    }
    
    public static IUdpTalk get(Integer key)
    {
        return udpTalkers.get(key);
    }
}
