package com.tt.talktok.service;

import com.tt.talktok.dto.ReviewDto;
import com.tt.talktok.entity.Review;
import com.tt.talktok.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;


    public void reviewWrite(ReviewDto reviewDto) {

//        LocalDateTime currentDateTime = LocalDateTime.now();

        Review review = Review
                .builder()
                .revName(reviewDto.getRev_name())
                .revDetail(reviewDto.getRev_detail())
                .revWriter(reviewDto.getRev_writer())
                .revScore(reviewDto.getRev_score())
//                .revDate(currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .lecNo(reviewDto.getLec_no())
                .lecName(reviewDto.getLec_name())
                .teaNo(reviewDto.getTea_no())
                .teaName(reviewDto.getTea_name())
                .teaNo(reviewDto.getStu_no())
                .build();
        reviewRepository.save(review);
    }

    public Page<ReviewDto> reviewFindAll(String search_target, String keyword, Pageable pageable) {


        Page<Review> reviews = null;
//        if (keyword == null){
        if (search_target == null || search_target == null) {
            System.out.println("keyword is null");
            reviews = reviewRepository.findAll(pageable);

        } else if (search_target.equals("lecture")) {
            reviews = reviewRepository.findByLecNameContaining(pageable, keyword);
        } else if (search_target.equals("teacher")) {
            reviews = reviewRepository.findByTeaNameContaining(pageable, keyword);
        } else if (search_target.equals("name")) {
            reviews = reviewRepository.findByRevNameContaining(pageable, keyword);
        } else if (search_target.equals("detail")) {
            reviews = reviewRepository.findByRevDetailContaining(pageable, keyword);
        } //else if (search_target.equals("name_detail")) {
            //reviews = reviewRepository.findByRevNameContainingOrRevDetailContaining(pageable, keyword);
        //}


        return reviews.map(this::convertToDto);
    }

    private ReviewDto convertToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setRev_no(review.getRevNo());
        reviewDto.setRev_name(review.getRevName());
        reviewDto.setRev_detail(review.getRevDetail());
        reviewDto.setRev_writer(review.getRevWriter());
        reviewDto.setRev_score(review.getRevScore());
        reviewDto.setRev_date(review.getRevDate());
        reviewDto.setLec_no(review.getLecNo());
        reviewDto.setLec_name(review.getLecName());
        reviewDto.setTea_no(review.getTeaNo());
        reviewDto.setTea_name(review.getTeaName());
        reviewDto.setStu_no(review.getStuNo());
        reviewDto.setRev_detail(reviewDto.getRev_detail().replace("\n","<br>"));
        // 엔티티 클래스의 필드를 DTO 클래스에 설정

        return reviewDto;
    }

    public ReviewDto reviewFindDetail(int rev_no) {
        Review review = reviewRepository.findByRevNo(rev_no);
        return convertToDto(review);

    }

    public List<ReviewDto> reviewFindTeacher(int tea_no) {
        List<Review> reviews = reviewRepository.findByTeaNo(tea_no);
        return reviews.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    public Page<ReviewDto> reviewFindAble(int stu_no, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByStuNo(stu_no, pageable);

        return reviews.map(this::convertToDto);

    }
}
