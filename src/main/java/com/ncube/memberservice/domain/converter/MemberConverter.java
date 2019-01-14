package com.ncube.memberservice.domain.converter;

import com.ncube.memberservice.domain.FileVO;
import com.ncube.memberservice.domain.Member;
import com.ncube.memberservice.domain.MemberVO;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.Binary;

import java.util.Base64;
import java.util.Calendar;
import java.util.Objects;


public final class MemberConverter {

    private MemberConverter() {

    }

    public static Member memberVOtoMember(MemberVO memberVO) {
        Member member = new Member();
        if (Objects.nonNull(memberVO)) {
            member.setFirstName(memberVO.getFirstName());
            member.setLastName(memberVO.getLastName());
            member.setBirthDate(DateUtils.ceiling(memberVO.getBirthDate(), Calendar.DATE));
            member.setPostalCode(memberVO.getPostalCode());
            member.setActive(memberVO.getActive());

            FileVO fileVO = memberVO.getImage();
            if (Objects.nonNull(fileVO)) {
                member.setImage(fileVOtoBinary(fileVO));
                member.setImageFormat(fileVO.getFormat());
                member.setImageName(fileVO.getName());
            }
        }
        return member;
    }

    private static Binary fileVOtoBinary(FileVO fileVO) {
        byte[] imageByte = Base64.getDecoder().decode(fileVO.getData());
        return new Binary(imageByte);
    }

    public static MemberVO memberToMemberVO(Member member) {
        MemberVO memberVO = new MemberVO();
        if (Objects.nonNull(member) && Objects.nonNull(member.get_id())) {
            memberVO.setId(member.get_id().toHexString());
            memberVO.setFirstName(member.getFirstName());
            memberVO.setLastName(member.getLastName());
            memberVO.setBirthDate(member.getBirthDate());
            memberVO.setActive(member.getActive());
            memberVO.setPostalCode(member.getPostalCode());
        }
        return memberVO;
    }

    public static MemberVO memberToMemberVOwithImage(Member member) {
        MemberVO memberVO = memberToMemberVO(member);
        Binary image = member.getImage();
        if (Objects.nonNull(image)) {
            FileVO fileVO = binaryToFileVo(image);
            fileVO.setFormat(member.getImageFormat());
            fileVO.setName(member.getImageName());
            memberVO.setImage(fileVO);
        }
        return memberVO;
    }

    private static FileVO binaryToFileVo(Binary binary) {
        FileVO fileVO = new FileVO();
        if (Objects.nonNull(binary)) {
            byte[] imageByte = binary.getData();
            String data = Base64.getEncoder().encodeToString(imageByte);
            fileVO.setData(data);
        }
        return fileVO;
    }
}
