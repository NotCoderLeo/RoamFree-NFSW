/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.sessions.types;

import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.UdpTalkPingComparator;
import me.coderleo.roamfree.udp.impl.UdpTalk;
import me.coderleo.roamfree.udp.protocols.nfsw.race.RaceTalk;
import me.coderleo.roamfree.udp.sessions.IUdpSession;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RaceSession implements IUdpSession
{
    private HashMap<Integer, RaceTalk> udpTalkers = new HashMap<>();
    private int sessionId;
    private byte numberOfClients;
    private long startingTime;
    private boolean isSyncDone = false;
    
    public RaceSession(int sessionId, byte numberOfClients, long startingTime)
    {
        this.sessionId = sessionId;
        this.numberOfClients = numberOfClients;
        this.startingTime = startingTime;
    }
    
    @Override
    public int getSessionId()
    {
        return sessionId;
    }
    
    @Override
    public boolean supports(IUdpTalk udpTalk)
    {
        return udpTalk.getSyncPacket()[7] == udpTalk.getHelloPacket()[2];
    }
    
    @Override
    public void put(IUdpTalk udpTalk)
    {
        udpTalkers.put((int) udpTalk.getSessionClientId(), (RaceTalk) udpTalk);
    }
    
    @Override
    public void broadcast(IUdpTalk udpTalk, byte[] data)
    {
        if (isSyncDone)
        {
            for (Entry<Integer, RaceTalk> next : udpTalkers.entrySet())
            {
                Integer key = next.getKey();
                Integer sessionClientIdx = (int) udpTalk.getSessionClientId();
                
                if (!sessionClientIdx.equals(key))
                {
                    IUdpTalk udpTalkTmp = next.getValue();
                    udpTalkTmp.sendFrom(udpTalk, data);
                }
            }
        }
    }
    
    @Override
    public void broadcastSyncPackets()
    {
        if (isFull() && !isSyncDone)
        {
            Iterator<Entry<Integer, RaceTalk>> iterator = udpTalkers.entrySet().iterator();
            
            while (iterator.hasNext())
            {
                Entry<Integer, RaceTalk> next = iterator.next();
                IUdpTalk udpTalkTmp = next.getValue();
                broadcastSyncPackets(udpTalkTmp, udpTalkTmp.getSyncPacket());
            }
            
            isSyncDone = true;
        }
    }
    
    private void broadcastSyncPackets(IUdpTalk udpTalk, byte[] dataPacket)
    {
        List<UdpTalk> talkersOrderedByPing = getSortedTalkers();
        long higherPing = talkersOrderedByPing.get(0).getPing() + 10;
        
        for (UdpTalk udpTalkTmp : talkersOrderedByPing)
        {
            Integer key = (int) udpTalkTmp.getSessionClientId();
            Integer sessionClientIdx = (int) udpTalk.getSessionClientId();
            long waitTime = higherPing - udpTalkTmp.getPing();
            
            if (!sessionClientIdx.equals(key))
            {
                // trying to sync first session packet... failed
                System.out.print("[" + udpTalkTmp.getSessionClientId());
                System.out.print("] ping: [" + udpTalkTmp.getPing());
                System.out.println("] waitTime: " + waitTime + "ms\n\n");
                
                try
                {
                    Thread.sleep(waitTime);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                udpTalkTmp.sendFrom(udpTalk, dataPacket);
            }
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
    
    @Override
    public long getDiffTime()
    {
        return new Date().getTime() - startingTime;
    }
    
    @Override
    public byte getNumberOfClients()
    {
        return numberOfClients;
    }
    
    @Override
    public boolean isFull()
    {
        return udpTalkers.size() == numberOfClients;
    }
}
