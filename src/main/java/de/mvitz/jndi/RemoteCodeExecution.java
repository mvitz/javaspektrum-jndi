package de.mvitz.jndi;

import javax.naming.Binding;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Map;

import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;
import static javax.naming.Context.SECURITY_CREDENTIALS;
import static javax.naming.Context.SECURITY_PRINCIPAL;

public class RemoteCodeExecution {

    public static void main(String[] args) throws NamingException {
        var env = new Hashtable<>();
        env.put(INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(PROVIDER_URL, "ldap://localhost:389/dc=mvitz,dc=de");
        env.put(SECURITY_PRINCIPAL, "cn=admin,dc=mvitz,dc=de");
        env.put(SECURITY_CREDENTIALS, "admin");

        var ctx = new InitialDirContext(env);

        System.out.println("Listing all objects");
        var objects = ctx.listBindings("ou=Objects");
        while (objects.hasMore()) {
            Binding object = objects.next();
            System.out.println("  " + object.getName() + ": " + object.getClassName());
        }
        System.out.println();

        System.out.println("HashMap");
        var map = (Map) ctx.lookup("cn=Map,ou=Objects");
        map.forEach((key, value) -> System.out.println("  " + key + ": " + value));
        System.out.println();

        System.out.println("Person");
        var person = ctx.lookup("cn=Person,ou=Objects");
        System.out.println(person);
        System.out.println(person);
        System.out.println();

        System.out.println("Hack");
        var hack = ctx.lookup("cn=Hack,ou=Objects");
        System.out.println(hack);
        System.out.println(hack);
    }
}
