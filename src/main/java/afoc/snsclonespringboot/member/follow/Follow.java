package afoc.snsclonespringboot.member.follow;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FOLLOW")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLUMN_ID")
    private Long id;

    private Long followerId;

    private Long followeeId;
}
