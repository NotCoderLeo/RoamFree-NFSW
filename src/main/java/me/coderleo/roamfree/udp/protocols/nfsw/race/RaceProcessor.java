/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.protocols.nfsw.race;

import me.coderleo.roamfree.udp.IProcessor;

import java.nio.ByteBuffer;

public class RaceProcessor implements IProcessor
{
    private int countA = 1;
    private int countB = 0;
    private byte sessionClientId;
    private boolean syncStopped = false;
    
    @Override
    public byte[] getProcessed(byte[] data, byte sessionClientId)
    {
        this.sessionClientId = sessionClientId;
        
        if (isTypeB(data))
        {
            return transformByteTypeB(data);
        }
        
        if (isTypeA(data))
        {
            return transformByteTypeA(data);
        }
        
        if (isTypeASync(data))
        {
            return transformByteTypeASync(data);
        }
        
        return data;
    }
    
    @Override
    public void syncStopped()
    {
        syncStopped = true;
    }
    
    @Override
    public boolean isSyncStopped()
    {
        return syncStopped;
    }
    
    /**
     * I honestly have no clue what these different "types" are.
     */
    private boolean isTypeA(byte[] data)
    {
        // 0x00 appears to be "nothing." not sure what 0x07 represents.
        return data[0] == 0x00 && data[3] == 0x07 && data.length != 26;
    }
    
    private boolean isTypeASync(byte[] data)
    {
        return data[0] == 0x00 && data[3] == 0x07 && data.length == 26;
    }
    
    private boolean isTypeB(byte[] data)
    {
        return data[0] == 0x01;
    }
    
    private byte[] transformByteTypeA(byte[] data)
    {
        if (data.length == 22)
        {
            if (!syncStopped)
            {
                syncStopped = true;
                return null;
            }
        }
        
        byte[] seqArray = ByteBuffer.allocate(2).putShort((short) countA).array();
        int size = data.length - 1;
        byte[] dataTmp = new byte[size];
        dataTmp[1] = seqArray[0];
        dataTmp[2] = seqArray[1];
        int iDataTmp = 3;
        for (int i = 4; i < data.length; i++)
        {
            dataTmp[iDataTmp++] = data[i];
        }
        
        countA++;
        return dataTmp;
    }
    
    private byte[] transformByteTypeASync(byte[] data)
    {
        data = transformByteTypeA(data);
        data[(data.length - 11)] = sessionClientId;
        countA++;
        return data;
    }
    
    private byte[] transformByteTypeB(byte[] data)
    {
        if (data.length < 4)
        {
            return null;
        }
        byte[] seqArray = ByteBuffer.allocate(2).putShort((short) countB).array();
        int size = data.length - 3;
        byte[] dataTmp = new byte[size];
        dataTmp[0] = 1;
        dataTmp[1] = sessionClientId;
        dataTmp[2] = seqArray[0];
        dataTmp[3] = seqArray[1];
        int iDataTmp = 4;
        for (int i = 6; i < (data.length - 1); i++)
        {
            dataTmp[iDataTmp++] = data[i];
        }
        
        if (!syncStopped)
        {
            dataTmp[4] = (byte) 0xff;
            dataTmp[5] = (byte) 0xff;
        }
        
        countB++;
        return dataTmp;
    }
}
