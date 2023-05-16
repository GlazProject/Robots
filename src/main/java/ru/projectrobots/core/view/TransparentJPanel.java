package ru.projectrobots.core.view;

import javax.swing.*;
import java.awt.*;

public abstract class TransparentJPanel extends JPanel {
    private static final Color bgPanelColor = new Color(1.0f,1.0f,1.0f,0.0f);

    protected TransparentJPanel() {
        setOpaque(false);
        setBackground(bgPanelColor);
    }
}
