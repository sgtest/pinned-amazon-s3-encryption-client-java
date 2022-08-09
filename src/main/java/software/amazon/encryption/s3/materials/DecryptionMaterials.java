package software.amazon.encryption.s3.materials;

import java.util.Collections;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import software.amazon.encryption.s3.algorithms.AlgorithmSuite;

final public class DecryptionMaterials {

    // Identifies what sort of crypto algorithms we want to use
    private final AlgorithmSuite _algorithmSuite;

    // Additional information passed into encrypted that is required on decryption as well
    // Should NOT contain sensitive information
    private final Map<String, String> _encryptionContext;

    private final byte[] _plaintextDataKey;

    private DecryptionMaterials(Builder builder) {
        this._algorithmSuite = builder._algorithmSuite;
        this._encryptionContext = builder._encryptionContext;
        this._plaintextDataKey = builder._plaintextDataKey;
    }

    static public Builder builder() {
        return new Builder();
    }

    public AlgorithmSuite algorithmSuite() {
        return _algorithmSuite;
    }

    public Map<String, String> encryptionContext() {
        return _encryptionContext;
    }

    public byte[] plaintextDataKey() {
        return _plaintextDataKey;
    }

    public SecretKey dataKey() {
        return new SecretKeySpec(_plaintextDataKey, "AES");
    }

    public Builder toBuilder() {
        return new Builder()
                .algorithmSuite(_algorithmSuite)
                .encryptionContext(_encryptionContext)
                .plaintextDataKey(_plaintextDataKey);
    }

    static public class Builder {

        private AlgorithmSuite _algorithmSuite = AlgorithmSuite.ALG_AES_256_GCM_IV12_TAG16_NO_KDF;
        private Map<String, String> _encryptionContext = Collections.emptyMap();
        private byte[] _plaintextDataKey = null;

        private Builder() {
        }

        public Builder algorithmSuite(AlgorithmSuite algorithmSuite) {
            _algorithmSuite = algorithmSuite;
            return this;
        }

        public Builder encryptionContext(Map<String, String> encryptionContext) {
            _encryptionContext = encryptionContext == null
                    ? Collections.emptyMap()
                    : Collections.unmodifiableMap(encryptionContext);
            return this;
        }

        public Builder plaintextDataKey(byte[] plaintextDataKey) {
            _plaintextDataKey = plaintextDataKey == null ? null : plaintextDataKey.clone();
            return this;
        }

        public DecryptionMaterials build() {
            return new DecryptionMaterials(this);
        }
    }
}