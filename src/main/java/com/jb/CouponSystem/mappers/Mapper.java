package com.jb.CouponSystem.mappers;

import java.util.List;

/**
 * Created by kobis on 24 Oct, 2021
 */
public interface Mapper<DAO,DTO> {

    DAO toDao(DTO dto);
    DTO toDto(DAO dao);

    List<DAO> toDaoList(List<DTO> dtos);
    List<DTO> toDtoList(List<DAO> daos);
}
