package afoc.snsclonespringboot.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수 입력 값입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotNull(message = "프로필 이미지는 필수 항목입니다.")
    private MultipartFile profileImage;
}
