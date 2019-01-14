package com.ncube.memberservice.controller;

import com.ncube.memberservice.domain.MemberVO;
import com.ncube.memberservice.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;


@Api(value = "members", description = "Operations you can do with members list")
@RestController
@RequestMapping(value = "/members", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "Create new member", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.POST, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<Void> createMember(@RequestBody MemberVO member) {
        memberService.create(member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Find the member by id. " +
            "Optional parameter loadWithImage allows get member data without loading image (if exist)", response = ResponseEntity.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MemberVO> findMember(@PathVariable String id,
                                               @RequestParam(required = false, value = "loadWithImage") Boolean loadImage) {
        if (Objects.isNull(loadImage)) {
            loadImage = true;
        }
        MemberVO member = memberService.findById(id, loadImage);
        return Objects.nonNull(member.getId())
                ? ResponseEntity.ok(member)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Update the member with specified id", response = ResponseEntity.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<Void> updateMember(@PathVariable String id, @RequestBody MemberVO member) {
        boolean updated = memberService.update(member, id);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation(value = "Delete all members with are not active", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMembersByActive(@RequestParam Boolean isActive) {
        boolean result = memberService.deleteMembersByActive(isActive);
        return result ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ApiOperation(value = "Get the list of all members. " +
            "Optional parameter loadWithImage allows get members data without loading images (if exist)", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MemberVO>> findAllMembers(@RequestParam(required = false, value = "loadWithImage") Boolean loadImage) {
        if (Objects.isNull(loadImage)) {
            loadImage = true;
        }
        List<MemberVO> members = memberService.findAll(loadImage);
        return ResponseEntity.ok(members);
    }
}
