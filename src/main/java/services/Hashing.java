package services;

import interfaces.ITask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing implements ITask<String> {
    private final File fileToBeHashed;
    private final HashingAlgorithm algorithm;

    public Hashing(File fileToBeHashed, HashingAlgorithm algorithm) {
        this.fileToBeHashed = fileToBeHashed;
        this.algorithm = algorithm;
    }

    @Override
    public String execute() {
        try {
            return switch (algorithm) {
                case MD5 -> md5();
                case SHA1 -> sha1();
                case SHA256 -> sha256();
            };
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String md5() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(fileToBeHashed);
        DigestInputStream dis = new DigestInputStream(fis, md);

        byte[] buffer = new byte[8192];
        while (dis.read(buffer) != -1) {}

        byte[] hashBytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        String hash = sb.toString();
        dis.close();
        fis.close();

        return hash;
    }

    private String sha1() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        FileInputStream fis = new FileInputStream(fileToBeHashed);
        DigestInputStream dis = new DigestInputStream(fis, md);

        byte[] buffer = new byte[8192];
        while (dis.read(buffer) != -1) {}

        byte[] hashBytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        String hash = sb.toString();
        dis.close();
        fis.close();

        return hash;
    }

    private String sha256() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(fileToBeHashed);
        DigestInputStream dis = new DigestInputStream(fis, md);

        byte[] buffer = new byte[8192];
        while (dis.read(buffer) != -1) {}

        byte[] hashBytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        String hash = sb.toString();
        dis.close();
        fis.close();

        return hash;
    }
}
