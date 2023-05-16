package ru.projectrobots.core.drawer;

/* created by zzemlyanaya on 07/03/2023 */

public abstract class Drawer {
    protected String createFullName(String... components){
        return String.join(".", components);
    }
}
