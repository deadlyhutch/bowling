package com.f8software.bowling.render;

import com.f8software.bowling.model.Player;

/**
 * This is a simple function interface that can be used to render a player status
 */
public interface Renderer {
    void render(Player player);
}
