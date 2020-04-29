Instructions:

Encrypt/Decrypt:
–e : instruction to encrypt the input file
–d: instruction to decrypt the input file
–k <path>: path to the keys, the key should be 384 bit (128*3) for . and should be divided into 3 separate keys.
–i <input file path>: a path to a file we want to encrypt/decrypt
–o <output file path>: a path to the output file
Usage: Java –jar aes.jar -e/-d –k <path-to-key-file > -i <path-to-input-file> -o <path-to-output-file>
    e.g  Java –jar aes.jar –e –k key.txt –i message.txt –o cypther.txt
    
Hack:
–b : instruction to break the encryption algorithm
–m <path>: denotes the path to the plain-text message
–c <path>: denotes the path to the cipher-text message
–o <path>: a path to the output file with the key(s) found.
Usage: Java –jar aes.jar -b –m <path-to-message> –c <path-to-cipher> -o < output-path>
