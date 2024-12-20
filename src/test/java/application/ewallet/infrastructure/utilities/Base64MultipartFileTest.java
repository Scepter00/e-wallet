package application.ewallet.infrastructure.utilities;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class Base64MultipartFileTest {

    @Test
    void createBase64MultipartFileWithValidData() throws IOException {
        String base64String ="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";
        Base64MultipartFile multipartFile = new Base64MultipartFile(base64String);

        assertNotNull(multipartFile);
        log.info("content...{}", multipartFile.getContentType());
        assertEquals("uploaded_image", multipartFile.getName());
        assertEquals("application/octet-stream", multipartFile.getContentType());
        assertEquals("uploaded_image", multipartFile.getOriginalFilename());
        assertFalse(multipartFile.isEmpty());
        assertTrue(multipartFile.getSize() > 0);
        assertNotNull(multipartFile.getBytes());
    }

    @Test
    void throwExceptionForInvalidBase64StringFormat() {
        String invalidBase64String = "invalid_base64_data";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Base64MultipartFile(invalidBase64String));
        assertEquals("Invalid Base64 string format", exception.getMessage());
    }

    @Test
    void throwExceptionForBase64StringWithoutValidData() {
        String base64StringWithoutData = "data:image/jpeg;base64,";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Base64MultipartFile(base64StringWithoutData));
        assertEquals("Base64 string does not contain valid data", exception.getMessage());
    }

    @Test
    void handleUnsupportedContentTypeGracefully() {
        String base64String = "data:application/pdf;base64,SGVsbG8gd29ybGQ=";
        Base64MultipartFile multipartFile = new Base64MultipartFile(base64String);

        assertNotNull(multipartFile);
        assertEquals("uploaded_image", multipartFile.getName());
        assertEquals("application/octet-stream", multipartFile.getContentType());
        assertEquals("uploaded_image", multipartFile.getOriginalFilename()); // No extension for unsupported type
    }

    @Test
    void returnCorrectInputStream() throws IOException {
        String base64String = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA...";
        Base64MultipartFile multipartFile = new Base64MultipartFile(base64String);

        assertNotNull(multipartFile.getInputStream());
        assertEquals(Base64.getDecoder().decode(base64String.split(",")[1]).length, multipartFile.getBytes().length);
    }

    @Test
    void returnEmptyForEmptyBase64Data() {
        String base64String = "data:image/png;base64,";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Base64MultipartFile(base64String));
        assertEquals("Base64 string does not contain valid data", exception.getMessage());
    }
}