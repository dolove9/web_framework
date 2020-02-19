package com.atoncorp.framework.example.web;

import com.atoncorp.framework.example.domain.posts.Posts;
import com.atoncorp.framework.example.domain.posts.PostsRepository;
import com.atoncorp.framework.example.web.dto.PostsRequestDto;
import com.atoncorp.framework.example.web.dto.PostsResponseDto;
import com.atoncorp.framework.example.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void posts_등록_test(){
        String title = "TEST title";
        String content = "TEST Content";

        LocalDateTime nowTime = LocalDateTime.now();


        PostsRequestDto requestDto = PostsRequestDto.builder()
                .title(title)
                .content(content)
                .author("dolove9@gmail.com")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThanOrEqualTo(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);


        assertThat(all.get(0).getCreateDate()).isAfter(nowTime);
        assertThat(all.get(0).getModifiedDate()).isAfter(nowTime);


    }

    @Test
    public void posts_수정_test(){
        /**
         * 1. 새로운거 등록
         * 2. 등록된 아이디로 검색
         * 3. updateRequestDto 생성
         * 4. 실제 update api 호출
         * 5. 업데이트 된것 확인
         */

        Posts savePosts = postsRepository.save(Posts.builder().title("타이틀_1").content("컨텐츠_2").author("작성자_1").build());

        Long updateId = savePosts.getId();
        String updateTitle = "수정된 타이틀_2";
        String updateContent = "수정된 컨텐츠 입니다.2";

        PostsUpdateRequestDto updateRequestDto = PostsUpdateRequestDto.builder().title(updateTitle).content(updateContent).build();

        String updateUrl = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);


        ResponseEntity<Long> responseEntity = restTemplate.exchange(updateUrl, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Optional<Posts> postsList = postsRepository.findById(savePosts.getId());

        if(postsList.isPresent()){
            Posts post = postsList.get();
            assertThat(post.getTitle()).isEqualTo(updateTitle);
            assertThat(post.getContent()).isEqualTo(updateContent);

            System.out.println("getCreateDate : " + post.getCreateDate());
            System.out.println("getModifiedDate : " + post.getModifiedDate());
        }


    }

    @Test
    public void posts_조회_test(){
        Posts savePosts = postsRepository.save(Posts.builder().title("타이틀_1").content("컨텐츠_2").author("작성자_1").build());

        Long searchId = savePosts.getId();

        String searchUrl = "http://localhost:" + port + "/api/v1/posts/" + searchId;

        ResponseEntity<PostsResponseDto> responsePost = restTemplate.getForEntity(searchUrl, PostsResponseDto.class);

        assertThat(responsePost.getBody().getId()).isEqualTo(searchId);
        assertThat(responsePost.getBody().getTitle()).isEqualTo("타이틀_1");
        assertThat(responsePost.getBody().getContent()).isEqualTo("컨텐츠_2");
        assertThat(responsePost.getBody().getAuthor()).isEqualTo("작성자_1");

    }

}