package afoc.snsclonespringboot.member.follow;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@NoArgsConstructor
@Table(name = "FOLLOW")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "COLUMN_ID")
    private Long id;

    private Long followerId;

    private Long followeeId;

    @Builder
    public Follow(
            Long followerId,
            Long followeeId
    ){
        this.followerId = followerId;
        this.followeeId = followeeId;
    }
}
