package com.xsj.handler;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Invocation implements Serializable{
    private Class<?> serverClass;
    private Method method;
    private Object[] args;
    public Invocation() {
    }

    public Invocation(Class<?> serverClass, Method method, Object[] args) {
        this.serverClass = serverClass;
        this.method = method;
        this.args = args;
    }

    public Class<?> getServerClass() {
        return serverClass;
    }

    public void setServerClass(Class<?> serverClass) {
        this.serverClass = serverClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "serverClass=" + serverClass +
                ", method=" + method +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
