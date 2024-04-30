package com.tt.talktok.service;

import com.tt.talktok.dto.LectureDto;
import com.tt.talktok.dto.PaymentDto;
import com.tt.talktok.entity.Lecture;
import com.tt.talktok.entity.Payment;
import com.tt.talktok.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 결제 정보 저장
    public PaymentDto save(PaymentDto paymentDto) {
        Payment payment = convertToEntity(paymentDto);
        payment = paymentRepository.save(payment); // 엔티티를 저장하고 저장된 엔티티를 반환
        return convertToDto(payment);
    }

    private Payment convertToEntity(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setStuEmail(paymentDto.getStu_Email());
        payment.setPay_price(paymentDto.getPay_price());
        payment.setLec_name(paymentDto.getLec_name());
        payment.setLecNo(paymentDto.getLec_no());
        payment.setPay_time(new Timestamp(System.currentTimeMillis())); // 현재 시간으로 설정
        return payment;
    }

    private PaymentDto convertToDto(Payment payment) { // Payment 엔티티를 받아서
        PaymentDto dto = new PaymentDto(); //새로운 PaymentDto 객체 생성
        dto.setStu_Email(payment.getStuEmail()); // 엔티티의 각 필드 값을 DTO 의 필드에 복사
        dto.setPay_price(payment.getPay_price());
        dto.setLec_name(payment.getLec_name());
        dto.setLec_no(payment.getLecNo());
        dto.setPay_time(payment.getPay_time());
        return dto; // 변환된 Dto 객체를 반환
    }
}
