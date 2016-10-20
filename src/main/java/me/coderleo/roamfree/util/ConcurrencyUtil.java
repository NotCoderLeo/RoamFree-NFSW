/*
 * RoamFree UDP Server
 *
 * Written by leorblx (Original UDP server by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ConcurrencyUtil
{
    public static ExecutorService pool = Executors.newFixedThreadPool(100);
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100);
}
