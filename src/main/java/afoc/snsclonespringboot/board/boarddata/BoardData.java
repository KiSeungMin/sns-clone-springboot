package afoc.snsclonespringboot.board.boarddata;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table
public class BoardData {
    @Id
    private Long id;

    private Long boardId;

    private Long dataInfoId;
}
