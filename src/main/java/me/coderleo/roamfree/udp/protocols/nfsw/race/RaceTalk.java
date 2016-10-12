/*
 * Licensed under the WTFPL, I don't care what you do 
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.protocols.nfsw.race;

import me.coderleo.roamfree.udp.IProcessor;
import me.coderleo.roamfree.udp.IUdpHello;
import me.coderleo.roamfree.udp.UdpWriter;
import me.coderleo.roamfree.udp.impl.UdpTalk;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class RaceTalk extends UdpTalk
{
    public RaceTalk(IUdpHello udpHello, IProcessor packetProcessor, UdpWriter udpWriter)
    {
        super(udpWriter, packetProcessor, udpHello);
    }
    
    @Override
    protected void parseSyncPacket() throws Exception
    {
        ByteBuffer dbuf = ByteBuffer.allocate(4);
        dbuf.putInt(getSessionId());
        byte[] sessionId = dbuf.array();
        
        if (!(sync[0] == 0x00 && //
                sync[4] == 0x02 && //
                sync[13] == 0x00 && //
                sync[14] == 0x06 && //
                sync[15] == 0x00 && //
                sync[16] == sessionId[0] && //
                sync[17] == sessionId[1] && //
                sync[18] == sessionId[2] && //
                sync[19] == sessionId[3]))
        {
            throw new Exception("[UDP Err] Invalid sync packet!");
        }
    }
    
    private byte pingCalc()
    {
        long diffTime = getDiffTime();
        if (diffTime > 1000)
        {
            return (byte) 0xff;
        }
        
        float pingCalcFloat = 0.254F * diffTime;
        
        return new BigDecimal(pingCalcFloat).byteValue();
    }
    
    @Override
    public byte[] getSyncPacket()
    {
        byte[] helloPacket = getHelloPacket();
        byte[] pingSyncPacket = sync;
        // pingSyncPacket[5] = seqSync; // seq from hello part?
        pingSyncPacket[6] = pingCalc(); // ping calc?
        pingSyncPacket[7] = helloPacket[2]; // hello part, fixed byte?
        pingSyncPacket[8] = helloPacket[3]; // hello part, packet loss?
        pingSyncPacket[(pingSyncPacket.length - 6)] = (byte) 0x03;
        
        return pingSyncPacket;
    }
}
