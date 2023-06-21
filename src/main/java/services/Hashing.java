package services;

import interfaces.ITask;

import java.io.File;
import java.io.FileInputStream;
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
    public String getTaskName() {
        return "Hash de um arquivo";
    }

    @Override
    public String execute() {
        try {
            return this.doHash(this.algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String doHash(HashingAlgorithm hashAlgo) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = switch (hashAlgo) {
            case MD5 -> MessageDigest.getInstance("MD5");
            case SHA1 -> MessageDigest.getInstance("SHA-1");
            case SHA256 -> MessageDigest.getInstance("SHA-256");
            case SHA512 -> MessageDigest.getInstance("SHA-512");
        };
        FileInputStream fis = new FileInputStream(fileToBeHashed);
        DigestInputStream dis = new DigestInputStream(fis, md);

        byte[] buffer = new byte[8192];
        while (dis.read(buffer) != -1) {
        }

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
