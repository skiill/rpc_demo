package com.xsj.exporter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RPCExporter {
    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void exporter(String hostName,int port)throws IOException{
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(hostName,port));
        try{
            while (true)
            executor.execute(new ExporterTask(server.accept()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            server.close();
        }

    }
    private static  class ExporterTask implements Runnable {
        Socket client = null;

        public ExporterTask(Socket accept) {
            client = accept;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        public void run() {
            ObjectInputStream inputStream = null;
            ObjectOutputStream outputStream = null;

            try {
                inputStream = new ObjectInputStream(client.getInputStream());
                String interFaceName = inputStream.readUTF();
                Class<?> service = Class.forName(interFaceName);
                String methodName = inputStream.readUTF();
                Class<?>[] parameterTypes = (Class<?>[])inputStream.readObject();
                Object[] arguments= (Object[])inputStream.readObject();
                Method method = service.getMethod(methodName,parameterTypes);
                Object result = method.invoke(service.newInstance(), arguments);
                outputStream = new ObjectOutputStream(client.getOutputStream());
                outputStream.writeObject(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }finally {
                try {
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
