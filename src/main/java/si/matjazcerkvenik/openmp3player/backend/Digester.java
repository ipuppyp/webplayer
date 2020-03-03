package si.matjazcerkvenik.openmp3player.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Digester {
	
	private static Logger logger = LoggerFactory.getLogger(Digester.class);
	
	public static String getMd5(String file) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
	    FileInputStream fis = new FileInputStream(file);
	    byte[] dataBytes = new byte[1024];
	 
	    int nread = 0; 
	 
	    while ((nread = fis.read(dataBytes)) != -1) {
	      md.update(dataBytes, 0, nread);
	    }
	 
	    byte[] mdbytes = md.digest();
	 
	    //convert the byte to hex format
	    StringBuffer sb = new StringBuffer("");
	    for (int i = 0; i < mdbytes.length; i++) {
	    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    
	    // +0x100 is used for nicer representation
	    // b + 0 = b --toString--> "b"
	    // b + 100 = 10b --toString--> "10b" --substring--> "0b"
	    
	    logger.debug("Digester:getMd5(): file: " + file + " MD5=" + sb.toString());
	    
//	    System.out.println("Digest(in hex format):: " + sb.toString());
	    
	    fis.close();
		
	    return sb.toString();
	    
	}
	
	public static String getSha1(String file) {
		
		if (!fileExists(file)) {
			return "-1";
		}
		
		FileInputStream fis;
		StringBuffer sb;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			fis = new FileInputStream(file);
			byte[] dataBytes = new byte[1024];
 
			int nread = 0; 
 
			while ((nread = fis.read(dataBytes)) != -1) {
			  md.update(dataBytes, 0, nread);
			}
 
			byte[] mdbytes = md.digest();
 
			sb = new StringBuffer("");
			//convert the byte to hex format
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			logger.debug("Digester:getSha1(): file: " + file + " SHA1=" + sb.toString());
			
			fis.close();
			
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
//	    System.out.println("Digest(in hex format):: " + sb.toString());
	    
	    return "0";
		
	}
	
	private static boolean fileExists(String file) {
		File f = new File(file);
		return f.exists();
	}
	
}
