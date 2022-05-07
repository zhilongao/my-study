package com.charles.dubbo.test.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charles.dubbo.test.invoke.DubboInvokeService;

import com.charles.dubbo.test.po.PathNode;
import com.charles.dubbo.test.util.CuratorUtil;
import com.charles.dubbo.test.util.DefaultConfigInfo;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping
public class RpcController {

    public static final String DEFAULT_DUBBO_NAMESPACE = "dubbo";

    String pattern = "^(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]):([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";


    @Autowired
    private DubboInvokeService dubboInvokeService;


    @GetMapping("/index")
    public String index() {
        return "index";
    }


    /**
     * 配置zk信息
     * @param params params
     * @return 是否配置成功
     */
    @PostMapping("/config")
    @ResponseBody
    public boolean configZk(@RequestBody JSONObject params) {
        String zkUrl = params.getString("zkUrl");
        if (StringUtils.isEmpty(zkUrl)) {
            return false;
        }
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(zkUrl);
        boolean matches = matcher.matches();
        if (!matches) {
            return false;
        }
        DefaultConfigInfo.setZkUrl(zkUrl);
        return true;
    }


    /**
     * 查看dubbo接口信息
     * @return dubbo接口信息
     */
    @GetMapping("/path")
    @ResponseBody
    public List<PathNode> findPath() {
        return findInterfaceNames();
    }


    @GetMapping("/method")
    @ResponseBody
    public List<String> findMethods(@RequestParam("catalog") String catalog) {
        return findInterfaceMethods(catalog);
    }

    /**
     * dubbo rpc调用
     * @param params params
     * @return
     */
    @PostMapping("/rpc")
    @ResponseBody
    public String commit(@RequestBody JSONObject params) {
        // 上下文参数
        JSONObject attachment = params.getJSONObject("attachment");
        Map<String, String> attachmentMap = new HashMap<>();
        Set<String> keys = attachment.keySet();
        for (String key : keys) {
            attachmentMap.put(key, attachment.getString(key));
        }
        // 接口参数
        JSONObject businessParams = params.getJSONObject("params");
        String interfaceName = businessParams.getString("interface");
        String methodName = businessParams.getString("method");
        // 入参类型 入参处理
        JSONArray values = businessParams.getJSONArray("values");
        String[] parameterTypes = new String[values.size()];
        Object[] parameterValues = new Object[values.size()];
        for (int i = 0; i < values.size(); i++) {
            JSONObject valueItem = values.getJSONObject(i);
            String type = valueItem.getString("type");
            Object value = valueItem.get("value");
            parameterTypes[i] = type;
            parameterValues[i] = value;
        }
        Object result = dubboInvokeService.invoke(interfaceName, methodName, parameterTypes, parameterValues, attachmentMap);
        return result.toString();
    }

    public List<PathNode> findProviderNodes() {
        List<PathNode> results = new ArrayList<>();
        String nameSpace = DEFAULT_DUBBO_NAMESPACE;
        CuratorFramework framework = CuratorUtil.curatorFramework(nameSpace);
        framework.start();
        try {
            List<String> catalogs = framework.getChildren().forPath("/");
            for (String catalog : catalogs) {
                List<String> providers = framework.getChildren().forPath("/" + catalog);
                for (String provider : providers) {
                    if ("providers".equals(provider)) {
                        List<String> providerNodes = framework.getChildren().forPath("/" + catalog + "/" + provider);
                        for (String providerNode : providerNodes) {
                            String proDecodeNode = URLDecoder.decode(providerNode);
                            String nodeInfo = proDecodeNode.substring(proDecodeNode.indexOf("?") + 1);
                            String[] nodeInfoArr = nodeInfo.split("&");
                            PathNode node = new PathNode();
                            for (String nodeInfoStr : nodeInfoArr) {
                                String[] split = nodeInfoStr.split("=");
                                String key = split[0];
                                String val = split[1];
                                if ("interface".equals(key)) {
                                    node.setInterfaceName(val);
                                } else if ("methods".equals(key)) {
                                    node.setMethods(val);
                                }

                            }
                            results.add(node);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<PathNode> findInterfaceNames() {
        List<PathNode> results = new ArrayList<>();
        String nameSpace = DEFAULT_DUBBO_NAMESPACE;
        CuratorFramework framework = CuratorUtil.curatorFramework(nameSpace);
        framework.start();
        try {
            List<String> catalogs = framework.getChildren().forPath("/");
            for (String catalog : catalogs) {
                PathNode node = new PathNode();
                node.setInterfaceName(catalog);
                results.add(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<String> findInterfaceMethods(String catalog) {
        List<String> results = new ArrayList<>();
        String nameSpace = DEFAULT_DUBBO_NAMESPACE;
        CuratorFramework framework = CuratorUtil.curatorFramework(nameSpace);
        framework.start();
        try {
            String path = "/" + catalog.trim() + "/providers";
            List<String> providerNodes = framework.getChildren().forPath(path);
            for (String providerNode : providerNodes) {
                String proDecodeNode = URLDecoder.decode(providerNode);
                String nodeInfo = proDecodeNode.substring(proDecodeNode.indexOf("?") + 1);
                String[] nodeInfoArr = nodeInfo.split("&");
                for (String nodeInfoStr : nodeInfoArr) {
                    String[] split = nodeInfoStr.split("=");
                    String key = split[0];
                    String val = split[1];
                    if ("interface".equals(key)) {
                        // ingore
                    } else if ("methods".equals(key)) {
                        String[] methods = val.split(",");
                        for (String m : methods) {
                            results.add(m.trim());
                        }
                        //results.add(val);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }





    public List<String> findInterfaceMethods() {

        return null;
    }



}
