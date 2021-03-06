package afoc.snsclonespringboot.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class BoardForm {
    private String textData;
    private List<MultipartFile> imageFiles;
}
