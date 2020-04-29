import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
class AESHack {
    private byte[] keys;
    private final int Nb = 4;
    String outputFilePath;
    byte[] cipherText;
    byte[] plainText;
    byte[][] state;
    byte[] K1;
    byte[] K2;
    byte[] K3;
    byte[][] plainTextMatrics;
    byte[][] cipherTextMatrics;
    String keyPath;
    String plainTextPath;
    String cipherTextPath;


    public AESHack(String plainTextPath, String cipherTextPath, String outputFilePath) {
        this.plainTextPath=plainTextPath;
        this.cipherTextPath=cipherTextPath;

        this.outputFilePath = outputFilePath;
        state = new byte[Nb][Nb];
        K1 = new byte[16];
        K2 = new byte[16];
        K3 = new byte[16];
        readPlainTextFiles(plainTextPath);
        readCipherTextPathFiles(cipherTextPath);
        keys = new byte[48];
        initPlainText();
        initCipherText();
    }

    private void readPlainTextFiles(String plainTextPath) {
        Path plainTextPathObject = Paths.get(plainTextPath);
        try {
            plainText = Files.readAllBytes(plainTextPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the plain text file.");
        }


    }

    private void readCipherTextPathFiles(String cipherTextPath) {
        Path cipherTextPathObject = Paths.get(cipherTextPath);
        try {
            cipherText = Files.readAllBytes(cipherTextPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the cipher file.");
        }
    }

    public void initPlainText() {
        plainTextMatrics = new byte[4][4];
        int pos = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                plainTextMatrics[j][i] = plainText[pos];
                pos++;
            }
        }
    }

    public void initCipherText() {
        cipherTextMatrics = new byte[4][4];
        int pos = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                cipherTextMatrics[j][i] = cipherText[pos];
                pos++;
            }
        }
    }


    public void Hack() {

        //init new array of random keys M'.
        new Random().nextBytes(K1);
        new Random().nextBytes(K2);
        byte[] keys = initTempArray(Arrays.copyOfRange(cipherText, 0, 16));

        byte[] p = Arrays.copyOfRange(plainText, 0, 16);

        AESEncrypt encrypt = new AESEncrypt(p,keys);
        K3 =encrypt.EncryptForBreak();
        byte[] out =initTempArray(K3);

        WriteResults(out);
    }

    private byte[] initTempArray(byte[] text){
        byte[] keys = new byte[48];

            int i=0;
            for (int j = 0; j < K1.length; j++) {
                keys[i++] = K1[j];
            }
            for (int j = 0; j < K2.length; j++) {
                keys[i++] = K2[j];
            }
            for (int j = 0; j < 16; j++) {
                keys[i++] = text[j];
            }

        return keys;
    }


    private void WriteResults(byte[] out) {
        String path = outputFilePath;
        byte[] WriteResults = out;
        File file = new File(path);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(WriteResults);
            os.close();
        } catch (Exception e) {
            System.out.println("Exception to write to file.");
        }
    }



}
