package com.charles.im.ui.listener;


import com.charles.im.common.LogUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 更换事件监听
 */
public class ChangeSelectListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        LogUtil.info("change selected");
    }
}
