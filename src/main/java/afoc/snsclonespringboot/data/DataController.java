package afoc.snsclonespringboot.data;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class DataController {
    private final DataService dataService;

    @ResponseBody
    @GetMapping(value = "/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + dataService.getAbsolutePath(filename));
    }
    @ResponseBody
    @GetMapping("/images/test/{filename}")
    public Resource downloadTestImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + dataService.getTestAbsolutePath(filename));
    }
}
