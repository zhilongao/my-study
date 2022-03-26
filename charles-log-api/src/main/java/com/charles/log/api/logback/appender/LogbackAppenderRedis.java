package com.charles.log.api.logback.appender;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.Status;

import java.util.List;

/**
 * 将日志文件写入redis
 * @param <E>
 */
public class LogbackAppenderRedis<E> implements Appender<E> {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void doAppend(E event) throws LogbackException {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void addStatus(Status status) {

    }

    @Override
    public void addInfo(String msg) {

    }

    @Override
    public void addInfo(String msg, Throwable ex) {

    }

    @Override
    public void addWarn(String msg) {

    }

    @Override
    public void addWarn(String msg, Throwable ex) {

    }

    @Override
    public void addError(String msg) {

    }

    @Override
    public void addError(String msg, Throwable ex) {

    }

    @Override
    public void addFilter(Filter<E> newFilter) {

    }

    @Override
    public void clearAllFilters() {

    }

    @Override
    public List<Filter<E>> getCopyOfAttachedFiltersList() {
        return null;
    }

    @Override
    public FilterReply getFilterChainDecision(E event) {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }
}
