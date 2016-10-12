/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.protocols.nfsw.freeroam;

import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.impl.UdpHello;
import me.coderleo.roamfree.udp.packet.UdpDataPacket;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class FreeroamHello extends UdpHello
{
    private byte[] helloPacketTmp;
    private int personaId;
    
    @Override
    protected void parseHelloPacket() throws Exception
    {
        if (helloPacket[2] != 0x06)
        {
            // not sure what the significance of this is.
            throw new Exception("[UDP Err] Invalid freeroam hello packet!");
        }
        
        helloPacketTmp = new byte[]{
                helloPacket[3],
                helloPacket[4],
                helloPacket[5],
                helloPacket[6]
        };
    }
    
    @Override
    protected void setSessionClientId() throws Exception
    {
        //
    }
    
    @Override
    protected void setNumberOfClients() throws Exception
    {
        
    }
    
    @Override
    protected void setSessionId() throws Exception
    {
        sessionId = 666;
    }
    
    @Override
    protected byte[] getServerHelloMessage()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(11);
        
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) 0x01);
        byteBuffer.put(helloPacketTmp[0]);
        byteBuffer.put(helloPacketTmp[1]);
        byteBuffer.put(helloPacketTmp[2]);
        byteBuffer.put(helloPacketTmp[3]);
        byteBuffer.put((byte) 0x01);
        byteBuffer.put((byte) 0x01);
        byteBuffer.put((byte) 0x01);
        byteBuffer.put((byte) 0x01);
        
        return byteBuffer.array();
    }
    
    @Override
    protected IUdpTalk getUdpTalkInstance(UdpDataPacket dataPacket)
    {
        
        
        return null;
    }
    
    public int getPersonaId()
    {
        return personaId;
    }
    
    protected void setPersonaId() throws Exception
    {
        byte[] personaIdTmp = {helloPacket[7], helloPacket[8], helloPacket[9], helloPacket[10]};
        
        personaId = new BigInteger(personaIdTmp).intValue();
    }
}
