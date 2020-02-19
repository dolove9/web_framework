package com.atoncorp.framework.example.service;

import com.atoncorp.framework.example.domain.posts.Posts;
import com.atoncorp.framework.example.domain.posts.PostsRepository;
import com.atoncorp.framework.example.web.dto.PostsRequestDto;
import com.atoncorp.framework.example.web.dto.PostsResponseDto;
import com.atoncorp.framework.example.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsRequestDto requestDto){
        Long result = postsRepository.save(requestDto.toEntity()).getId();
        return result;
//        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto updateRequestDto) {
        /**
         * key를 가지고 검색된 Entity 를 가져오고
         * 그 Entity를 가지고 updata --> 객체 저장을 진행한다.
         */

        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        //TODO update에 대한 부분이 postsRepository에 없는 이유는 JPA에 대한 영속성 때문이다. --> 좀더 공부해봐야할 듯
        posts.update(updateRequestDto.getTitle(), updateRequestDto.getContent());
        return id;
    }


    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }
}
