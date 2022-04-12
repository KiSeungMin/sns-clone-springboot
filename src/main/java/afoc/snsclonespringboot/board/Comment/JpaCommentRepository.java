package afoc.snsclonespringboot.board.Comment;

import afoc.snsclonespringboot.board.like.Like;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class JpaCommentRepository implements CommentRepository{

    @PersistenceContext
    private EntityManager em;

    public JpaCommentRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Optional<Comment> save(Comment comment) {
        em.persist(comment);

        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findCommentListByBoardId(Long boardId) {

        List<Comment> commentList = em.createQuery("select C from Comment C where C.boardId = :boardId")
                .setParameter("boardId", boardId)
                .getResultList();

        return commentList;
    }
}
