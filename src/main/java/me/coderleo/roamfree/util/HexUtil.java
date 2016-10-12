/*
 * Licensed under the WTFPL, I don't care what you do
 * as long as you retain the copyright. Please.
 *
 * Copyright (c) 2016 leorblx (Original by Nilzao!)
 * This is in no way affiliated with EA, or the developers of Need for Speed World.
 */

package me.coderleo.roamfree.util;

public class HexUtil
{
    public static byte[] hexStringToByteArray(String input)
    {
        input = input.replace(":", "");
        int len = input.length();
        byte[] data = new byte[len / 2];
        
        for (int i = 0; i < len; i += 2)
            data[i / 2] = (byte) ((
                    Character.digit(input.charAt(i), 16) << 4) + Character.digit(input.charAt(i + 1), 16));
        return data;
    }
    
    public static String byteArrayToHexString(byte[] input)
    {
        int len = input.length;
        StringBuilder buf = new StringBuilder();
    
        for (byte anInput : input)
        {
            buf.append(Integer.toHexString((anInput >> 4) & 0xf));
            buf.append(Integer.toHexString(anInput & 0xf));
            buf.append(':');
        }
        
        return buf.toString();
    }
}
