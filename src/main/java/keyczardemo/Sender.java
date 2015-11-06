package keyczardemo;

import org.keyczar.Encrypter;
import org.keyczar.SignedSessionEncrypter;
import org.keyczar.Signer;
import org.keyczar.exceptions.KeyczarException;

import java.nio.charset.Charset;
import java.util.Base64;

public class Sender {
    /**
     * Path to the public crypt keys from receiver.
     */
    private static final String CRYPT_KEYS = "src/main/resources/receiver/public/crypt";
    /**
     * Path to the private sign keys from sender.
     */
    private static final String SIGN_KEYS = "src/main/resources/sender/secret/sign";

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static void main(String[] args) throws KeyczarException {
        Encrypter encrypter = new Encrypter(CRYPT_KEYS);
        Signer signer = new Signer(SIGN_KEYS);

        // Encrypt message and sign it
        SignedSessionEncrypter signedSessionEncrypter = new SignedSessionEncrypter(encrypter, signer);
        String session = signedSessionEncrypter.newSession();
        byte[] data = signedSessionEncrypter.encrypt("Hello Keyczar!".getBytes(UTF_8));

        // Send session material and data to receiver
        System.out.println("Session material: " + session);
        System.out.println("Data: " + Base64.getEncoder().encodeToString(data));
    }
}
