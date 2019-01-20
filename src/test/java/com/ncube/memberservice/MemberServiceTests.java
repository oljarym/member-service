package com.ncube.memberservice;

import com.ncube.memberservice.domain.Member;
import com.ncube.memberservice.domain.MemberVO;
import com.ncube.memberservice.repository.MemberRepository;
import com.ncube.memberservice.service.MemberService;
import com.ncube.memberservice.util.MemberUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.ncube.memberservice.util.MemberUtil.ID;
import static com.ncube.memberservice.util.MemberUtil.objectId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTests {

    private static MemberVO memberVO;
    private static MemberVO memberVoWithoutImage;
    private static Member member;
    private static Member memberWithoutImage;
    private static List<MemberVO> memberVoList = new ArrayList<>();
    private static List<MemberVO> memberVoListWithoutImages = new ArrayList<>();
    private static List<Member> memberList = new ArrayList<>();

    @Autowired
    private MemberService memberService;
    @MockBean
    private MemberRepository memberRepository;

    @BeforeClass
    public static void setupData() {
        memberVO = MemberUtil.getMemberVO();
        member = MemberUtil.getMember();
        memberVoWithoutImage = MemberUtil.getMemberVoWithoutImage();
        memberWithoutImage = MemberUtil.getMemberWithoutImage();

        member.set_id(objectId);
        memberWithoutImage.set_id(objectId);
        memberVO.setId(ID);
        memberVoWithoutImage.setId(ID);

        Date birthDate = DateUtils.truncate(MemberUtil.birthDate, Calendar.DATE);
        member.setBirthDate(birthDate);
        memberWithoutImage.setBirthDate(birthDate);
        memberVO.setBirthDate(birthDate);
        memberVoWithoutImage.setBirthDate(birthDate);

        memberVoList.add(memberVO);
        memberVoListWithoutImages.add(memberVoWithoutImage);
        memberList.add(member);
    }

    @Test
    public void findById_withLoadWithImageTrue_thenReturnMemberVO() {
        when(memberRepository.findBy_id(objectId)).thenReturn(member);

        MemberVO memberVoResult = memberService.findById(ID, true);

        assertNotNull(memberVoResult);
        assertEquals(memberVO, memberVoResult);
        assertNotNull(memberVoResult.getImage());

        verify(memberRepository).findBy_id(objectId);
    }

    @Test
    public void findById_withLoadWithImageFalse_thenReturnMemberVO() {
        when(memberRepository.findBy_id(objectId)).thenReturn(memberWithoutImage);

        MemberVO memberVO = memberService.findById(ID, false);

        assertNotNull(memberVO);
        assertEquals(memberVoWithoutImage, memberVO);
        assertNull(memberVO.getImage());

        verify(memberRepository).findBy_id(objectId);
    }

    @Test
    public void findById_withLoadWithImageFalse_thenReturnEmptyMember() {
        when(memberRepository.findBy_id(any(ObjectId.class))).thenReturn(null);

        MemberVO memberVO = memberService.findById(ID, false);

        assertEquals(new MemberVO(), memberVO);
        verify(memberRepository).findBy_id(objectId);
    }

    @Test
    public void update_withExistedMember_thenReturnTrue() {
        when(memberRepository.findBy_id(objectId)).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(member);

        boolean updated = memberService.update(memberVO, ID);

        assertTrue(updated);
        verify(memberRepository).findBy_id(objectId);
        verify(memberRepository).save(member);
    }

    @Test
    public void update_withNotExistedMember_thenReturnFalse() {
        when(memberRepository.findBy_id(objectId)).thenReturn(null);

        boolean updated = memberService.update(memberVO, ID);

        assertFalse(updated);
        verify(memberRepository).findBy_id(objectId);
    }

    @Test
    public void deleteMembersByActive_withActiveFalse_thenReturnTrue() {
        boolean removed = memberService.deleteMembersByActive(false);

        assertTrue(removed);
        verify(memberRepository).removeAllByActiveFalse();
    }

    @Test
    public void deleteMembersByActive_withActiveTrue_thenReturnFalse() {
        boolean removed = memberService.deleteMembersByActive(true);
        assertFalse(removed);
    }

    @Test
    public void findAll_withLoadImageTrue_thenReturnMemberVoListWithImages() {
        when(memberRepository.findAll()).thenReturn(memberList);

        List<MemberVO> result = memberService.findAll(true);

        assertEquals(memberVoList, result);
        verify(memberRepository).findAll();
    }

    @Test
    public void findAll_withLoadImageFalse_thenReturnMemberVoListWithoutImages() {
        when(memberRepository.findAll()).thenReturn(memberList);

        List<MemberVO> result = memberService.findAll(false);

        assertEquals(memberVoListWithoutImages, result);
        verify(memberRepository).findAll();
    }

    @Test
    public void findAll_withLoadImageAny_thenReturnEmptyList() {
        when(memberRepository.findAll()).thenReturn(Collections.emptyList());

        List<MemberVO> result = memberService.findAll(true);

        assertEquals(Collections.emptyList(), result);
        verify(memberRepository).findAll();
    }
}
