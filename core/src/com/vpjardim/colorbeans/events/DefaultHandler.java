/*
 * Copyright 2015-2018 Vinícius Petrocione Jardim. All rights reserved
 */

package com.vpjardim.colorbeans.events;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author Vinícius Jardim
 * 2018/04/14
 */
public class DefaultHandler implements EventHandler {

    public static final DefaultHandler defaultHandler = new DefaultHandler();

    private ObjectMap<String, Array<EventListener>> listeners;

    public DefaultHandler() {
        listeners = new ObjectMap<>();
    }

    @Override
    public void addListener(String type, EventListener eListener) {

        Array<EventListener> typeListeners;

        if(listeners.get(type) == null) {
            typeListeners = new Array<>();
            listeners.put(type, typeListeners);
        }
        else typeListeners = listeners.get(type);

        typeListeners.add(eListener);
    }

    @Override
    public void removeListener(String type, EventListener eListener) {
        if(listeners.get(type) != null) {
            listeners.get(type).removeValue(eListener, true);
        }
    }

    @Override
    public void addEvent(String type, Event e) {
        if(listeners.get(type) != null) {
            for(EventListener eListener : listeners.get(type)) {
                eListener.handleEvent(e);
            }
        }
    }
}
