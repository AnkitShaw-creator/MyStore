package generate_hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ValueHash {

    private String value;

    public ValueHash() {
    }

    public ValueHash(String value) {
        this.value = value;
    }

    public String getValue() {

        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] valueArray = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            BigInteger bi = new BigInteger(1, valueArray);

            StringBuilder hashValue = new StringBuilder(bi.toString(16));

            while(hashValue.length() < 32){
                hashValue.insert(0, "0");
            }
            return hashValue.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public void setValue(String value) {
        this.value = value;
    }
}
