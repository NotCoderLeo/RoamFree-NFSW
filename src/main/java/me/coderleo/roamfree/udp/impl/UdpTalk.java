/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.impl;

import me.coderleo.roamfree.udp.IProcessor;
import me.coderleo.roamfree.udp.IUdpHello;
import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.UdpWriter;
import me.coderleo.roamfree.udp.managers.SessionManager;

import java.util.Date;

public abstract class UdpTalk implements IUdpTalk, Comparable<UdpTalk>
{
    protected UdpWriter udpWriter;
    protected IProcessor processor;
    protected IUdpHello hello;
    protected long started;
    protected long ping;
    protected byte[] sync;
    protected boolean syncStarted;
    
    public UdpTalk(UdpWriter udpWriter, IProcessor processor, IUdpHello hello)
    {
        this.started = new Date().getTime();
        this.udpWriter = udpWriter;
        this.processor = processor;
        this.hello = hello;
    }
    
    public boolean isSyncStarted()
    {
        return syncStarted;
    }
    
    public byte getSessionClientId()
    {
        return hello.getSessionClientId();
    }
    
    public void sendFrom(IUdpTalk udpTalk, byte[] dataPacket)
    {
        byte[] processed = processor.getProcessed(dataPacket, getSessionClientId());
        
        if (processed != null)
            udpWriter.sendPacket(processed);
    }
    
    public void setIncomingSyncPacket(byte[] data)
    {
        this.sync = data;
        
        try
        {
            parseSyncPacket();
            
            syncStarted = true;
            ping = getDiffTime();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public long getStartingTime()
    {
        return started;
    }
    
    public void broadcast(byte[] dataPacket)
    {
        SessionManager.get(getSessionId()).broadcast(this, dataPacket);
    }
    
    public int getSessionId()
    {
        return hello.getSessionId();
    }
    
    public byte getNumberOfClients()
    {
        return hello.getNumberOfClients();
    }
    
    public long getDiffTime()
    {
        long now = new Date().getTime();
        
        return now - getStartingTime();
    }
    
    public long getPing()
    {
        return ping;
    }
    
    protected IProcessor getProcessor()
    {
        return processor;
    }
    
    public byte[] getHelloPacket()
    {
        return hello.getHelloPacket();
    }
    
    protected abstract void parseSyncPacket() throws Exception;
    
    public int compareTo(UdpTalk o)
    {
        if (ping < o.getPing())
            return -1;
        else if (ping > o.getPing())
            return 1;
        return 0;
    }
}
