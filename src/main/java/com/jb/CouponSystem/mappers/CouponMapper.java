package com.jb.CouponSystem.mappers;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CouponMapper implements  Mapper<Coupon, CouponDto> {

    private final DateMapper dateMapper;
    @Override
    public Coupon toDao(CouponDto couponDto) {
        return Coupon.builder()
                .id(couponDto.getId())
                .company(couponDto.getCompany())
                .category(couponDto.getCategory())
                .title(couponDto.getTitle())
                .description(couponDto.getDescription())
                .startDate(couponDto.getStartDate())
                .endDate(couponDto.getEndDate())
                .amount(couponDto.getAmount())
                .price(couponDto.getPrice())
                .image(couponDto.getImage())
                .build();
    }

    @Override
    public CouponDto toDto(Coupon coupon) {
        return CouponDto.builder()
                .id(coupon.getId())
                .company(coupon.getCompany())
                .category(coupon.getCategory())
                .title(coupon.getTitle())
                .description(coupon.getDescription())
                .startDate(coupon.getStartDate())
                .endDate(coupon.getEndDate())
                .amount(coupon.getAmount())
                .price(coupon.getPrice())
                .image(coupon.getImage())
                .build();
    }

    @Override
    public List<Coupon> toDaoList(List<CouponDto> couponDtos) {
        return couponDtos.stream().map(this::toDao).collect(Collectors.toList());
    }

    @Override
    public List<CouponDto> toDtoList(List<Coupon> coupons) {
        return coupons.stream().map(this::toDto).collect(Collectors.toList());
    }
}
