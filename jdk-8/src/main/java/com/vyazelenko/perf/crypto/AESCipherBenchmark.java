package com.vyazelenko.perf.crypto;

import org.openjdk.jmh.annotations.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class AESCipherBenchmark {
    private Cipher encryptor;
    private Cipher decryptor;
    private byte[] rawBytes;
    private byte[] encryptedBytes;
    private GCMParameterSpec parameterSpec;
    private SecretKey key;

    @Setup
    public void setup() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom());
        key = keyGenerator.generateKey();
        encryptor = Cipher.getInstance("AES/GCM/NoPadding");
        encryptor.init(Cipher.ENCRYPT_MODE, key);
        parameterSpec = encryptor.getParameters().getParameterSpec(GCMParameterSpec.class);
        decryptor = Cipher.getInstance("AES/GCM/NoPadding");
        decryptor.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        rawBytes = "The quick brown \uD83D\uDC7B jumps over the lazy dog".getBytes(UTF_8);
        encryptedBytes = encryptor.doFinal(Arrays.copyOf(rawBytes, rawBytes.length));
    }

    @Benchmark
    public byte[] encrypt() throws Exception {
        initEncryptor();
        byte[] input = Arrays.copyOf(rawBytes, rawBytes.length);
        return encryptor.doFinal(input);
    }

    private void initEncryptor() throws InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] iv = new byte[parameterSpec.getIV().length];
        ThreadLocalRandom.current().nextBytes(iv);
        parameterSpec = new GCMParameterSpec(parameterSpec.getTLen(), iv);
        encryptor.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
    }

    @Benchmark
    public byte[] decrypt() throws Exception {
        byte[] input = Arrays.copyOf(encryptedBytes, encryptedBytes.length);
        return decryptor.doFinal(input);
    }
}
