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
    private Long COMMENT_ID = 1L;
    private Long USER_ID = 1L;
    private Long COMMENT_LIKE_ID = 1L;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/addCommentLike_SuccessfulTest/After.sql")
    public void addCommentLike_SuccessfulTest() throws Exception {
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByCommentId(COMMENT_ID);
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(true);
        mockMvc.perform(post("/api/v1/comments/{commentId}/comment-likes", COMMENT_ID)
                        .param("userId", USER_ID.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.commentId", Is.is(COMMENT_ID.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId", Is.is(USER_ID.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.positive", Is.is(dto.positive())));
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(cl.id) > 0
                                FROM CommentLike cl
                                WHERE cl.commentId= :commentId
                                AND cl.userId = :userId
                                AND cl.positive = :positive
                                """, Boolean.class)
                .setParameter("commentId", COMMENT_ID)
                .setParameter("userId", USER_ID)
                .setParameter("positive", dto.positive())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/addCommentLike_CommentLikeExistsTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/addCommentLike_CommentLikeExistsTest/After.sql")
    public void addCommentLike_CommentLikeExistsTest() throws Exception {
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByCommentId(COMMENT_ID);
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(true);
        mockMvc.perform(post("/api/v1/comments/{commentId}/comment-likes", COMMENT_ID)
                        .param("userId", USER_ID.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Пользователь userId %d уже поставил лайк на комментарий commentId %d",
                                COMMENT_ID, USER_ID))));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/deleteCommentLike_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/deleteCommentLike_SuccessfulTest/After.sql")
    public void deleteCommentLike_SuccessfulTest() throws Exception {
        mockMvc.perform(delete("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        COMMENT_ID, COMMENT_LIKE_ID)
                        .param("userId", USER_ID.toString())
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
                .setParameter("commentId", COMMENT_ID)
                .setParameter("userId", USER_ID)
                .setParameter("positive", Boolean.TRUE)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/deleteCommentLike_NotExistsTest/After.sql")
    public void deleteCommentLike_NotExistsTest() throws Exception {
        mockMvc.perform(delete("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        COMMENT_ID, COMMENT_LIKE_ID)
                        .param("userId", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Лайк комментария с commentLikeId %d, commentId %d, userId %d нет в базе данных",
                                COMMENT_LIKE_ID, COMMENT_ID, USER_ID))));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/getCommentLikeCount_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/getCommentLikeCount_SuccessfulTest/After.sql")
    public void getCommentLikeCount_SuccessfulTest() throws Exception {
        Boolean positive = true;
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByCommentId(COMMENT_ID);
        Long count = entityManager.createQuery(
                        """
                                SELECT COUNT (cl.id)
                                FROM CommentLike cl
                                WHERE cl.commentId= :commentId
                                AND cl.positive = :positive
                                """, Long.class)
                .setParameter("commentId", COMMENT_ID)
                .setParameter("positive", positive)
                .getSingleResult();
        mockMvc.perform(get("/api/v1/comments/{commentId}/comment-likes/count", COMMENT_ID)
                        .param("userId", USER_ID.toString())
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
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(false);
        mockMvc.perform(put("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        COMMENT_ID, COMMENT_LIKE_ID)
                        .param("userId", USER_ID.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.commentId", Is.is(COMMENT_ID.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId", Is.is(USER_ID.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.positive", Is.is(dto.positive())));
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(cl.id) > 0
                                FROM CommentLike cl
                                WHERE cl.commentId= :commentId
                                AND cl.userId = :userId
                                AND cl.positive = :positive
                                """, Boolean.class)
                .setParameter("commentId", COMMENT_ID)
                .setParameter("userId", USER_ID)
                .setParameter("positive", dto.positive())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/outer/CommentLikeRestController/updateCommentLike_NotExistsTest/After.sql")
    public void updateCommentLike_NotExistsTest() throws Exception {
        CommentLikePersistRequestDto dto = new CommentLikePersistRequestDto(false);
        mockMvc.perform(put("/api/v1/comments/{commentId}/comment-likes/{commentLikeId}",
                        COMMENT_ID, COMMENT_LIKE_ID)
                        .param("userId", USER_ID.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Лайк комментария с commentLikeId %d, commentId %d, userId %d нет в базе данных",
                                COMMENT_LIKE_ID, COMMENT_ID, USER_ID))));
    }
}
