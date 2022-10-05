package kata.academy.eurekalikeservice.outer;

import kata.academy.eurekalikeservice.SpringSimpleContextTest;
import kata.academy.eurekalikeservice.feign.ContentServiceFeignClient;
import kata.academy.eurekalikeservice.model.dto.CommentLikePersistRequestDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({
        @MockBean(ContentServiceFeignClient.class)
})
public class CommentLikeRestControllerIT extends SpringSimpleContextTest {
    @Autowired
    private ContentServiceFeignClient contentServiceFeignClient;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/addCommentLike_SuccessfulTest/After.sql")
    public void addCommentLike_SuccessfulTest() throws Exception {
        Long commentId = 1L;
        Long userId = 1L;
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByCommentId(commentId);
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(true);
        mockMvc.perform(post("/api/v1/comments/{commentId}/comment-likes", commentId)
                        .param("userId", userId.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.commentId", Is.is(commentId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId", Is.is(userId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.positive", Is.is(dto.positive())));
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(cl.id) > 0
                                FROM CommentLike cl
                                WHERE cl.commentId= :commentId
                                AND cl.userId = :userId
                                AND cl.positive = :positive
                                """, Boolean.class)
                .setParameter("commentId", commentId)
                .setParameter("userId", userId)
                .setParameter("positive", dto.positive())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/addCommentLike_CommentLikeExistsTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/addCommentLike_CommentLikeExistsTest/After.sql")
    public void addCommentLike_CommentLikeExistsTest() throws Exception {
        Long commentId = 1L;
        Long userId = 1L;
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByCommentId(commentId);
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(true);
        mockMvc.perform(post("/api/v1/comments/{commentId}/comment-likes", commentId)
                        .param("userId", userId.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Пользователь userId %d уже поставил лайк на комментарий commentId %d",
                                commentId, userId))));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/deleteCommentLike_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/deleteCommentLike_SuccessfulTest/After.sql")
    public void deleteCommentLike_SuccessfulTest() throws Exception {
        Long commentId = 1L;
        Long commentLikeId = 1L;
        Long userId = 1L;
        mockMvc.perform(delete("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        commentId, commentLikeId)
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));
        assertFalse(entityManager.createQuery(
                        """
                                SELECT COUNT(cl.id) > 0
                                FROM CommentLike cl
                                WHERE cl.commentId= :commentId
                                AND cl.userId = :userId
                                AND cl.positive = :positive
                                """, Boolean.class)
                .setParameter("commentId", commentId)
                .setParameter("userId", userId)
                .setParameter("positive", Boolean.TRUE)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/deleteCommentLike_NotExistsTest/After.sql")
    public void deleteCommentLike_NotExistsTest() throws Exception {
        Long commentId = 1L;
        Long commentLikeId = 1L;
        Long userId = 1L;
        mockMvc.perform(delete("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        commentId, commentLikeId)
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Лайк комментария с commentLikeId %d, commentId %d, userId %d нет в базе данных",
                                commentLikeId, commentId, userId))));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/getCommentLikeCount_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/getCommentLikeCount_SuccessfulTest/After.sql")
    public void getCommentLikeCount_SuccessfulTest() throws Exception {
        Long commentId = 1L;
        Long userId = 1L;
        Boolean positive = true;
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByCommentId(commentId);
        Long count = entityManager.createQuery(
                        """
                                SELECT COUNT (cl.id)
                                FROM CommentLike cl
                                WHERE cl.commentId= :commentId
                                AND cl.positive = :positive
                                """, Long.class)
                .setParameter("commentId", commentId)
                .setParameter("positive", positive)
                .getSingleResult();
        mockMvc.perform(get("/api/v1/comments/{commentId}/comment-likes/count", commentId)
                        .param("userId", userId.toString())
                        .param("positive", positive.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Is.is(count.intValue())));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/updateCommentLike_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/updateCommentLike_SuccessfulTest/After.sql")
    public void updateCommentLike_SuccessfulTest() throws Exception {
        Long commentId = 1L;
        Long commentLikeId = 1L;
        Long userId = 1L;
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(false);
        mockMvc.perform(put("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        commentId, commentLikeId)
                        .param("userId", userId.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.commentId", Is.is(commentId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId", Is.is(userId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.positive", Is.is(dto.positive())));
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(cl.id) > 0
                                FROM CommentLike cl
                                WHERE cl.commentId= :commentId
                                AND cl.userId = :userId
                                AND cl.positive = :positive
                                """, Boolean.class)
                .setParameter("commentId", commentId)
                .setParameter("userId", userId)
                .setParameter("positive", dto.positive())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/updateCommentLike_NotExistsTest/After.sql")
    public void updateCommentLike_NotExistsTest() throws Exception {
        Long commentId = 1L;
        Long commentLikeId = 1L;
        Long userId = 1L;
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(false);
        mockMvc.perform(put("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        commentId, commentLikeId)
                        .param("userId", userId.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Лайк комментария с commentLikeId %d, commentId %d, userId %d нет в базе данных",
                                commentLikeId, commentId, userId))));
    }
}
