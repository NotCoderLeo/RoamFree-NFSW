/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.impl;

import me.coderleo.roamfree.udp.IUdpHello;
import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.packet.UdpDataPacket;

public abstract class UdpHello implements IUdpHello
{
    protected byte[] helloPacket;
    protected byte sessionClientId;
    protected byte numberOfClients;
    protected int sessionId;
    
    public IUdpTalk startTalking(UdpDataPacket dataPacket)
    {
        IUdpTalk talk = null;
        
        try
        {
            setHelloPacket(dataPacket.getDataBytes());
            talk = getUdpTalkInstance(dataPacket);
            sendHelloPacket(talk);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return talk;
    }
    
    public byte getSessionClientId()
    {
        return sessionClientId;
    }
    
    public byte getNumberOfClients()
    {
        return numberOfClients;
    }
    
    public int getSessionId()
    {
        return sessionId;
    }
    
    public byte[] getHelloPacket()
    {
        return helloPacket;
    }
    
    protected abstract void parseHelloPacket() throws Exception;
    
    protected abstract void setSessionClientId() throws Exception;
    
    protected abstract void setNumberOfClients() throws Exception;
    
    protected abstract void setSessionId() throws Exception;
    
    protected abstract byte[] getServerHelloMessage();
    
    protected abstract IUdpTalk getUdpTalkInstance(UdpDataPacket dataPacket);
    
    protected void sendHelloPacket(IUdpTalk udpTalk)
    {
        udpTalk.sendFrom(udpTalk, getServerHelloMessage());
    }
    
    protected void setHelloPacket(byte[] helloPacket) throws Exception
    {
        this.helloPacket = helloPacket;
        parseHelloPacket();
        setSessionClientId();
        setNumberOfClients();
        setSessionId();
        
        if (getNumberOfClients() < 2)
        {
            throw new Exception("[UDP Warn] Not enough clients! At least 2 required.");
        }
    }
}
