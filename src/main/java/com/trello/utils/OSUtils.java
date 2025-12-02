package com.trello.utils;

import com.trello.utils.dataReader.PropertyReader;

public class OSUtils {
    public enum OS { WINDOWS, MAC, LINUX, OTHER }

    public static OS getOS() {
        String os = PropertyReader.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return OS.WINDOWS;
        else if (os.contains("mac")) return OS.MAC;
        else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) return OS.LINUX;
        else return OS.OTHER;
    }
}
