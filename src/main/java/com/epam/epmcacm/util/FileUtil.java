package com.epam.epmcacm.util;

import com.epam.epmcacm.exceptions.InvalidMp3FileException;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public enum FileAttribute {

        TRACK,
        ARTIST,
        ALBUM,
        LENGTH,
        YEAR;

    }

    private FileUtil() {
        throw new IllegalStateException("Utility class!");
    }

    public static Mp3File createMp3File(String fileName, byte[] bytes) throws IOException, InvalidDataException, UnsupportedTagException {
        File standardFile = new File(fileName);
        FileUtils.writeByteArrayToFile(standardFile,bytes);
        Mp3File mp3 = new Mp3File(standardFile);
        return mp3;
    }

    public static Map<FileAttribute, String> generateMp3FileAttributeMap(Mp3File mp3file) throws InvalidMp3FileException {
        if (!mp3file.hasId3v2Tag()) throw new InvalidMp3FileException("File doesn't have appropriate attributes - artist,album,year,length...");
        Map<FileAttribute, String> result = new HashMap<>();
        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
        result.put(FileAttribute.TRACK, mp3file.getFilename());
        result.put(FileAttribute.ARTIST, id3v2Tag.getArtist());
        result.put(FileAttribute.ALBUM, id3v2Tag.getAlbum());
        result.put(FileAttribute.YEAR, id3v2Tag.getYear());
        result.put(FileAttribute.LENGTH, String.valueOf(id3v2Tag.getLength()));
        logger.info(result.toString());
        return result;
    }
    public static void deleteTempFile(String filePath){
        Path path = Paths.get(filePath);
        try {
            logger.info("! Deleting File From The Configured Path !");
            Files.delete(path);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
