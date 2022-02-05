package de.mvitz.jndi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class ObjectWriter {

    public static void main(String[] args) throws Exception {
        var map = new HashMap<String, String>();
        map.put("name", "Michael Vitz");
        map.put("organziation", "innoQ Deutschland GmbH");
        write("map", map);

        write("person", new Person("Michael Vitz"));
    }

    private static void write(String name, Serializable object) throws IOException {
        try (var fout = new FileOutputStream("./" + name + ".obj");
             var out = new ObjectOutputStream(fout)) {
            out.writeObject(object);
        }
    }
}
