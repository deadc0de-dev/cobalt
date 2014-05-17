package dev.deadc0de.cobalt.text;

public interface MenuEntry {

    String label();

    void onSelected(MenuFacade menuFacade);
}
