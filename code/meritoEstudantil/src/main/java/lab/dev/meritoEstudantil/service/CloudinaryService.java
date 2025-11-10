package lab.dev.meritoEstudantil.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file, String folderName) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            
            Map params = ObjectUtils.asMap(
                "folder", folderName,
                "use_filename", true, 
                "unique_filename", true
            );

            Map uploadResult = cloudinary.uploader().upload(uploadedFile, params);
            
            uploadedFile.delete();
            
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível armazenar o arquivo. Erro: " + e.getMessage());
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
