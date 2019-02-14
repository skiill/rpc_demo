import com.xsj.exporter.RPCExporter;
import com.xsj.importer.RPCImporter;
import com.xsj.server.EchoServer;
import com.xsj.server.impl.EchoServerImpl;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class TestDemo {

    @Test
    public void test(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    RPCExporter.exporter("localhost",8080);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }).start();

        RPCImporter<EchoServer> importer = new RPCImporter<EchoServer>();
        EchoServer echo = importer.importer(EchoServerImpl.class,new InetSocketAddress("localhost",8080));

        System.out.println(echo.echo("ok"));

            Scanner sc = new Scanner(System.in);
            System.out.println(echo.echo(sc.next()));


    }
}
