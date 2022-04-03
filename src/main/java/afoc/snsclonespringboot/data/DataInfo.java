package afoc.snsclonespringboot.data;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter @Getter
@NoArgsConstructor
@ToString
public class DataInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private DataType dataType;
    private String sourceDataPath;
    private String saveDataPath;

    @Builder
    public DataInfo(
            DataType dataType,
            String sourceDataPath,
            String saveDataPath
    ) {
        this.dataType = dataType;
        this.sourceDataPath = sourceDataPath;
        this.saveDataPath = saveDataPath;
    }
}
