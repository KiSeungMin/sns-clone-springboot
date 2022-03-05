package afoc.snsclonespringboot.member.follow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@Table(name = "FOLLOW")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "COLUMN_ID")
    private Long id;

    private Long followerId;

    private Long followeeId;

}
