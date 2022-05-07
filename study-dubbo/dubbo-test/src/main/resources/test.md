测试案例
    127.0.0.1:8084/rpc


Content-Type: application/json

参数1
{
    "attachment": {
        "key1": "val1",
        "key2": "val2",
        "product_type": "API"
    },
    "params": {
        "interface": "org.charles.study.common.api.ProviderService",
        "method": "printMessage",
        "values": [
            {
                "type": "java.lang.String",
                "value": "hello"
            }
        ]
    }
}

参数2
{
    "attachment": {
        "key1": "val1",
        "key2": "val2",
        "product_type": "API"
    },
    "params": {
        "interface": "org.charles.study.common.api.ProviderService",
        "method": "pay",
        "values": [
            {
                "type": "org.charles.study.common.params.PayParams",
                "value": {
                    "id": 111,
                    "mount": 12.34,
                    "desc": "测试的"
                }
            }
        ]
    }
}