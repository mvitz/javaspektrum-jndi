package de.mvitz.jndi.remote;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Hack implements Serializable {

    public static void main(String[] args) throws Exception{
        try (var fout = new FileOutputStream("./hack.obj");
             var out = new ObjectOutputStream(fout)) {
            out.writeObject(new Hack());
        }
    }

    @Override
    public String toString() {
        return "Hacked @ " + System.currentTimeMillis();
    }
}
