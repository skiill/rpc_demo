package com.xsj.server.impl;

import com.xsj.server.EchoServer;

public class EchoServerImpl implements EchoServer{

    public String echo(String str) {
        return str !=null ? str+"ok":"str == null";
    }
}
