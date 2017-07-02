/*
 * Copyright 2017 Vinícius Petrocione Jardim
 */

package com.vpjardim.colorbeans.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * @author Vinícius Jardim
 * 06/01/2017
 *
 * #debugCode
 */
public class Dbg {

    // Used to change game speed
    public static final int DELTA_REAL  = 1;
    public static final int DELTA_0_25X = 2;
    public static final int DELTA_0_5X  = 3;
    public static final int DELTA_1X    = 4;
    public static final int DELTA_2X    = 5;
    public static final int DELTA_4X    = 6;

    public int delta;
    public boolean fps;
    public boolean fpsText;
    public boolean lagWarn;
    /** LOG_NONE = []; LOG_ERROR [error]; LOG_INFO [error, log]; LOG_DEBUG [error, log, debug] */
    public int logLevel;
    public boolean uiTable;
    public int map0shape;
    public int map1shape;
    public int campStart;
    public int campEnd;
    public boolean aiPlayerCamp;
    public boolean aiDisableMap1;
    public int[] aiTraining;

    public Dbg() { off(); }

    public void off() {

        delta         = DELTA_REAL;
        fps           = false;
        fpsText       = false;
        lagWarn       = false;
        logLevel      = Application.LOG_NONE;
        uiTable       = false;
        map0shape     = 0;
        map1shape     = 0;
        campStart     = 0;
        campEnd       = Integer.MAX_VALUE;
        aiPlayerCamp  = false;
        aiDisableMap1 = false;
        aiTraining    = null;

        Gdx.app.setLogLevel(logLevel);
    }

    public void on() { Gdx.app.setLogLevel(logLevel); }

    public static void print(String str) { System.out.println(str); }

    public static void err(String tag, String str) { Gdx.app.error(tag, str); }

    public static void inf(String tag, String str) { Gdx.app.log(tag, str); }

    public static void dbg(String tag, String str) { Gdx.app.debug(tag, str); }

    /** Tag only class name */
    public static String tag(Object o) { return o.getClass().getSimpleName(); }

    /** Tag class # object name */
    public static String tagO(Object o) { return o.getClass().getSimpleName() + "#" + o; }
}
