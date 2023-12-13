package com.gukkey.easytorrent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Checksum {

    /**
     * Calculates the SHA-1 hash of a file.
     *
     * @param fileName The path to the file to calculate the hash for.
     * @return The SHA-1 hash of the file as a hexadecimal string, or null if the
     *         file cannot be found or read.
     */

    public static String getSha1Hash(String fileName) {
        byte[] bytes = createChecksum(fileName);
        String sha1Hash = convertByteArrayToHexString(bytes);
        return sha1Hash;
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes The byte array to convert.
     * @return The hexadecimal string representation of the byte array.
     */

    private static String convertByteArrayToHexString(byte[] bytes) {
        final String HEX_ARRAY = "0123456789abcdef";
        if (bytes == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * bytes.length);
        for (Byte b : bytes) {
            hex.append(HEX_ARRAY.charAt((b & 0xF0) >> 4))
                    .append(HEX_ARRAY.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    /**
     * Generates the checksum of a file using the SHA-1 algorithm.
     *
     * @param fileName The path to the file to generate the checksum for.
     * @return The checksum of the file as a byte array.
     * @throws IOException              If an error occurs while reading the file.
     * @throws NoSuchAlgorithmException If the SHA-1 algorithm is not available on
     *                                  the system.
     * @throws FileNotFoundException    If the file cannot be found.
     */

    private static byte[] createChecksum(String fileName) {
        try {
            InputStream fis = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("SHA1");
            int numRead;
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
            fis.close();
            return complete.digest();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
