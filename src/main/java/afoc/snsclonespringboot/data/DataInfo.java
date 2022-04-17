package afoc.snsclonespringboot.data;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table
public class DataInfo {

    @Id
    @Column(name="DATAINFO_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DataType dataType;

    private String saveDataPath;
}
