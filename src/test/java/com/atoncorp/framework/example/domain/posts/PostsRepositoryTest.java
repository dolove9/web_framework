package com.atoncorp.framework.example.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void tearDown(){
        postsRepository.deleteAll();
    }

    @Test
    public void getPosts_test(){
        String title = "테스트 게시글 제목";
        String content = "테스트 게시글 입니다.";

        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("dolove9@gmail.com")
                        .build()
        );

        List<Posts> postsList = postsRepository.findAll();
        System.out.println("");


        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}