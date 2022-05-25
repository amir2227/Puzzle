package ir.tehranpuzzle.mistery.minio;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    byte[] getFile(String fileName, String folder);
    
    String uploadImage(MultipartFile file, String folder, boolean isResize) throws IOException;

    void deleteFile(String fileName, String folder);
}
