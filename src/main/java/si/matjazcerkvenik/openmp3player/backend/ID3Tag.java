package si.matjazcerkvenik.openmp3player.backend;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v1;
import org.farng.mp3.id3.AbstractID3v2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.player.Mp3File;

public class ID3Tag {
		
	
	private static Logger logger = LoggerFactory.getLogger(ID3Tag.class);
	
	public static Mp3File getMetadata(Mp3File mp3) {
		
		try {
			File sourceFile = new File(mp3.getPath());
			
			if (!sourceFile.exists()) {
				return mp3;
			}
			
			MP3File mp3file = new MP3File(sourceFile);
			
			AbstractID3v1 tag = mp3file.getID3v1Tag();
			if (tag != null) {
				logger.info("ID3Tag:getMetadata(): parsing ID3v1: " + mp3.getPath());
				if (tag.getSongTitle().trim().length() > 0) {
					mp3.setTitle(validate(tag.getSongTitle()));
				}
				mp3.setArtist(validate(tag.getLeadArtist()));
				mp3.setAlbum(validate(tag.getAlbumTitle()));
				mp3.setGenre(validate(tag.getSongGenre()));
				mp3.setYear(validate(tag.getYearReleased()));
				mp3.setSize(tag.getSize());
				return mp3;
			}
			
			AbstractID3v2 tag2 = mp3file.getID3v2Tag();
			if (tag2 != null) {
				logger.info("ID3Tag:getMetadata(): parsing ID3v2: " + mp3.getPath());
				if (tag2.getSongTitle().trim().length() > 0) {
					mp3.setTitle(validate(tag2.getSongTitle()));
				}
				mp3.setArtist(validate(tag2.getLeadArtist()));
				mp3.setAlbum(validate(tag2.getAlbumTitle()));
				mp3.setGenre(validate(tag2.getSongGenre()));
				mp3.setYear(validate(tag2.getYearReleased()));
				mp3.setSize(tag2.getSize());
				return mp3;
			}
			
			
		} catch (IOException e) {
			logger.error("ID3Tag:getMetadata(): " , e);
		} catch (TagException e) {
			logger.error("ID3Tag:getMetadata(): " , e);
		} catch (Exception e) {
			logger.error("ID3Tag:getMetadata(): " , e);
		}
		
		return mp3;
	}
	
	public static String validate(String s) {
		s = s.replaceAll("\\x00", "");
		s = s.replaceAll("\\x01", "");
		s = s.replaceAll("\\x02", "");
		s = s.replaceAll("\\x03", "");
		s = s.replaceAll("\\x04", "");
		s = s.replaceAll("\\x05", "");
		s = s.replaceAll("\\x06", "");
		s = s.replaceAll("\\x07", "");
		s = s.replaceAll("\\x08", "");
		s = s.replaceAll("\\x09", "");
		s = s.replaceAll("\\x0a", "");
		s = s.replaceAll("\\x0b", "");
		s = s.replaceAll("\\x0c", "");
		s = s.replaceAll("\\x0d", "");
		s = s.replaceAll("\\x0e", "");
		s = s.replaceAll("\\x0f", "");
		return s;
		
	}
	
}
