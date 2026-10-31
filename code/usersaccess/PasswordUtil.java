package usersaccess;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
public final class PasswordUtil {
 private PasswordUtil(){}

 public static String hash(String password){
  if(password==null||password.length()<8)throw new IllegalArgumentException("Password must be at least 8 characters.");
  try{
   byte[] salt=new byte[16];
   new SecureRandom().nextBytes(salt);
   int iterations=120000;
   PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,iterations,256);
   byte[] key=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
   spec.clearPassword();
   return iterations+":"+Base64.getEncoder().encodeToString(salt)+":"+Base64.getEncoder().encodeToString(key);
  } catch (Exception e){
   throw new IllegalStateException(e);
  }
 }

 public static boolean verify(String password,String stored){
  try{
   String[] p=stored.split(":");
   int n=Integer.parseInt(p[0]);
   byte[] salt=Base64.getDecoder().decode(p[1]),expected=Base64.getDecoder().decode(p[2]);
   PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,n,expected.length*8);
   byte[] actual=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
   spec.clearPassword();
   if(actual.length!=expected.length)return false;
   int diff=0;for(int i=0;i<actual.length;i++)diff|=actual[i]^expected[i];
   return diff==0;
  } catch (Exception e){
   return false;
  }
 }
}
