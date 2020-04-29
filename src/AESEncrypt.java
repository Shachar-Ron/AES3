import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class AESEncrypt  {
    private byte[][] messageAsNby4Matrix;
    private final int Nb = 4;
    String outputFilePath;
    byte[] cipherText;
    byte[] plainText;
    byte[][] state;
    byte[][] K1;
    byte[][] K2;
    byte[][] K3;

    public AESEncrypt(byte[] plainText,byte[] key) {
        state = new byte[Nb][Nb];
        this.plainText=plainText;
        byte[] keys =  key;
        initKeys(keys);
        messageAsNby4Matrix = convertplainText2(plainText);
    }


    public AESEncrypt(String keysPath, String inputFilePath, String outputFilePath) {
        this.outputFilePath = outputFilePath;
        state = new byte[Nb][Nb];
        byte[] keys =  readFileskeyPath(keysPath);
        initKeys(keys);
        readFilesinputFilePath(inputFilePath);
        messageAsNby4Matrix = convertplainText2(plainText);
    }
    //----------------------------------------------------------------------------------
    private byte[] readFileskeyPath(String keyPath) {
        byte[] keys = new byte[0];
        Path keyPathObject = Paths.get(keyPath);
        try {
            keys = Files.readAllBytes(keyPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the splitKeys file.");
        }
        return keys;
    }
    private void initKeys(byte[] keys){
        K1= new byte[Nb][Nb];
        K2= new byte[Nb][Nb];;
        K3= new byte[Nb][Nb];

        //-----init K1-----
        byte [] key1= Arrays.copyOfRange(keys, 0, 16);
        int counter = 0;
        for (int i = 0; i < Nb; i++) {
            for (int j = 0; j < Nb; j++) {
                K1[j][i] = key1[counter++];
            }
        }
        //-----init K2-----
        byte [] key2= Arrays.copyOfRange(keys, 16, 32);
        counter = 0;
        for (int i = 0; i < Nb; i++) {
            for (int j = 0; j < Nb; j++) {
                K2[j][i] = key2[counter++];
            }
        }
        //-----init K3-----
        byte [] key3= Arrays.copyOfRange(keys, 32, 48);
        counter = 0;
        for (int i = 0; i < Nb; i++) {
            for (int j = 0; j < Nb; j++) {
                K3[j][i] = key3[counter++];
            }
        }
    }

    private void readFilesinputFilePath(String cipherPath) {
        Path cipherPathObject = Paths.get(cipherPath);
        try {
            plainText = Files.readAllBytes(cipherPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the cipher file.");
        }
        //messageAsNby4Matrix = convertplainText(plainText);
    }

    private byte[][] convertplainText2(byte[] plainText){
        byte[][] temp = new byte[plainText.length/16][16];
        int start = 0;
        for (int i = 0 ; i < temp.length; i++){
            //temp[i] = Arrays.copyOfRange(plainText,i*16,i*4+16);
            temp[i] = Arrays.copyOfRange(plainText, start, start + 16);
            start += 16;
        }
        return temp;
    }



    private byte[][] convertplainText(byte[] plainText){
        byte[][] temp = new byte[plainText.length/4][4];
        for (int i = 0 ; i < plainText.length/4; i++)
            temp[i] = Arrays.copyOfRange(plainText,i*4,i*4+4);
        return temp;
    }
    public void Encrypt() {
        cipherText = new byte[plainText.length];
        int pos = 0;
        for (byte[] originalInChunk : messageAsNby4Matrix) {
           int counter = 0;
            for (int i = 0; i < Nb; i++) {
                for (int j = 0; j < Nb; j++) {
                    state[j][i] = originalInChunk[counter++];
                }
            }
            encryptionAlgo();


            for (int j = 0; j < 4; j++) {
                for (int h = 0; h < 4; h++) {
                    cipherText[pos] = state[h][j];
                    pos++;
                }
            }
        }
        WriteToFile();
    }

    public byte[] EncryptForBreak() {
        cipherText = new byte[plainText.length];
        int pos = 0;
        for (byte[] originalInChunk : messageAsNby4Matrix) {
            int counter = 0;
            for (int i = 0; i < Nb; i++) {
                for (int j = 0; j < Nb; j++) {
                    state[j][i] = originalInChunk[counter++];
                }
            }
            encryptionAlgo();
            for (int j = 0; j < 4; j++) {
                for (int h = 0; h < 4; h++) {
                    cipherText[pos] = state[h][j];
                    pos++;
                }
            }
        }
        return cipherText;
    }

    private void encryptionAlgo(){
        shiftCol();
        addRoundKey(state, K1);
        shiftCol();
        addRoundKey(state, K2);
        shiftCol();
        addRoundKey(state, K3);
    }
    private void shiftCol() {
        byte[][] temp2= new byte[4][4];
        temp2[0][0]=state[0][0];
        temp2[1][0]=state[1][0];
        temp2[2][0]= state[2][0];
        temp2[3][0]= state[3][0];

        temp2[0][1]=state[1][1];
        temp2[1][1]=state[2][1];
        temp2[2][1]=state[3][1];
        temp2[3][1]= state[0][1];

        temp2[0][2]=state[2][2];
        temp2[1][2]=state[3][2];
        temp2[2][2]=state[0][2];
        temp2[3][2]=state[1][2];

        temp2[0][3]=state[3][3];
        temp2[1][3]=state[0][3];
        temp2[2][3]=state[1][3];
        temp2[3][3]=state[2][3];

        for(int i=0;i<state.length;i++){
            for (int j=0;j<temp2.length;j++){
                state[i][j]=temp2[i][j];
            }
        }

    }

//----------------------------------------------------------------------


    private void addRoundKey(byte[][] val1, byte[][] val2) {

        //byte[][] roundedArray = new byte[4][4];
        for (int c = 0; c < Nb; c++)
            for (int r = 0; r < 4; r++)
                state[r][c] = (byte)(val1[r][c] ^ val2[r][c]);
    }
    private void WriteToFile() {
        byte[] WriteToFile = cipherText;
        File file = new File(outputFilePath);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(WriteToFile);
            os.close();
        } catch (Exception e) {
            System.out.println("Exception to write to file.");
        }
    }

}
