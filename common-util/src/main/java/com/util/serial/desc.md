java常用的几种序列化方式
1. 原生序列化方式
2. hessian
3. json序列化方式fastjson jackson
4. proto

几种序列化方式的排名
序列化排名
------------序列化排名------------
stream       耗时:63
hessian      耗时:165
fastjson     耗时:170
jackson      耗时:192
proto        耗时:1120


------------反序列化排名------------
proto       耗时:13
fastjson    耗时:64
hessian     耗时:70
jackson     耗时:149
stream      耗时:200
