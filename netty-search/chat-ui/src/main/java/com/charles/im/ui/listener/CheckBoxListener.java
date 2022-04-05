package com.charles.im.ui.listener;


import com.charles.im.ui.ImUi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckBoxListener implements ActionListener {

    private ImUi imUi;

    public CheckBoxListener(ImUi imUi) {
        this.imUi = imUi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        String command = source.getActionCommand();
        boolean selected = source.isSelected();
        if (selected) {
            this.imUi.getGroupUser().add(command);
        } else {
            this.imUi.getGroupUser().remove(command);
        }
    }

}
