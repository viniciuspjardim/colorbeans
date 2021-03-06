/*
 * Copyright 2015-2018 Vinícius Petrocione Jardim. All rights reserved
 */

package com.vpjardim.colorbeans.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.vpjardim.colorbeans.G;

/**
 * @author Vinícius Jardim
 * 2015/03/21
 */
public class DesktopLauncher {

    public static void main(String[] args) {

        boolean fullScreen = true;
        boolean useVsync = true;

        for(int i = 0; i < args.length; i++) {
            if(args[i].equals("-window")) {
                fullScreen = false;
            }
            else if(args[i].equals("-vsyncOff")) {
                useVsync = false;
            }
        }

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        config.setWindowIcon("icon/desk256.png", "icon/desk64.png", "icon/desk32.png");

        config.setWindowedMode(1280, 720);
        config.setTitle("Color Beans");
        config.setWindowSizeLimits(480, 320, 3840, 2160);
        config.useVsync(useVsync);

        if(fullScreen) {
            config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        }

        new Lwjgl3Application(new G(), config);
    }
}