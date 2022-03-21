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
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private DataType dataType;
    private String sourceDataPath;
    private String saveDataPath;

    @Builder
    public Data(
            Long id, // remove this line later
            DataType dataType,
            String sourceDataPath,
            String saveDataPath
    ) {
        this.id = id;
        this.dataType = dataType;
        this.sourceDataPath = sourceDataPath;
        this.saveDataPath = saveDataPath;
    }
}
