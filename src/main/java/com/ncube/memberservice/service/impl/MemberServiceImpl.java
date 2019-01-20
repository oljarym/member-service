package com.ncube.memberservice.service.impl;

import com.ncube.memberservice.domain.Member;
import com.ncube.memberservice.domain.MemberVO;
import com.ncube.memberservice.domain.converter.MemberConverter;
import com.ncube.memberservice.repository.MemberRepository;
import com.ncube.memberservice.service.MemberService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ncube.memberservice.domain.converter.MemberConverter.memberToMemberVO;
import static com.ncube.memberservice.domain.converter.MemberConverter.memberToMemberVoWithImage;
import static com.ncube.memberservice.domain.converter.MemberConverter.memberVoToMember;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Autowired
    public MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(MemberVO member) {
        repository.save(memberVoToMember(member));
    }

    @Override
    public MemberVO findById(String id, Boolean loadImage) {
        Member member = repository.findBy_id(new ObjectId(id));
        return loadImage
                ? memberToMemberVoWithImage(member)
                : memberToMemberVO(member);
    }

    @Override
    public boolean update(MemberVO memberVo, String id) {
        Member member = repository.findBy_id(new ObjectId(id));
        Member saved = new Member();
        if (Objects.nonNull(member)) {
            Member memberData = memberVoToMember(memberVo);
            memberData.set_id(member.get_id());
            saved = repository.save(memberData);
        }
        return Objects.nonNull(saved.get_id());
    }

    @Override
    public boolean deleteMembersByActive(Boolean isActive) {
        if (!isActive) {
            repository.removeAllByActiveFalse();
        }
        return !isActive;
    }

    @Override
    public List<MemberVO> findAll(Boolean loadImage) {
        List<Member> members = repository.findAll();
        return loadImage
                ? members.stream().map(MemberConverter::memberToMemberVoWithImage).collect(Collectors.toList())
                : members.stream().map(MemberConverter::memberToMemberVO).collect(Collectors.toList());
    }
}
