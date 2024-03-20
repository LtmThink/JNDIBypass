package org.example;


import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDITest {
    public static void main(String[] args) throws Exception {
        Object object=new InitialContext().lookup("ldap://127.0.0.1:4444/dc=example,dc=com");
    }

}

