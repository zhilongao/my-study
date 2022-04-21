package com.charles.dubbo.test.controller;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.io.Bytes;
import com.alibaba.dubbo.common.serialize.Cleanable;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.remoting.buffer.ChannelBufferOutputStream;
import com.alibaba.dubbo.remoting.transport.CodecSupport;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.fastjson.JSONObject;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.alibaba.dubbo.rpc.protocol.dubbo.DubboCodec.DUBBO_VERSION;


@RestController
@RequestMapping
public class RpcController {

    @Autowired
    ApplicationContext context;


    @PostMapping("/rpc")
    public String commit(@RequestBody JSONObject params) {
        // 上下文参数
        JSONObject attachment = params.getJSONObject("attachment");
        Set<String> keys = attachment.keySet();
        for (String key : keys) {
            RpcContext.getContext().getAttachments().put(key, attachment.getString(key));
        }
        // 接口参数
        JSONObject businessParams = params.getJSONObject("params");
        //
        // 接口路径 方法名称
        // todo 如何动态加载

        return "ok";
    }

    public static void main(String[] args) {
        InputStream is = null;
        OutputStream os = null;
        try {
            Socket socket = new Socket("127.0.0.1", 31020);
            is = socket.getInputStream();
            os = socket.getOutputStream();
            // 发送请求
            ChannelBuffer buffer = encode();
            byte[] array = buffer.array();
            os.write(array);
            os.flush();
            // 读取响应
            byte[] data1 = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(data1)) != -1) {
                String message1 = new String(data1, 0, len);
                sb.append(message1);
            }
            System.err.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static final int HEADER_LENGTH = 16;
    static final short MAGIC = (short) 0xdabb;
    static final byte MAGIC_HIGH = Bytes.short2bytes(MAGIC)[0];
    static final byte MAGIC_LOW = Bytes.short2bytes(MAGIC)[1];

    static final byte FLAG_REQUEST = (byte) 0x80;
    static final byte FLAG_TWOWAY = (byte) 0x40;
    static final byte FLAG_EVENT = (byte) 0x20;
    static final int SERIALIZATION_MASK = 0x1f;

    public static ChannelBuffer encode(RpcInvocation inv, String urlStr) {
        com.alibaba.dubbo.remoting.buffer.ChannelBuffer buffer =
                com.alibaba.dubbo.remoting.buffer.ChannelBuffers.dynamicBuffer(1024);
        URL url = URL.valueOf(urlStr);
        Serialization serialization = CodecSupport.getSerialization(url);
        // build send data
        // header
        byte[] header = new byte[HEADER_LENGTH];
        // set magic number
        Bytes.short2bytes(MAGIC, header);
        // set request and serialization flag.
        header[2] = (byte) (FLAG_REQUEST | serialization.getContentTypeId());
        header[2] |= FLAG_TWOWAY;
        // set request id.
        Bytes.long2bytes(1, header, 4);

        // encode request data.
        int savedWriteIndex = buffer.writerIndex();
        buffer.writerIndex(savedWriteIndex + HEADER_LENGTH);
        ChannelBufferOutputStream bos = new ChannelBufferOutputStream(buffer);
        try {
            ObjectOutput out = serialization.serialize(url, bos);
            out.writeUTF(inv.getAttachment(Constants.DUBBO_VERSION_KEY, DUBBO_VERSION));
            out.writeUTF(inv.getAttachment(Constants.PATH_KEY));
            out.writeUTF(inv.getAttachment(Constants.VERSION_KEY));
            out.writeUTF(inv.getMethodName());
            out.writeUTF(ReflectUtils.getDesc(inv.getParameterTypes()));
            out.writeObject("hello,world");
            out.writeObject(inv.getAttachments());
            out.flushBuffer();
            if (out instanceof Cleanable) {
                ((Cleanable) out).cleanup();
            }
            bos.flush();
            bos.close();
            int len = bos.writtenBytes();
            // checkPayload(channel, len);
            Bytes.int2bytes(len, header, 12);
            // write
            buffer.writerIndex(savedWriteIndex);
            buffer.writeBytes(header); // write header.
            buffer.writerIndex(savedWriteIndex + HEADER_LENGTH + len);
            ChannelBuffers.wrappedBuffer(buffer.toByteBuffer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static ChannelBuffer encode() {
        com.alibaba.dubbo.remoting.buffer.ChannelBuffer buffer =
                com.alibaba.dubbo.remoting.buffer.ChannelBuffers.dynamicBuffer(1024);
        URL url = URL.valueOf("dubbo://172.16.1.28:31020/org.charles.study.common.api.ProviderService?anyhost=true&application=dubbo-reference&check=false&codec=dubbo&dubbo=2.6.2&generic=false&heartbeat=60000&interface=org.charles.study.common.api.ProviderService&methods=printMessage,pay&pid=2380&register.ip=172.16.1.28&remote.timestamp=1650433318734&side=consumer&timestamp=1650433344945");
        Serialization serialization = CodecSupport.getSerialization(url);
        // header.
        byte[] header = new byte[16];
        // set magic number.
         Bytes.short2bytes((short) 0xdabb, header);
        // set request and serialization flag.
        header[2] = (byte) ((byte) 0x80 | serialization.getContentTypeId());
        header[2] |= (byte) 0x40;
//        // set request id.
        Bytes.long2bytes(1, header, 4);
//
        int savedWriteIndex = buffer.writerIndex();
        buffer.writerIndex(savedWriteIndex + 16);
        ChannelBufferOutputStream bos = new ChannelBufferOutputStream(buffer);
        try {
            ObjectOutput out = serialization.serialize(url, bos);
            out.writeUTF("2.6.2");
            out.writeUTF("org.charles.study.common.api.ProviderService");
            out.writeUTF("0.0.0");
            out.writeUTF("printMessage");
            out.writeUTF("Ljava/lang/String");
            out.writeObject("hello,world");
            JSONObject map1 = new JSONObject();
            map1.put("path", "org.charles.study.common.api.ProviderService");
            map1.put("product_type", "api");
            map1.put("interface", "org.charles.study.common.api.ProviderService");
            map1.put("version", "0.0.0");
            out.writeObject(map1);
            out.flushBuffer();
            if (out instanceof Cleanable) {
                ((Cleanable) out).cleanup();
            }
            bos.flush();
            bos.close();
            int len = bos.writtenBytes();
          //  checkPayload(channel, len);
            Bytes.int2bytes(len, header, 12);
            // write
            buffer.writerIndex(savedWriteIndex);
            buffer.writeBytes(header); // write header.
            buffer.writerIndex(savedWriteIndex + 16 + len);
            ChannelBuffer channelBuffer = ChannelBuffers.wrappedBuffer(buffer.toByteBuffer());
            System.err.println(channelBuffer);
            return channelBuffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static RpcInvocation buildInvocation() {
        RpcInvocation invocation = new RpcInvocation();
        invocation.setMethodName("printMessage");
        invocation.setArguments(new String[]{"hello,world"});
        invocation.setParameterTypes(new Class<?>[]{String.class});
        Map<String, String> attachments = new HashMap<>();
        attachments.put("path", "org.charles.study.common.api.ProviderService");
        attachments.put("product_type", "api");
        attachments.put("interface", "org.charles.study.common.api.ProviderService");
        attachments.put("version", "0.0.0");
        invocation.addAttachments(attachments);
        // 还是需要传递依赖信息
        return invocation;
    }

    public static class Request implements Serializable {
        private String interfaceName;
        private String methodName;
        private Object[] args;

        public String getInterfaceName() {
            return interfaceName;
        }

        public void setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }


    public static class SendMessage {
        private int id;
        private String version;
        private Boolean twoway;
        private Boolean event;
        private Boolean broken;
        private V1RpcInvocation data;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Boolean getTwoway() {
            return twoway;
        }

        public void setTwoway(Boolean twoway) {
            this.twoway = twoway;
        }

        public Boolean getEvent() {
            return event;
        }

        public void setEvent(Boolean event) {
            this.event = event;
        }

        public Boolean getBroken() {
            return broken;
        }

        public void setBroken(Boolean broken) {
            this.broken = broken;
        }

        public V1RpcInvocation getData() {
            return data;
        }

        public void setData(V1RpcInvocation data) {
            this.data = data;
        }
    }

    public static class V1RpcInvocation {
        private String methodName;
        private String[] parameterTypes;
        private String[] arguments;
        private JSONObject attachments;

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String[] getParameterTypes() {
            return parameterTypes;
        }

        public void setParameterTypes(String[] parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        public String[] getArguments() {
            return arguments;
        }

        public void setArguments(String[] arguments) {
            this.arguments = arguments;
        }

        public JSONObject getAttachments() {
            return attachments;
        }

        public void setAttachments(JSONObject attachments) {
            this.attachments = attachments;
        }
    }




    // 1. 连接到zookeeper
    // 2. 获取到接口服务列表
    // 3. 选择特定的接口,去调用
    // 4. 调用方式的选择(telnet  接口调用)
    //        telnet 不需要依赖jar包
    //        接口调用 需要提前引入jar包(但是不知道需要使用哪些jar包)

}
