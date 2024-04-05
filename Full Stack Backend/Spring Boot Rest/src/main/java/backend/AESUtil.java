package backend;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"; // CBC mode with PKCS5 padding

    // Constant AES key (16 bytes)
    private static final byte[] AES_KEY = {
            0x54, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x46,
            0x69, 0x78, 0x65, 0x64, 0x4B, 0x65, 0x79, 0x31
    };

    // Constant Initialization Vector (IV) (16 bytes)
    private static final byte[] IV = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    };

    private static final SecretKeySpec keySpec = new SecretKeySpec(AES_KEY, AES_ALGORITHM);

    public static String encrypt(String data) throws Exception {
        // Trim input data to remove any leading or trailing whitespace
        data = data.trim();

        SecretKeySpec key = keySpec;
        IvParameterSpec iv = new IvParameterSpec(IV);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        return encryptedText;
    }
    public static String decrypt(String encryptedText) throws Exception {
        // Decode the base64-encoded ciphertext
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

        // Initialize the cipher for decryption
        SecretKeySpec key = keySpec;
        IvParameterSpec iv = new IvParameterSpec(IV);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        // Decrypt the ciphertext
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Convert the decrypted bytes to a string
        String decryptedText = new String(decryptedBytes);

        return decryptedText;
    }
}
