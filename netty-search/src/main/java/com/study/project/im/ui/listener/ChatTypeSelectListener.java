package com.study.project.im.ui.listener;

import com.alibaba.fastjson.JSONObject;
import com.study.project.im.common.LogUtil;
import com.study.project.im.ui.CommonUtils;
import com.study.project.im.ui.ImUi;
import com.study.project.im.ui.po.ChatTypeItem;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatTypeSelectListener implements ActionListener {

    private ImUi imUi;

    public ChatTypeSelectListener(ImUi imUi) {
        this.imUi = imUi;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        LogUtil.log("更改消息类型");
        ChatTypeItem typeItem = (ChatTypeItem) this.imUi.getMsgTypeBox().getSelectedItem();
        LogUtil.log("已经选中:" + JSONObject.toJSONString(typeItem));
        int type = typeItem.getType();
        if (1 == type) {
            // 刷新用户
            CommonUtils.refreshUser(this.imUi.getComboBox());
        } else if (2 == type) {
            // 刷新群组
            CommonUtils.refreshGroup(this.imUi.getComboBox());
        }
    }
}
