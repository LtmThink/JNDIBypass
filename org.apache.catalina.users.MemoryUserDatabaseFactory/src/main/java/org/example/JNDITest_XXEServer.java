package org.example;


import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDITest_XXEServer {
    public static void main(String[] args) throws NamingException {
        String uri = "rmi://127.0.0.1:1100/xxe";
        InitialContext initialContext = new InitialContext();
        initialContext.lookup(uri);
    }

}

