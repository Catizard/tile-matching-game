package com.dreamtea.Game.MessagePipeline;

import java.util.Map;

public abstract class MessageHandler {
    protected MessageHandler nextHandler;

    public String handle(String... messages) throws Exception {
        return null;
    }
    public String handle(Map<String, Object> messages) throws Exception {
        return null;
    }
    public void setNextHandler(MessageHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    public MessageHandler getNextHandler() {
        return nextHandler;
    }

    public boolean isSupported(String[] SUPPORTEDTYPES, String type) {
        if(type == null) {
            return false;
        }

        for (String supportedType : SUPPORTEDTYPES) {
            if(type.equals(supportedType)) {
                return true;
            }
        }
        return false;
    }
}
