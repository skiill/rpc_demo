package com.xsj.importer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;


public class RPCImporter<S> {

    public S importer(final Class<?> serverClass, final InetSocketAddress address){
        return (S) Proxy.newProxyInstance(serverClass.getClassLoader(),new Class<?>[]{
                serverClass.getInterfaces()[0]},new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectOutputStream outputStream = null;
                ObjectInputStream inputStream = null;

                try {
                    socket = new Socket();
                    socket.connect(address);
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeUTF(serverClass.getName());
                    outputStream.writeUTF(method.getName());
                    outputStream.writeObject(method.getParameterTypes());
                    outputStream.writeObject(args);
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    return inputStream.readObject();
                }finally {
                    if (outputStream !=null){
                        outputStream.close();
                    }
                }

            }
        });
    }
}
