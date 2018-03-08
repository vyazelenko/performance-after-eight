package com.vyazelenko.perf.crypto;

import org.openjdk.jmh.annotations.*;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(3)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class RSACipherBenchmark {
    private Cipher encryptor;
    private Cipher decryptor;
    private byte[] rawBytes;
    private byte[] encryptedBytes;

    @Setup
    public void setup() throws Exception {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        KeyPair keyPair = keyGenerator.generateKeyPair();
        encryptor = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptor.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        decryptor = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptor.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        rawBytes = "The quick brown fox jumps over the lazy dog".getBytes(UTF_8);
        encryptedBytes = encryptor.doFinal(Arrays.copyOf(rawBytes, rawBytes.length));
    }

    @Benchmark
    public byte[] encrypt() throws Exception {
        byte[] input = Arrays.copyOf(rawBytes, rawBytes.length);
        return encryptor.doFinal(input);
    }

    @Benchmark
    public byte[] decrypt() throws Exception {
        byte[] input = Arrays.copyOf(encryptedBytes, encryptedBytes.length);
        return decryptor.doFinal(input);
    }
}
