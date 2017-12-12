/*
 * Copyright 2017 Vinícius Petrocione Jardim
 */

package com.vpjardim.colorbeans.input;

import com.badlogic.gdx.input.GestureDetector;
import com.vpjardim.colorbeans.G;
import com.vpjardim.colorbeans.Map;
import com.vpjardim.colorbeans.core.Dbg;

/**
 * @author Vinícius Jardim
 * 10/02/2017
 */
public class TouchInput2 extends GestureDetector.GestureAdapter implements InputBase {

    private TargetBase target;
    private Map map;

    private boolean horizontalEvent = false;
    private boolean verticalEvent = false;

    /** -1 left; 1 right */
    private int horizontal = 0;
    /** -1 up; 1 down */
    private int vertical = 0;

    /** -1 left; 1 right */
    private int horizontalOld = 0;
    /** -1 up; 1 down */
    private int verticalOld = 0;

    public int width = 400;
    public float[] div;
    public boolean hPanning = false;
    public boolean vPanning = false;
    public boolean move = false;
    public boolean draw = false;

    public int moveCurr;
    public int moveStart;

    public float touchX = 0f;
    public float touchY = 0f;
    public float dTouchX = 0f;
    public float dTouchY = 0f;

    @Override
    public void setTarget(TargetBase target) {

        this.target = target;

        if(target instanceof Map) {
            map = (Map) target;
            div = new float[map.N_COL + 1];
        }
        else map = null;
    }

    @Override
    public void setProfile(Profile profile) {}

    @Override
    public Profile getProfile() { return null; }

    @Override
    public void update() {

        if(!horizontalEvent) { horizontalOld = horizontal; }
        if(!verticalEvent) { verticalOld = vertical; }

        horizontalEvent = false;
        verticalEvent = false;

        if(move) move();
    }

    @Override
    public int getAxisX() { return horizontal; }

    @Override
    public int getAxisY() { return vertical; }

    @Override
    public int getAxisXOld() { return horizontalOld; }

    @Override
    public int getAxisYOld() { return verticalOld; }

    private void move() {

        if(map != null && map.isInState(Map.MState.PLAYER_FALL)) {

            int deltaH = moveCurr - map.pb.b1x;

            if(deltaH == 0) horizontal = 0;
            else {
                horizontal = deltaH / Math.abs(deltaH);
                vertical = 0;
            }
        }
        else vertical = 0;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        // #debugCode
        Dbg.dbg(Dbg.tag(this), "tap -> x = " + x + "; y = " + y + "; count = " + count +
                "; button = " + button);

        if(target == null) return false;

        if(y < G.height * 0.2f) {
            target.buttonStart(true);
            return false;
        }

        if(x > G.width / 2f)
            target.button1(true);
        else
            target.button3(true);

        // Returns false because the stage need this event on the PlayScreen
        // Todo ControllerInput or other input might have the same problem
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        if(map == null) return false;

        // #debugCode
        Dbg.print(Dbg.tag(this) + ": pan ->   x = " + x + ";  y = " + y + "; dTouchX = " + deltaX +
                "; dTouchY = " + deltaY);

        // Resetting values
        if(!hPanning && !vPanning) {
            move = false;
            hPanning = false;
            vPanning = false;
            touchX = x;
            touchY = y;
            dTouchX = 0f;
            dTouchY = 0f;
            moveStart = map.pb.b1x;
        }

        draw = true;
        dTouchX += deltaX;
        dTouchY += deltaY;
        updateDiv();
        findCurrent();

        if(vertical == 0 && Math.abs(deltaX) >= Math.abs(deltaY) * 0.8) {
            hPanning = true;
            horizontalEvent = true;

            move = true;
        }
        else if(horizontal == 0 && !hPanning && deltaY > 0) {
            vPanning = true;
            verticalEvent = true;

            verticalOld = vertical;
            vertical = 1;
        }

        // #debugCode
        Dbg.print(Dbg.tag(this) + ": pan2 -> tx = " + touchX + "; ty = " + touchY +
                "; dTouchX = " + this.dTouchX + "; dTouchY = " + this.dTouchY);

        return false;
    }

    private void updateDiv() {

        float colWidth = width / map.N_COL;
        float x = touchX - (moveStart * colWidth) - colWidth / 2f;
        div[0] = x;
        div[div.length -1] = x + width;

        for(int i = 1; i < div.length -1; i++) {
            div[i] = x + i * colWidth;
        }
    }

    private void findCurrent() {

        float touchCurr = dTouchX + touchX;

        for(int i = 1; i < div.length -1; i++) {
            if(div[i] > touchCurr) {
                moveCurr = i -1;
                return;
            }
        }
        moveCurr = map.N_COL - 1;
    }

    @Override
    public boolean panStop (float x, float y, int pointer, int button) {

        // #debugCode
        Dbg.print(Dbg.tag(this) + ": panStop -> x = " + x + "; y = " + y);

        horizontalEvent = true;
        horizontalOld = horizontal;
        horizontal = 0;

        hPanning = false;
        vPanning = false;
        move = false;
        draw = false;
        moveCurr = 0;
        moveStart = 0;

        touchX = 0f;
        touchY = 0f;
        dTouchX = 0f;
        dTouchY = 0f;

        verticalClean();
        return false;
    }

    public void verticalClean() {
        if(vertical == 1) {
            verticalEvent = true;
            verticalOld = vertical;
            vertical = 0;
        }
    }
}