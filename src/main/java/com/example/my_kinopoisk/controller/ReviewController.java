package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.entity.Review;
import com.example.my_kinopoisk.domain.entity.security.User;
import com.example.my_kinopoisk.service.ReviewService;
import com.example.my_kinopoisk.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasAuthority('read')")
    @Validated(OnCreate.class)
    public ResponseEntity<Review> saveReview(@Valid @RequestBody Review review, Principal principal) {
        var user = new User();//to service
        user.setId(0L);//без дефолтного значения  не работает,хотя связываю по другой колонке
        user.setUserName(principal.getName());

        review.setUser(user);
        return ResponseEntity.ok(reviewService.save(review));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Review> updateReview(@Valid @RequestBody Review review, Principal principal) throws NoPermissionException {
        if (principal.getName().equals(review.getUser().getUserName())) {
            return ResponseEntity.ok(reviewService.save(review));
        }
        throw new NoPermissionException("You didn't have permission to modify it");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete_comments')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete-own")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Void> deleteReview(@Valid @RequestBody Review review, Principal principal) throws NoPermissionException {
        if (principal.getName().equals(review.getUser().getUserName())) {
            return ResponseEntity.noContent().build();
        }
        throw new NoPermissionException("You didn't have permission to modify it");
    }


}
