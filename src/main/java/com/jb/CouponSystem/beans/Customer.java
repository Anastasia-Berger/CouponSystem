package com.jb.CouponSystem.beans;

import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "customer_vs_coupons")
    private List<Coupon> coupons = new ArrayList<>();

    public void setId(int id) throws CouponSystemException {
        if (this.id == 0) {
            this.id = id;
        } else {
            throw new CouponSystemException(ErrMsg.ILLEGAL_ACTION_EXCEPTION);
        }
    }


}
