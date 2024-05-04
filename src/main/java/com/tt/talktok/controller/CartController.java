package com.tt.talktok.controller;


import com.tt.talktok.dto.CartDto;
import com.tt.talktok.dto.StudentDto;
import com.tt.talktok.entity.Cart;
import com.tt.talktok.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 추가
    @PostMapping("/add")
    public String cart(@RequestParam("lec_no") int lec_no, @ModelAttribute CartDto cartDto, HttpSession session, Model model) {
        StudentDto studentDto = (StudentDto) session.getAttribute("studentDto");

        if(studentDto == null){
            return "cart/loginmessage";
        } else {

            // 장바구니에 기존 상품이 있는지 검사
            int count = cartService.countCart(lec_no, studentDto.getStuNo());

            if(count == 0){
                System.out.println("studentDto.getStuNo() : "+studentDto.getStuNo());
                System.out.println("lectureDto.getLec_no() : "+lec_no);

                cartService.addCart(studentDto.getStuNo(), lec_no);

                System.out.println("장바구니 추가");

                return "cart/smessage";

            } else{
                return "cart/fmessage";
            }
        }
    }

    // 장바구니 목록 조회
    @GetMapping("/list")
    public String cartList(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page) {
        StudentDto studentDto = (StudentDto) session.getAttribute("studentDto");

        if(studentDto == null){
            return "redirect:/student/login";
        }else{
            List<Cart> cartItems = cartService.getCartItems(studentDto.getStuNo());
            System.out.println("cartItems : "+ cartItems.toString());
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("page", page);
        }

        return "cart/list";
    }
}