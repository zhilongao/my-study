#!/bin/sh
# SkyWalking Agent配置
export SW_AGENT_NAME=aop-search-demo
export SW_AGENT_COLLECTOR_BACKEND_SERVICES=127.0.0.1:11800
export SW_AGENT_SPAN_LIMIT=2000
export JAVA_AGENT=-javaagent:/usr/local/src/apache-skywalking-apm-bin-es7/agent/skywalking-agent.jar
java $JAVA_AGENT -jar aop-search-0.0.1-SNAPSHOT.jar