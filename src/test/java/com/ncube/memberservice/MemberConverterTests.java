package com.ncube.memberservice;

import com.ncube.memberservice.domain.FileVO;
import com.ncube.memberservice.domain.Member;
import com.ncube.memberservice.domain.MemberVO;
import com.ncube.memberservice.domain.converter.MemberConverter;
import com.ncube.memberservice.util.MemberUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class MemberConverterTests {

    private static MemberVO memberVO;
    private static Member member;
    private static Member memberWithoutImage;

    @BeforeClass
    public static void setup() {
        memberVO = MemberUtil.getMemberVO();
        memberWithoutImage = MemberUtil.getMemberWithoutImage();
        member = MemberUtil.getMember();
    }

    @Test
    public void memberVoToMember_withNotNullMember_thenReturnMember() {
        Member member = MemberConverter.memberVoToMember(memberVO);
        assertCommonMemberVoData(memberVO, member);

        assertNotNull(member);
        assertEquals(memberVO.getImage().getName(), member.getImageName());
        assertEquals(memberVO.getImage().getFormat(), member.getImageFormat());

        byte[] data = member.getImage().getData();
        assertEquals(memberVO.getImage().getData(), Base64.getEncoder().encodeToString(data));
    }

    private void assertCommonMemberVoData(MemberVO memberVO, Member member) {
        assertEquals(memberVO.getLastName(), member.getLastName());
        assertEquals(memberVO.getFirstName(), member.getFirstName());
        assertEquals(memberVO.getLastName(), member.getLastName());
        assertEquals(memberVO.getActive(), member.getActive());
        assertEquals(DateUtils.truncate(memberVO.getBirthDate(), Calendar.DATE), member.getBirthDate());
        assertEquals(memberVO.getPostalCode(), member.getPostalCode());
    }

    @Test
    public void memberVoToMemberWithImage_withNotNullMemberVO_thenReturnMember() {
        Member member = MemberConverter.memberVoToMember(memberVO);
        assertCommonMemberVoData(memberVO, member);

        FileVO image = memberVO.getImage();

        assertEquals(image.getName(), member.getImageName());
        assertEquals(image.getFormat(), member.getImageFormat());
        assertNotNull(member.getImage());

        byte[] data = member.getImage().getData();
        assertEquals(memberVO.getImage().getData(), Base64.getEncoder().encodeToString(data));
    }

    @Test
    public void memberToMemberVO_withNotNullMember_thenReturnMemberVoWithoutImage() {
        MemberVO memberVO = MemberConverter.memberToMemberVO(member);
        assertCommonMemberData(memberWithoutImage, memberVO);
        assertNull(memberVO.getImage());
    }

    private void assertCommonMemberData(Member member, MemberVO memberVO) {
        assertEquals(member.getLastName(), memberVO.getLastName());
        assertEquals(member.getFirstName(), memberVO.getFirstName());
        assertEquals(member.getLastName(), memberVO.getLastName());
        assertEquals(member.getActive(), memberVO.getActive());
        assertEquals(member.getBirthDate(), memberVO.getBirthDate());
        assertEquals(member.getPostalCode(), memberVO.getPostalCode());
    }

    @Test
    public void memberToMemberVoWithImage_withNotNullMember_thenReturnMemberVo() {
        MemberVO memberVO = MemberConverter.memberToMemberVoWithImage(member);
        assertCommonMemberData(member, memberVO);

        assertNotNull(memberVO.getImage());
        assertEquals(member.getImageName(), memberVO.getImage().getName());
        assertEquals(member.getImageFormat(), memberVO.getImage().getFormat());

        byte[] data = member.getImage().getData();
        assertEquals(memberVO.getImage().getData(), Base64.getEncoder().encodeToString(data));
    }

    @Test
    public void memberVoToMember_withNullArg_thenReturnStub() {
        Member member = MemberConverter.memberVoToMember(null);
        Member stub = new Member();

        assertEquals(stub, member);
    }

    @Test
    public void memberToMemberVO_withNullArg_thenReturnStub() {
        MemberVO memberVO = MemberConverter.memberToMemberVO(null);
        MemberVO stub = new MemberVO();

        assertEquals(stub, memberVO);
    }

    @Test
    public void memberToMemberVoWithImage_withNullArg_thenReturnStub() {
        MemberVO memberVO = MemberConverter.memberToMemberVoWithImage(null);
        MemberVO stub = new MemberVO();

        assertEquals(stub, memberVO);
    }
}
