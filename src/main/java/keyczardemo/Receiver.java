package keyczardemo;

import org.keyczar.Crypter;
import org.keyczar.SignedSessionDecrypter;
import org.keyczar.Verifier;
import org.keyczar.exceptions.KeyczarException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Base64;

public class Receiver {
    /**
     * Path to the private crypt keys from receiver.
     */
    private static final String CRYPT_KEYS = "src/main/resources/receiver/secret/crypt";
    /**
     * Path to the public sign keys from sender.
     */
    private static final String VERIFY_KEYS = "src/main/resources/sender/public/sign";

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    // Use these for testing:
    // Session: ALL___pWDCBflvZq9tEYn5hxpit2BBN6z6GZyG6I_7Blu2yktlGY5kuxkpjFhGwbTDwvKF68KZOsYrxhsWsUd8REvU6OvjuLmYld19HwUsS2oYI7ROYVFnXTNFOJDPbnhzy5xzM_iUmSed5eCSG_g2p2JA-mWfDzpp4R6SmT7B96HyMCHMaTDma6mLlFeJeo8OPVqbt6B-ZcYofwNDfO28fnVUQQMdIPLQAMtLrV81d9UGoOX9MT-6tSc7Vmq2wGpamew9fkpTxTC7pVbcGtAPznAywG3q7nfggQ9x3clFbFZRjT5uKcOEzmsEi8V7lmlodFbQLj9DjEhFFIEkhUw6e049QLEQ2zS7P7VOCFtLXv3azehRjZ8MG0WEqaJ-52fzUMJccYkTojrcKAxNIJwXKwtBtUbilBeO0Worq4UP5_Zac8bvpZ-TYzaX6bYRfCKrk3Tzyq2STeZ8a7Eb-Xs2484o47kRwMOPD9mSmB1py4DcPRvHCRKukNVoMBbgmmyPMaQnv86ko_JDLaMd2HpQf56XWh_h7bELnqxF_8DnBiD3OJ_xGxvMxnSmVdaw0P2qhw9SLLpGqILeHM4luOa0EIvqmKB3-NS5sT9h8XCn82LUVf4qcfqEpLZcGYtho2QW8BPSYlJ0O5Wd08Dh6bgktGnyNWhA72YS2nblHhf4Y7DXBfLg
    // Data: AD2aGHcAAAA5ABPIN3k1cF/svh0X7inqOyxyHWW8krTdLRpGPkUOEQtn4dm+6gIg1XUG0/U4xkb7m6Kep/zr34XjGAJsH5g+vK/m+pakvio3+BCrVfjkuByEhu/kIoaSSJ5RRph+tH/ad95unT7bfu/FGOwR+3YiiZbiyCfokV4wnQzifK33CLb50FsTwX4Rc9a2zazlLBa6X36Cgv8M8EmcF575n7On5PDyrhgth0ua8hAjmjb8eum9JHUoxI4vwH/IFDGSd083FWDp3H5fuFfSZewCfixa/FXptPPbXWrT1JPp9ntpi/kcRdtXEk1nFwreR7V8jOCv8NON/vgIbOQpQNvETfrkhkIy7EyMn7KKTVp1tYBDl7DSh3ud283iwazTyd8I5SCECWxBzS8AKOmASz7K6ZGmwzhfSZ+kqHxkiuzIlujByDlcOBYgRiwlCZvw8/IGcxalBVLc+3Yz26yF3xiQXpYxZGQs2xQ2QJQJWt015b9sbzyqcvb0a9B63R9FWx1p6iFFHnAj6Jq+EltA6p+jiPCg8mDRobp+g5Za9BnOFuo06H+XNOxVYjVQdt5YNQrTBvEAq5nzmuDFt0ZAuCRqVwePSueJUzx5YlNbXTCrbMsln5PpfsPkq/6WeGpP1firRCHry3ZmRW43j8F4O/wQAsy+7yc2Jg7AaYRN6KPCJUQoC2t1/YTqwaU21EzaYeYklFD/ee4/8VIlY2GhoTBFCaTUBpdyFrt9sTn28JZuhT42ID45NPCpP30HWFE=

    public static void main(String[] args) throws KeyczarException, IOException {
        // Receive session and data from sender
        System.out.print("Enter session material: ");
        String sessionMaterial = readFromStdIn();
        System.out.print("Enter data: ");
        byte[] data = Base64.getDecoder().decode(readFromStdIn());

        // Verify signature and decrypt message
        Crypter crypter = new Crypter(CRYPT_KEYS);
        Verifier verifier = new Verifier(VERIFY_KEYS);
        SignedSessionDecrypter signedSessionDecrypter = new SignedSessionDecrypter(crypter, verifier, sessionMaterial);
        byte[] decrypt = signedSessionDecrypter.decrypt(data);

        System.out.println("Message: " + new String(decrypt, UTF_8));
    }

    private static String readFromStdIn() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}
