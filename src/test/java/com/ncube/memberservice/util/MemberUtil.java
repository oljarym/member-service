package com.ncube.memberservice.util;

import com.ncube.memberservice.domain.FileVO;
import com.ncube.memberservice.domain.Member;
import com.ncube.memberservice.domain.MemberVO;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public final class MemberUtil {

    public static String ID = "518cbb1389da79d3a25453f9";
    public static ObjectId objectId = new ObjectId(ID);
    public static Date birthDate = new Date();

    private static String FIRST_NAME = "Jon";
    private static String LAST_NAME = "Doe";
    private static String POSTAL_CODE = "123456";
    private static String IMAGE_NAME = "test";
    private static String IMAGE_FORMAT = "png";
    private static String IMAGE_DATA = "dGVzdA==";

    private MemberUtil() {
    }

    public static MemberVO getMemberVO() {
        MemberVO memberVO = getMemberVoWithoutImage();

        FileVO fileVO = new FileVO();
        fileVO.setName(IMAGE_NAME);
        fileVO.setFormat(IMAGE_FORMAT);
        fileVO.setData(IMAGE_DATA);

        memberVO.setImage(fileVO);
        return memberVO;
    }

    public static MemberVO getMemberVoWithoutImage() {
        MemberVO memberVO = new MemberVO();
        memberVO.setFirstName(FIRST_NAME);
        memberVO.setLastName(LAST_NAME);
        memberVO.setBirthDate(birthDate);
        memberVO.setPostalCode(POSTAL_CODE);
        memberVO.setActive(true);
        return memberVO;
    }

    public static Member getMember() {
        Member member = getMemberWithoutImage();
        member.setImageName(IMAGE_NAME);
        member.setImageFormat(IMAGE_FORMAT);
        byte[] data = Base64.getDecoder().decode(IMAGE_DATA);
        member.setImage(new Binary(data));
        return member;
    }

    public static Member getMemberWithoutImage() {
        Member member = new Member();
        member.set_id(objectId);
        member.setFirstName(FIRST_NAME);
        member.setLastName(LAST_NAME);
        member.setPostalCode(POSTAL_CODE);
        member.setActive(true);
        member.setBirthDate(DateUtils.truncate(birthDate, Calendar.DATE));
        return member;
    }
}
