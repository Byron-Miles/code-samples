/** 
 * Cryptography Utility poviding methods to:
 * Encrypt a byte array with a key
 * Decrypt a byte array with a key
 * Convert a secret key to a byte array
 * Convert a byte array to a secret key
 * Generate a secret key
 * Create a message digest for a byte array
 * 
 * Note: Encryption / Decryption done with AES
 *       Message digest created with SHA
 *
 * @author ASM Sajeev, Byron Miles
**/

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.io.*;


public class CryptoUtil 
{  
   /**
    * Method to encrypt a byte array with a secret key
    * Input: byte array, SecretKey
    * Output: encrypted byte array
    **/
   public static byte[] encrypt(byte[] decrypted, SecretKey key)
   {
      byte[] encrypted = null;

      try {
         Cipher AESCipher = Cipher.getInstance("AES"); //Create a new Cipher

         AESCipher.init(Cipher.ENCRYPT_MODE, key); //Init for encryption

         encrypted = AESCipher.doFinal(decrypted); //Encrypt the byte array
      }
      catch (NoSuchAlgorithmException e) {
         System.out.println("Error in encrypt: " + e.toString());
      }
      catch (InvalidKeyException e) {
	 System.out.println("Error in encrypt: "+ e.toString());
      }
      catch (IllegalBlockSizeException e) {
         System.out.println("Error in encrypt: " + e.toString());
      }
      catch (NoSuchPaddingException e) {
         System.out.println("Error in encrypt: " + e.toString());
      }
      catch (BadPaddingException e) {
         System.out.println("Error in encrypt: " + e.toString());
      }
      return encrypted; //Return the encrypted byte array
   }

   /**
    * Method to decrypt a byte array with a secret key
    * Input: encrypted byte array, SecretKey
    * Output: decrypted byte array
    **/
   public static byte[] decrypt(byte[] encrypted, SecretKey key)
   {
      byte[] decrypted = null;

      try{
         Cipher AESCipher = Cipher.getInstance("AES"); //Create a new Cipher

         AESCipher.init(Cipher.DECRYPT_MODE, key); //Init for decryption

         decrypted = AESCipher.doFinal(encrypted); //Decrypt the byte array

      }
      catch (NoSuchAlgorithmException e) {
         System.out.println("Error in decrypt: " + e.toString());
      }
      catch (InvalidKeyException e) {
	 System.out.println("Error in decrypt: " + e.toString());
      }
      catch (IllegalBlockSizeException e) {
         System.out.println("Error in decrypt: " + e.toString());
      }
      catch (NoSuchPaddingException e) {
         System.out.println("Error in decrypt: " + e.toString());
      }
      catch (BadPaddingException e) {
         System.out.println("Error in decrypt: " + e.toString());
      }
      return decrypted; //Return the decrypted byte array
   }

   /**
    * Method to make an AES Secret Key from a byte array
    * Input: byte array
    * Output: the corresponding secret key
    **/
   public static SecretKey makeKey(byte[] keyBytes) 
   {
      SecretKey key = new SecretKeySpec(keyBytes, "AES");
      return key;
   }

   /**
    * Method to break a Secret Key into a byte array
    * Input: secret key
    * Output: byte array
    **/
   public static byte[] breakKey(SecretKey key) 
   {
      return key.getEncoded();
   }

   /**
    * Method to generate an AES Secret Key
    * Output: An AES secret key
    **/
   public static SecretKey getKey() 
   {
      SecretKey key = null;
      try {   
         KeyGenerator keygen = KeyGenerator.getInstance("AES");
	 keygen.init(128); //Generate a 128 bit key
         key =  keygen.generateKey();
      }
      catch(NoSuchAlgorithmException e) {
         System.out.println("Error in getAESKey: " + e.toString());
      }
     return key;
   }

   /**
    * Method to hash a byte array
    * Input: byte array
    * Output: corresponding hash as byte array 
    **/
   public static byte[] hash(byte[] msg)
   {
      byte[] hashValue = null;

      try{
         MessageDigest mDigest = MessageDigest.getInstance("SHA");
         mDigest.update(msg); //Hash the byte array
         hashValue = mDigest.digest(); //Get the hash value to return
      }
      catch(NoSuchAlgorithmException e)
      {
         System.out.println("Error in hash " + e.toString());
      }

      return hashValue;
   }

}//End CryptoHandler