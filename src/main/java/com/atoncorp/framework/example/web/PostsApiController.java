package com.atoncorp.framework.example.web;

import com.atoncorp.framework.example.service.PostsService;
import com.atoncorp.framework.example.web.dto.PostsRequestDto;
import com.atoncorp.framework.example.web.dto.PostsResponseDto;
import com.atoncorp.framework.example.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsRequestDto requestDto){
        String temp = "Test";
        System.out.println(temp);
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto updateRequestDto){
        return postsService.update(id, updateRequestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}
