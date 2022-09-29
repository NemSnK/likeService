package kata.academy.eurekalikeservice.outer;

import kata.academy.eurekalikeservice.SpringSimpleContextTest;
import kata.academy.eurekalikeservice.feign.ContentServiceFeignClient;
import kata.academy.eurekalikeservice.model.dto.PostLikePersistRequestDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({
        @MockBean(ContentServiceFeignClient.class)
})
public class PostLikeRestControllerIT extends SpringSimpleContextTest {

    @Autowired
    private ContentServiceFeignClient contentServiceFeignClient;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/PostLikeRestController/addPostLike_SuccessfulTest/After.sql")
    public void addPostLike_SuccessfulTest() throws Exception {
        Long postId = 1L;
        Long userId = 1L;
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByPostId(postId);
        PostLikePersistRequestDto dto = new PostLikePersistRequestDto(true);
        mockMvc.perform(post("/api/v1/posts/{postId}/post-likes", postId)
                        .param("userId", userId.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.postId", Is.is(postId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId", Is.is(userId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.positive", Is.is(dto.positive())));
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(pl.id) > 0
                                FROM PostLike pl
                                WHERE pl.postId = :postId
                                AND pl.userId = :userId
                                AND pl.positive = :positive
                                """, Boolean.class)
                .setParameter("postId", postId)
                .setParameter("userId", userId)
                .setParameter("positive", dto.positive())
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/PostLikeRestController/addPostLike_PostLikeExistsTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/PostLikeRestController/addPostLike_PostLikeExistsTest/After.sql")
    public void addPostLike_PostLikeExistsTest() throws Exception {
        Long postId = 1L;
        Long userId = 1L;
        doReturn(ResponseEntity.ok(Boolean.TRUE)).when(contentServiceFeignClient).existsByPostId(postId);
        PostLikePersistRequestDto dto = new PostLikePersistRequestDto(true);
        mockMvc.perform(post("/api/v1/posts/{postId}/post-likes", postId)
                        .param("userId", userId.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Реакция на пост с postId %d пользователя с userId %d уже была добавлена",
                                postId, userId)
                )));
    }
}
