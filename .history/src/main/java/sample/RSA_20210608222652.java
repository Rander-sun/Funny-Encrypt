package sample;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RSA {
    private static final int MAX_DECRYPT_SIZE=64;
    public PublicKey getPublicKey() {
        return publicKey;
    }
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private String originText;
    private String encryptedText;
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    public String getOriginText() {
        return originText;
    }
    public String getEncryptedText() {
        return encryptedText;
    }

//输入原文获取加密结果
    RSA(String origin){
        this.originText=origin;
        try{
            getKeyPair();
            encryptText();
            //decryptText(this.privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

//输入私钥和密文获取明文
    RSA(String encrypted,String pri){
        this.encryptedText=encrypted;
        try{
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(pri));
            KeyFactory keyFactory =KeyFactory.getInstance("RSA");
            this.privateKey=(RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            decryptText(this.privateKey);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }  catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator =KeyPairGenerator.getInstance("RSA");//指定算法的密钥对生成器
        generator.initialize(512);//密钥长度
        KeyPair keys = generator.generateKeyPair();//随机生成公钥私钥
        this.privateKey=(RSAPrivateKey) keys.getPrivate();
        this.publicKey=(RSAPublicKey)keys.getPublic();
    }
//公钥加密
    public void encryptText() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher= Cipher.getInstance("RSA");//获取密码器
        cipher.init(Cipher.ENCRYPT_MODE,this.publicKey);//初始化模型
        byte[] oriText=this.originText.getBytes(UTF_8);
        byte[] enText=cipher.doFinal(oriText);
        this.encryptedText=bytesToString(enText);
        //this.encryptedText=Base64.getEncoder().encodeToString(enText);
    }

//考虑到密文可能过长，对其进行分段解密
    public void decryptText(RSAPrivateKey pk) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,pk);
        byte[] enText=stringToBytes(this.encryptedText);
        int total=enText.length;
        int offset = 0;
        byte[] buffer;
        int left=total;
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream(1024);
        while(left>0){
            if(left>MAX_DECRYPT_SIZE){
                buffer=cipher.doFinal(enText,offset,MAX_DECRYPT_SIZE);
            }
            else{
                buffer=cipher.doFinal(enText,offset,left);
            }
            outputStream.write(buffer,0, buffer.length);
            offset+=MAX_DECRYPT_SIZE;
            left=total-offset;
        }
        this.originText = outputStream.toString(String.valueOf(UTF_8));
    }

    //bytes to string with blank
    public String bytesToString(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        int len= bytes.length;
        for(i=0;i<len;i++){
            stringBuffer.append(Byte.toString(bytes[i]));
            if(!(i==len-1)){
                stringBuffer.append(" ");
            }
        }
        return stringBuffer.toString();
    }

    //string to bytes without blank
    public byte[] stringToBytes(String string){
        String[] strings = string.split(" ");
        int len= strings.length;
        byte[] bytes=new byte[len];
        for(int i=0;i<len;i++){
            bytes[i]=Byte.parseByte(strings[i]);
        }
        return bytes;
    }
}
