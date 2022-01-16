package com.study.project.im.ui.listener;

import com.alibaba.fastjson.JSONObject;
import com.study.project.im.common.LogUtil;
import com.study.project.im.ui.ImUi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class AddGroupActionListener implements ActionListener {

    private ImUi imUi;

    public AddGroupActionListener(ImUi imUi) {
        this.imUi = imUi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField groupNameField = this.imUi.getGroupNameField();
        String groupName = groupNameField.getText();
        Set<String> groupUser = this.imUi.getGroupUser();
        LogUtil.log("创建群组");
        LogUtil.log("群名称:" + groupName);
        LogUtil.log("群组成员:" + JSONObject.toJSONString(groupUser));
    }
}
