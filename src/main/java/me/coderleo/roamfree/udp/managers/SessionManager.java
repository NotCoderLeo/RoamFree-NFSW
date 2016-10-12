/*
 * Licensed under the WTFPL, I don't care what you do 
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.udp.managers;

import me.coderleo.roamfree.udp.IUdpTalk;
import me.coderleo.roamfree.udp.protocols.nfsw.freeroam.FreeroamTalk;
import me.coderleo.roamfree.udp.protocols.nfsw.race.RaceTalk;
import me.coderleo.roamfree.udp.sessions.IUdpSession;
import me.coderleo.roamfree.udp.sessions.types.FreeroamSession;
import me.coderleo.roamfree.udp.sessions.types.GenericSession;
import me.coderleo.roamfree.udp.sessions.types.RaceSession;
import me.coderleo.roamfree.util.Log;

import java.util.HashMap;

public class SessionManager
{
    private static HashMap<Integer, IUdpSession> udpSessions = new HashMap<>();
    
    public static IUdpSession addUdpTalk(IUdpTalk udpTalk)
    {
        int sessionId = udpTalk.getSessionId();
        IUdpSession udpSession = udpSessions.get(sessionId);
        
        if (udpSession == null)
        {
            Log.debug("New session (ID: %s) added to session manager");
            
            udpSession = findBestType(udpTalk);
            udpSession.put(udpTalk);
            
            put(udpSession);
            
            return udpSession;
        }
        
        udpSession.put(udpTalk);
        
        Log.debug("Returning session with new talker (ID: %s)", udpSession.getSessionId());
        
        try
        {
            udpSession.broadcastSyncPackets();
        } catch (UnsupportedOperationException e)
        {
            //
        }
        
        return udpSession;
    }
    
    private static void put(IUdpSession udpSession)
    {
        udpSessions.put(udpSession.getSessionId(), udpSession);
    }
    
    public IUdpSession remove(IUdpSession udpSession)
    {
        return udpSessions.remove(udpSession.getSessionId());
    }
    
    private static IUdpSession findBestType(IUdpTalk udpTalk)
    {
        if (udpTalk instanceof RaceTalk)
            return new RaceSession(udpTalk.getSessionId(), udpTalk.getNumberOfClients(), udpTalk.getStartingTime());
        else if (udpTalk instanceof FreeroamTalk)
            return new FreeroamSession(udpTalk.getSessionId());
        
        return new GenericSession();
    }
    
    public static IUdpSession get(int sessionId)
    {
        return udpSessions.get(sessionId);
    }
}