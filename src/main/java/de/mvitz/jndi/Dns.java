package de.mvitz.jndi;

import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;

public class Dns {

    public static void main(String[] args) throws NamingException {
        var env = new Hashtable<>();
        env.put(INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        env.put(PROVIDER_URL, "dns://8.8.8.8");

        var domain = "mvitz.de";
        String[] records = { "A", "TXT", "MX" };

        var ctx = new InitialDirContext(env);
        var attributes = ctx.getAttributes(domain, records).getAll();
        while(attributes.hasMore()) {
            var a = attributes.next();
            System.out.println(a.getID());
            var it = a.getAll();
            while (it.hasMore()) {
                System.out.println(it.next());
            }
            System.out.println();
        }
        attributes.close();
    }
}
