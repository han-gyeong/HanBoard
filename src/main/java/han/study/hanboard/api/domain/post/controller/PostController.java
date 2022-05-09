package han.study.hanboard.api.domain.post.controller;

import han.study.hanboard.api.domain.comment.models.Comment;
import han.study.hanboard.api.domain.post.models.Post;
import han.study.hanboard.api.domain.post.models.dto.PostDto;
import han.study.hanboard.api.domain.post.repository.PostRepository;
import han.study.hanboard.api.domain.post.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping
    public String getPosts(Model model) {
        List<Post> postList = postRepository.findAll();
        List<PostDtoForList> dtoList = postList.stream().map(PostDtoForList::new).collect(Collectors.toList());

        model.addAttribute("postList", dtoList);
        return "postList";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            return "post";
        }

        Post post = postOptional.get();
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            commentDtoList.add(
                    new CommentDto(comment.getId(), comment.getMember().getUsername(),
                            comment.getContent(), comment.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))));
        }

        PostOutputDto postOutputDto = new PostOutputDto(post.getId(), post.getTitle(), post.getMember().getUsername(), post.getContent(), commentDtoList);
        model.addAttribute("post", postOutputDto);

        return "post";
    }

    // 게시물 수정하기
    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("post", post);

        return "editPost";
    }

    @PostMapping("/{id}/edit")
    public String submitEditPost(@PathVariable Long id, @ModelAttribute PostDto postDto, RedirectAttributes redirectAttributes) {
        postService.editPost(id, postDto);

        redirectAttributes.addFlashAttribute("message", id + " 번 게시물이  정상적으로 수정되었습니다.");
        return "redirect:/post/" + id;
    }

    // 게시물 삭제하기

    // 게시물 생성하기
    @GetMapping("/new")
    public String writePostPage(@SessionAttribute(name = "username", required = false) String username) {
        if (username == null) {
            return "redirect:/login";
        }

        return "newPost";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute PostDto postDto, BindingResult bindingResult,
                             @SessionAttribute(name = "username", required = false) String username) {
        if (bindingResult.hasErrors()) {
            return "post";
        }

        Long generatedPostId = postService.createPost(postDto.toEntity(), username);
        return "redirect:/post/" + generatedPostId;
    }

    @Data
    @ToString
    @AllArgsConstructor
    static class PostOutputDto {
        Long id;
        String title;
        String author;
        String content;
        List<CommentDto> comments;
    }

    @Data
    @ToString
    @AllArgsConstructor
    static class CommentDto {
        Long id;
        String author;
        String content;
        String created;
    }

    @Data
    @AllArgsConstructor
    static class PostDtoForList {
        Long id;
        String title;
        String author;
        String createdTime;

        public PostDtoForList(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.author = post.getMember().getUsername();
            this.createdTime = post.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString();
        }
    }
}
