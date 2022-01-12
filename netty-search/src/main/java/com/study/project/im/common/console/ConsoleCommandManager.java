package com.study.project.im.common.console;

import com.study.project.im.common.util.Logs;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private final Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("1", new MessageConsoleCommand());
        consoleCommandMap.put("2", new CreateGroupConsoleCommand());
        consoleCommandMap.put("3", new JoinGroupConsoleCommand());
        consoleCommandMap.put("4", new QuitGroupConsoleCommand());
        consoleCommandMap.put("5", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("6", new SendGroupMessageConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            Logs.error("无法识别[" + command + "]指令,请重新输入!");
        }
    }
}
