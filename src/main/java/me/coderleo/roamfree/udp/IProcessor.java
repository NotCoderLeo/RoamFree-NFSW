/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp;

public interface IProcessor
{
    byte[] getProcessed(byte[] data, byte sessionClientId);
    
    void syncStopped();
    
    boolean isSyncStopped();
}
