package afoc.snsclonespringboot.data;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@ToString
@Table
public class DataInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private DataType dataType;
    private String saveDataPath;

    @Builder
    public DataInfo(
            DataType dataType,
            String saveDataPath
    ) {
        this.dataType = dataType;
        this.saveDataPath = saveDataPath;
    }
}
