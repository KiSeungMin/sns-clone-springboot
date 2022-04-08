package afoc.snsclonespringboot.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class UploadForm {
    private String text;
    private List<MultipartFile> imageFiles;
}
