package com.jb.CouponSystem.beans;

import com.jb.CouponSystem.enums.ClientType;
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

@Table(name = "companies")
public class Company extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @OneToMany
    @JoinTable(name = "companies_vs_coupons")
    private List<Coupon> coupons = new ArrayList<>();

    public void setId(int id) throws CouponSystemException {
        if (this.id == 0) {
            this.id = id;
        }
        throw new CouponSystemException(ErrMsg.ILLEGAL_ACTION_EXCEPTION);
    }

}

