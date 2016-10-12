/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.protocols.nfsw.race;

import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.UdpWriter;
import me.coderleo.roamfree.udp.impl.UdpHello;
import me.coderleo.roamfree.udp.packet.UdpDataPacket;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * The NFS:W hello implementation.
 * I have no clue what the offsets represent.
 * Wireshark and IDA, here I come?
 */
public class RaceHello extends UdpHello
{
    private byte[] helloPacketTmp;
    
    protected void parseHelloPacket() throws Exception
    {
        if ((helloPacket[3] != 0x06))
        {
            // Not exactly sure what this is.
            throw new Exception("[UDP Error] Hello packet was invalid!");
        }
        
        helloPacketTmp = new byte[]{helloPacket[5], helloPacket[6], helloPacket[7], helloPacket[8]};
    }
    
    protected void setSessionClientId() throws Exception
    {
        sessionClientId = helloPacket[4];
    }
    
    protected void setNumberOfClients() throws Exception
    {
        numberOfClients = helloPacket[13];
    }
    
    protected void setSessionId() throws Exception
    {
        byte[] sessionIdTmp = {helloPacket[9], helloPacket[10], helloPacket[11], helloPacket[12]};
        
        sessionId = new BigInteger(sessionIdTmp).intValue();
    }
    
    protected byte[] getServerHelloMessage()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        
        for (int i = 1; i < 4; i++)
        {
            byteBuffer.put((byte) 0x00);
        }
        
        byteBuffer.put((byte) 0x01);
        byteBuffer.put(helloPacketTmp);
        
        for (int i = 1; i < 5; i++)
        {
            byteBuffer.put((byte) 0x01);
        }
        
        return byteBuffer.array();
    }
    
    protected IUdpTalk getUdpTalkInstance(UdpDataPacket dataPacket)
    {
        RaceProcessor packetProcessor = new RaceProcessor();
        UdpWriter writer = new UdpWriter(dataPacket);
        
        return new RaceTalk(this, packetProcessor, writer);
    }
}
