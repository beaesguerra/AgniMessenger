package agni.server.manager;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.LoginListener;
import agni.server.sender.InfoSender;

public class LoginManager implements LoginListener{
    private InfoSender infoSender;
    private I_UserDataGuard userDataGuard;

    public LoginManager(InfoSender infoSender,
                        UserDataGuard userDataGuard) {
        this.infoSender = infoSender;
        this.userDataGuard = userDataGuard;
    }
    private byte [] generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt); 
        return salt; 
    }
    
    private String generatePasswordHash(String password, byte [] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("pbkdf2withhmacsha1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        return new String(hash,StandardCharsets.US_ASCII);
    }
    @Override
    public void loginRequest(String ip, String user, String password) {
        if (password == userDataGuard.getPasswordHash(user)) {
            userDataGuard.loginUser(ip, user);
            infoSender.sendInfo(ip, "approved");
        }
        else {
            infoSender.sendInfo(ip, "declined");
        }
    }

    @Override
    public void newUserRequest(String ip, String user, String password) {
        if (userDataGuard.userExists(user)) {
            infoSender.sendInfo(ip, "failure: " + user + " exists");
        }
        else {
            byte [] salt = generateSalt(); 
            String passwordHash = null;
            try {
                passwordHash = generatePasswordHash(password, salt);
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            String saltString = new String(salt, StandardCharsets.US_ASCII);
            userDataGuard.addUser(user, saltString, passwordHash);
            infoSender.sendInfo(ip, "success: " + user + " registered");
        }
        
    }
    
    
    

}
