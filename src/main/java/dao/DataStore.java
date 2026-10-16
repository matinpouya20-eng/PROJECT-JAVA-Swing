package dao;

import bussiness.Universitysystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public final class DataStore {
    private DataStore() { }

    public static void save(Universitysystem university, String fileName) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(university);
        }
    }

    public static Universitysystem load(String fileName)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            Object object = input.readObject();
            if (object instanceof Universitysystem university) {
                return university;
            }
            throw new IOException("فایل، اطلاعات یک سیستم دانشگاهی معتبر ندارد.");
        }
    }
}
