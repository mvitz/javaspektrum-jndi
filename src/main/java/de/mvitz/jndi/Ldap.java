package de.mvitz.jndi;

import javax.naming.Binding;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import java.util.Hashtable;

import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;
import static javax.naming.Context.SECURITY_CREDENTIALS;
import static javax.naming.Context.SECURITY_PRINCIPAL;
import static javax.naming.directory.DirContext.REPLACE_ATTRIBUTE;
import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;

public class Ldap {

    public static void main(String[] args) throws NamingException {
        var env = new Hashtable<>();
        env.put(INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(PROVIDER_URL, "ldap://localhost:389/dc=mvitz,dc=de");
        env.put(SECURITY_PRINCIPAL, "cn=admin,dc=mvitz,dc=de");
        env.put(SECURITY_CREDENTIALS, "admin");

        var ctx = new InitialDirContext(env);

        var people = ctx.listBindings("ou=People");
        //var people = ctx.list("ou=People");
        while (people.hasMore()) {
            Binding person = people.next();
            //NameClassPair person = people.next();
            var attributes = ctx.getAttributes(person.getName() + ",ou=People");
            var cn = attributes.get("cn");
            System.out.println(cn.get());
        }

        System.out.println();

        System.out.println("Search example");
        var searchControls = new SearchControls();
        searchControls.setSearchScope(SUBTREE_SCOPE);
        searchControls.setReturningAttributes(new String[] { "cn" });

        var result = ctx.search("ou=People", "ou=Jedi", searchControls);
        while (result.hasMore()) {
            var next = result.next();
            System.out.println(next.getAttributes().get("cn").get());
        }

        System.out.println();

        System.out.println("Modify example");
        var newAttribute = new BasicAttribute("cn", "Leia Skywalker");
        var modification = new ModificationItem(REPLACE_ATTRIBUTE, newAttribute);
        ModificationItem[] modifications = { modification };
        ctx.modifyAttributes("uid=leia,ou=People", modifications);
        var modifiedAttribute = ctx.getAttributes("uid=leia,ou=People").get("cn");
        System.out.println(modifiedAttribute.get());
    }
}
