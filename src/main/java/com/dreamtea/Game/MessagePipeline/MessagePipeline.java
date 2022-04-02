package com.dreamtea.Game.MessagePipeline;

import java.util.ArrayList;
import java.util.Map;


public class MessagePipeline {
    private final String pipelineName;
    private final ArrayList<MessageHandler> handlers;

    public MessagePipeline(String pipelineName) {
        this.pipelineName = pipelineName;
        handlers = new ArrayList<>();

        MessageHandler dummy = new DummyMessageHandler();
        handlers.add(dummy);
    }

    public MessagePipeline addLast(MessageHandler handler) {
        if(handlers.size() > 1) {
            (handlers.get(handlers.size() - 2)).setNextHandler(handler);
        }
        handler.setNextHandler(handlers.get(handlers.size() - 1));
        handlers.add(handlers.size() - 1, handler);
        return this;
    }

    public MessagePipeline addFirst(MessageHandler handler) {
        handler.setNextHandler(getFirstHandler());
        handlers.add(0, handler);
        return this;
    }

    public void remove(MessageHandler handler) {
        int i = handlers.indexOf(handler);
        if(i - 1 >= 0) {
            (handlers.get(i - 1)).setNextHandler(handler.getNextHandler());
        }
        handlers.remove(i);
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public String readMessage(String message) throws Exception {
        return getFirstHandler().handle(message);
    }

    public String readMessage(Map<String, Object> message) throws Exception {
        return getFirstHandler().handle(message);
    }

    public MessageHandler getFirstHandler() {
        return handlers.size() == 0 ? null : handlers.get(0);
    }

    public MessagePipeline setEnd(MessageHandler handler) {
        if(handlers.size() > 1) {
            handlers.get(handlers.size() - 2).setNextHandler(handler);
        }
        handlers.set(handlers.size() - 1, handler);
        return this;
    }

    static class DummyMessageHandler extends MessageHandler {
        @Override
        public String handle(String... messages) throws Exception {
            return null;
        }

        @Override
        public String handle(Map<String, Object> messages) throws Exception {
            return null;
        }
    }
}
