package com.ncube.memberservice.service;

import com.ncube.memberservice.domain.MemberVO;

import java.util.List;

public interface MemberService {

    void create(MemberVO member);

    MemberVO findById(String id, Boolean loadImage);

    boolean update(MemberVO member, String id);

    boolean deleteMembersByActive(Boolean isActive);

    List<MemberVO> findAll(Boolean loadImage);

}
