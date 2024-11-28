package application.ewallet.infrastructure.adapters.output.imageManager;

import application.ewallet.application.output.imageManager.ImageManagerOutputPort;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.infrastructure.utilities.Base64MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryAdapter implements ImageManagerOutputPort {

    private final Cloudinary cloudinary;

    private static final String SECURE_URL = "secure_url";
    private static  final String RESOURCE_TYPE = "resource_type";
    private static final String AUTO = "auto";

    @Override
    public String uploadImage(String content) throws WalletException {
        MultipartFile multipart = new Base64MultipartFile(content);

        try {
            Map<?, ?> uploadResponse = cloudinary.uploader().upload(multipart.getBytes(),
                    ObjectUtils.asMap(
                            RESOURCE_TYPE, AUTO
                    ));
            return (String) uploadResponse.get(SECURE_URL);
        }catch (IOException exception) {
            throw new WalletException(exception.getMessage());
        }
    }
}
