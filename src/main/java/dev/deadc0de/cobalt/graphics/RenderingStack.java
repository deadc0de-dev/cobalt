package dev.deadc0de.cobalt.graphics;

public interface RenderingStack {

    void pushLayer(RenderingLayer layer, View view);

    void popLayer();
}
