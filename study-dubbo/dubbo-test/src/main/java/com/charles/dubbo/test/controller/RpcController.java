package com.charles.dubbo.test.controller;


import com.alibaba.fastjson.JSONObject;
import com.charles.dubbo.test.invoke.DubboInvokeService;

import com.charles.dubbo.test.po.PathNode;
import com.charles.dubbo.test.util.CuratorUtil;
import com.charles.dubbo.test.util.DefaultConfigInfo;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.net.URLDecoder;
import java.util.*;


//@RestController
@Controller
@RequestMapping
public class RpcController {

    public static final String DEFAULT_DUBBO_NAMESPACE = "dubbo";

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
        return findProviderNodes();
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
        String[] parameterTypes = {String.class.getName()};
        Object[] args = {"1"};
        if ("pay".equals(methodName)) {
            parameterTypes = new String[]{"org.charles.study.common.params.PayParams"};
            // 复杂类型的调用
            Map<String, Object> params1 = new HashMap<String, Object>();
            //params1.put("class", "org.charles.study.common.params.PayParams");
            params1.put("id", 1001);
            params1.put("mount", 2000);
            params1.put("desc", "测试的");
            args = new Object[] {params1};
        }
        Object result = dubboInvokeService.invoke(interfaceName, methodName, parameterTypes, args, attachmentMap);
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
}
