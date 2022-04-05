package com.charles.im.ui.listener;



import com.charles.im.common.LogUtil;
import com.charles.im.ui.CommonUtils;
import com.charles.im.ui.ImUi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupMenuItemListener implements ActionListener {

    private ImUi imUi;

    public GroupMenuItemListener(ImUi imUi) {
        this.imUi = imUi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case CommonUtils.CREATE_GROUP_COMMAND:
                LogUtil.log("创建群组");
                this.imUi.getGroupFrame().setVisible(true);
                break;
            case CommonUtils.ADD_GROUP_COMMAND:
                LogUtil.log("加入群组");
                break;
            case CommonUtils.QUIT_GROUP_COMMAND:
                LogUtil.log("退出群组");
                break;
        }
    }
}
