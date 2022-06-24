package com.jb.CouponSystem.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kobis on 24 Oct, 2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrDto {

    private String title;
    private String description;
}
