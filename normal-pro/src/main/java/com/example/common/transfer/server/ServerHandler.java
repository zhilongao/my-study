package com.example.common.transfer.server;

import com.example.common.transfer.common.FileLogInfo;
import com.example.common.transfer.common.RecordHandler;
import com.example.common.transfer.common.ConfigUtils;
import com.example.common.transfer.common.LogsUtil;
import com.example.common.transfer.common.StreamTool;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ServerHandler implements Runnable{

    private RecordHandler recordHandler = new RecordHandler();

    private Socket socket;


    public ServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        System.out.println("FileServer accepted connection "+ socket.getInetAddress()+ ":"+ socket.getPort());
        try {
            // 1. 首先获取到客户端发送过来的协议数据 协议格式内容：Content-Length=143253434;filename=xxx.3gp;sourceid=13123over
            InputStream is = socket.getInputStream();
            String head = StreamTool.readLine(is);
            LogsUtil.info("FileServer head:" + head);
            if(head.length() > 0) {
                // 1.2 下面从协议数据中提取各项参数值
                String[] items = head.split(";");
                String fileLength = items[0].substring(items[0].indexOf("=") + 1);
                String fileName = items[1].substring(items[1].indexOf("=") + 1);
                String sourceId = items[2].substring(items[2].indexOf("=") + 1);
                // 1.3 生成资源id，如果需要唯一性，可以采用UUID
                long id = System.currentTimeMillis();
                FileLogInfo log = null;
                if(!"".equals(sourceId)) {
                    id = Long.parseLong(sourceId);
                    // 查找上传的文件是否存在上传记录
                    log = recordHandler.find(id);
                }
                File file = null;
                int position = 0;
                // 1.4 如果上传的文件不存在上传记录,为文件添加跟踪记录
                if(log == null) {
                    // 设置存放的位置与当前应用的位置有关
                    String basePath = ConfigUtils.get("file.store.base.path");
                    File dir = new File(basePath);
                    if(!dir.exists()) {
                        dir.mkdirs();
                    }
                    file = new File(dir, fileName);
                    // 如果上传的文件发生重名，然后进行改名
                    if(file.exists()) {
                        fileName = fileName.substring(0, fileName.indexOf(".") - 1) + dir.listFiles().length + fileName.substring(fileName.indexOf("."));
                        file = new File(dir, fileName);
                    }
                    recordHandler.save(id, file);
                    // 如果上传的文件存在上传记录,读取上次的断点位置
                } else {
                    LogsUtil.info("FileServer have exits log not null");
                    // 从上传记录中得到文件的路径
                    file = new File(log.getPath());
                    if(file.exists()) {
                        File logFile = new File(file.getParentFile(), file.getName()+".log");
                        if(logFile.exists()) {
                            Properties properties = new Properties();
                            properties.load(new FileInputStream(logFile));
                            //读取断点位置
                            position = Integer.valueOf(properties.getProperty("length"));
                        }
                    }
                }
                // ***************************上面是对协议头的处理，下面正式接收数据 ***************************************
                // 1.5 向客户端请求传输数据
                String protocolSuffix = ConfigUtils.get("protocol.suffix");
                OutputStream outStream = socket.getOutputStream();
                String response = "sourceid=" + id + ";position=" + position + protocolSuffix;
                // 1.6 服务器收到客户端的请求信息后，给客户端返回响应信息：sourceid=1274773833264;position=position sourceid由服务生成，唯一标识上传的文件，position指示客户端从文件的什么位置开始上传
                outStream.write(response.getBytes());
                outStream.flush();

                RandomAccessFile fileOutStream = new RandomAccessFile(file, "rwd");
                // 设置文件长度
                if(position == 0) {
                    fileOutStream.setLength(Integer.valueOf(fileLength));
                }
                // 移动文件指定的位置开始写入数据
                fileOutStream.seek(position);
                byte[] buffer = new byte[1024];
                int len = -1;
                int length = position;
                // 从输入流中读取数据写入到文件中，并将已经传入的文件长度写入配置文件，实时记录文件的最后保存位置
                while((len = is.read(buffer)) != -1) {
                    fileOutStream.write(buffer, 0, len);
                    length += len;
                    Properties properties = new Properties();
                    properties.put("length", String.valueOf(length));
                    FileOutputStream logFile = new FileOutputStream(new File(file.getParentFile(), file.getName()+".log"));
                    //实时记录文件的最后保存位置
                    properties.store(logFile, null);
                    logFile.close();
                }
                // 如果长传长度等于实际长度则表示长传成功
                if(length == fileOutStream.length()){
                    recordHandler.delete(id);
                }
                fileOutStream.close();
                is.close();
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
