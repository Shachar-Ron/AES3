//--------------------------args for main--------------------------------------
//--------------------------Encryption----------------------------------------
//-e -k C:\Users\shach\Desktop\test\key_long -i C:\Users\shach\Desktop\test\message_long -o  C:\Users\shach\Desktop\test\output_long
//-e -k C:\Users\shach\Desktop\test\key_short -i C:\Users\shach\Desktop\test\message_short -o  C:\Users\shach\Desktop\test\output_short
//-------------------------Decryption-------------------------------------
//-d -k C:\Users\shach\Desktop\test\key_long -i C:\Users\shach\Desktop\test\cipher_long -o  C:\Users\shach\Desktop\test\output_long
//-d -k C:\Users\shach\Desktop\test\key_short -i C:\Users\shach\Desktop\test\cipher_short -o  C:\Users\shach\Desktop\test\output_short
//------------------------Hack-----------------------------
//-b -m C:\Users\shach\Desktop\test\message_short -c C:\Users\shach\Desktop\test\cipher_short -o  C:\Users\shach\Desktop\test\output_short
//-b -m C:\Users\shach\Desktop\test\message_long -c C:\Users\shach\Desktop\test\cipher_long -o  C:\Users\shach\Desktop\test\output_long

//        String s1="箻㣞䌒⊗䪝灟떳碷㯔ᥑ⃄䫀녶看羴暉ᨒ➊ᇄ뙶☊낱狤柟᥌₞᳅ꅶ✀ꫧ↶㪂ᡇ⋍ᮗ똥⑊뮸疾㧈䩄ₛኝ灏뛫⊴涂ᜒ綛䇄뙳縋릸矤㪂၉⦙Ꮚ뽶灔볢粹拈ṕ↖ᶜ른絔릳羻粈䄑⊓ወ紊瞷㪉䍄⻄䆞ⴀ떸";
//         String s2="箻㣞䌒⊗䪝灟떳碷㯔ᥑ⃄䫀녶看羴暉ᨒ➊ᇄ뙶☊낱狤柟᥌₞᳅ꅶ✀ꫧ↶㪂ᡇ⋍ᮗ똥⑊뮸疾㧈䩄ₛኝ灏뛫⊴涂ᜒ綛䇄뙳縋릸矤㪂၉⦙Ꮚ뽶灔볢粹拈ṕ↖ᶜ른絔릳羻粈䄑⊓ወ紊瞷㪉䍄⻄䆞ⴀ떸";
//         boolean ans= s1.equals(s2);

public class Main {
    public static void main(String args[]) {
        String keyPath = "";
        String inputFilePath = "";
        String outPutFilePath = "";

        if (args[0].equals("-e")) {
            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-k")) {
                    keyPath = args[i + 1];
                    i++;
                } else if (args[i].equals("-i")) {
                    inputFilePath = args[i + 1];
                    i++;
                } else if (args[i].equals("-o")) {
                    outPutFilePath = args[i + 1];
                }
            }
            AESEncrypt encrypt = new AESEncrypt(keyPath, inputFilePath, outPutFilePath);
            encrypt.Encrypt();
        } else if (args[0].equals("-d")) {
            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-k")) {
                    keyPath = args[i + 1];
                    i++;
                } else if (args[i].equals("-i")) {
                    inputFilePath = args[i + 1];
                    i++;
                } else if (args[i].equals("-o")) {
                    outPutFilePath = args[i + 1];
                }
            }
            AESDecrypt decrypt = new AESDecrypt(keyPath, inputFilePath, outPutFilePath);
            decrypt.Decrypt();
        } else if (args[0].equals("-b")) {
            String plainTextPath = "";
            String cipherTextPath = "";

            for (int i = 1; i < args.length; i++) {
                if (args[i].equals("-m")) {
                    plainTextPath = args[i + 1];
                    i++;
                } else if (args[i].equals("-c")) {
                    cipherTextPath = args[i + 1];
                    i++;
                } else if (args[i].equals("-o")) {
                    outPutFilePath = args[i + 1];
                }
            }
            AESHack hack = new AESHack(plainTextPath, cipherTextPath, outPutFilePath);
            hack.Hack();
        }
    }
}

