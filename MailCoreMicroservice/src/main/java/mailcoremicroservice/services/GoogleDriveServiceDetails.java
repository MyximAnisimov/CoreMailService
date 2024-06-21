package mailcoremicroservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class GoogleDriveServiceDetails {
    @Autowired
    private GoogleDriveService service;
    public String getAllVideo() throws IOException, GeneralSecurityException {
        return service.getfiles();
    }
    public String uploadVideo(MultipartFile file) throws IOException, GeneralSecurityException{

        return service.uploadFile(file);
    }
}
