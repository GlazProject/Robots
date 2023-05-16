package ru.projectrobots.core.view;

import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;

public class GameJButton extends JButton {
    private static final String FONT_NAME = "button";
    private static Font font;

    public GameJButton(String text){
        this(text, null);
    }

    public GameJButton(String text, Icon icon){
        super(text, icon);
        trySetFont();
    }

    private void trySetFont(){
        if(font == null)
            font = Repository.getFont(FONT_NAME);
        if (font != null)
            setFont(font);
    }
}
