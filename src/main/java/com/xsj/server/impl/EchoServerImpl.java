package com.xsj.server.impl;

import com.xsj.anno.MyService;
import com.xsj.server.EchoServer;
@MyService
public class EchoServerImpl implements EchoServer{

    public String echo(String str) {
        return str !=null ? str+"ok":"str == null";
    }
}
