package com.dreamtea.Game.Utils;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

@Component
public final class TextWebSocketFrameMessageFactory extends AdaptableMessageFactory {
    @Override
    public TextWebSocketFrame castAdaptMessage(String message) {
        return new TextWebSocketFrame(message);
    }
}
