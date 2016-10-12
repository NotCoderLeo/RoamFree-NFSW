/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.sessions.types;

import com.google.common.base.Preconditions;
import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.UdpTalkPingComparator;
import me.coderleo.roamfree.udp.impl.UdpTalk;
import me.coderleo.roamfree.udp.protocols.nfsw.freeroam.FreeroamTalk;
import me.coderleo.roamfree.udp.sessions.IUdpSession;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class FreeroamSession implements IUdpSession
{
    private HashMap<Integer, FreeroamTalk> udpTalkers = new HashMap<>();
    private int sessionId;
    
    public FreeroamSession(int sessionId)
    {
        this.sessionId = sessionId;
    }
    
    @Override
    public int getSessionId()
    {
        return sessionId;
    }
    
    @Override
    public boolean supports(IUdpTalk udpTalk)
    {
        return udpTalk.getSyncPacket()[0] == 0x00; // freeroam sync will *always* be 0x00
    }
    
    @Override
    public void put(IUdpTalk udpTalk)
    {
        Preconditions.checkState(udpTalk instanceof FreeroamTalk, "Not a freeroam talker!");
        
        udpTalkers.put(((FreeroamTalk) udpTalk).getPersonaId(), (FreeroamTalk) udpTalk);
    }
    
    @Override
    public void broadcast(IUdpTalk udpTalk, byte[] data)
    {
        List<UdpTalk> talkersOrderedByPing = getSortedTalkers();
        long higherPing = talkersOrderedByPing.get(0).getPing() + 10;
        
        for (Entry<Integer, FreeroamTalk> next : udpTalkers.entrySet())
        {
            Integer key = next.getKey();
            FreeroamTalk udpTalkTmp = (FreeroamTalk) udpTalk;
            Integer personaId = udpTalkTmp.getPersonaId();
    
            long waitTime = higherPing - next.getValue().getPing();
    
            if (!personaId.equals(key))
            {
                System.out.print("freeroam [" + udpTalkTmp.getSessionClientId());
                System.out.print("] ping: [" + udpTalkTmp.getPing());
                System.out.println("] waitTime: " + waitTime + "ms\n\n");
        
                try
                {
                    Thread.sleep(waitTime);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
        
                udpTalkTmp.sendFrom(udpTalk, data);
            }
//            if (!personaId.equals(key))
//            {
//                IUdpTalk udpTalkTmp = next.getValue();
//
//                udpTalkTmp.sendFrom(udpTalk, data);
//            }
        }
    }
    
    @Override
    public List<UdpTalk> getSortedTalkers()
    {
        List<UdpTalk> talkersOrderedByPing = udpTalkers.entrySet().stream()
                .map(next -> (UdpTalk) next.getValue())
                .collect(Collectors.toList());
        
        Collections.sort(talkersOrderedByPing, new UdpTalkPingComparator());
        
        return talkersOrderedByPing;
    }
}
