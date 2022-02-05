package de.mvitz.jndi;

import javax.naming.Binding;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;
import static javax.naming.Context.SECURITY_CREDENTIALS;
import static javax.naming.Context.SECURITY_PRINCIPAL;

public class Ldap {

    public static void main(String[] args) throws NamingException {
        var env = new Hashtable<>();
        env.put(INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(PROVIDER_URL, "ldap://localhost:389/dc=mvitz,dc=de");
        env.put(SECURITY_PRINCIPAL, "cn=admin,dc=mvitz,dc=de");
        env.put(SECURITY_CREDENTIALS, "admin");

        var ctx = new InitialDirContext(env);

        var people = ctx.listBindings("ou=People");
        while (people.hasMore()) {
            Binding person = people.next();
            var attributes = ctx.getAttributes(person.getName() + ",ou=People");
            var cn = attributes.get("cn");
            System.out.println(cn.get());
        }
    }
}
