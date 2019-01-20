package com.ncube.memberservice;

import com.ncube.memberservice.controller.MemberController;
import com.ncube.memberservice.domain.MemberVO;
import com.ncube.memberservice.service.impl.MemberServiceImpl;
import com.ncube.memberservice.util.MemberUtil;
import com.ncube.memberservice.util.TestUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static com.ncube.memberservice.util.MemberUtil.ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MemberServiceApplication.class)
public class MemberControllerTests {

    private static String emptyList = "[]";
    private static MemberVO memberVO;
    private static MemberVO stub;

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private MemberController memberController;
    @MockBean
    private MemberServiceImpl memberService;
    private MockMvc mockMvc;

    @BeforeClass
    public static void setupClass() {
        memberVO = MemberUtil.getMemberVO();
        stub = new MemberVO();
    }

    @Before
    public void setup() {
        mockMvc = standaloneSetup(memberController).build();
    }

    @Test
    public void createMember_withNotNullMemberVO_thenReturnCreatedStatus() throws Exception {
        String data = TestUtil.convertObjectToJsonString(memberVO);

        String urlTemplate = "/members";
        mockMvc.perform(post(urlTemplate)
                .content(data)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(memberService).create(memberVO);
    }

    @Test
    public void findAllMembers_withLoadWithImageFalse_thenReturnEmptyList() throws Exception {
        when(memberService.findAll(false)).thenReturn(new ArrayList<>());

        String urlTemplate = "/members?loadWithImage=false";
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(emptyList));

        verify(memberService).findAll(false);
    }

    @Test
    public void findAllMembers_withLoadWithImageTrue_thenReturnEmptyList() throws Exception {
        when(memberService.findAll(true)).thenReturn(new ArrayList<>());

        String urlTemplate = "/members?loadWithImage=true";
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(emptyList));

        verify(memberService).findAll(true);
    }


    @Test
    public void findMemberById_withLoadWithImageTrue_thenReturnNotFound() throws Exception {
        when(memberService.findById(ID, true)).thenReturn(stub);

        String urlTemplate = "/members/" + ID + "?loadWithImage=true";
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        verify(memberService).findById(ID, true);
    }

    @Test
    public void findMemberById_withLoadWithImageFalse_thenReturnNotFound() throws Exception {
        when(memberService.findById(ID, false)).thenReturn(stub);

        String urlTemplate = "/members/" + ID + "?loadWithImage=false";
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        verify(memberService).findById(ID, false);
    }

    @Test
    public void updateMember_withMemberVoAndWrongId_thenReturnNotFound() throws Exception {
        when(memberService.update(memberVO, ID)).thenReturn(true);

        String data = TestUtil.convertObjectToJsonString(memberVO);
        String urlTemplate = "/members/" + ID;
        mockMvc.perform(put(urlTemplate)
                .content(data)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        verify(memberService).update(memberVO, ID);
    }

    @Test
    public void deleteMembersByActiveFalse_ShouldDeleteMembers() throws Exception {
        when(memberService.deleteMembersByActive(false)).thenReturn(true);

        String urlTemplate = "/members?isActive=false";
        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        verify(memberService).deleteMembersByActive(false);
    }

    @Test
    public void deleteMembersByActiveTrue_ShouldNotDeleteMembers() throws Exception {
        when(memberService.deleteMembersByActive(true)).thenReturn(false);

        String urlTemplate = "/members?isActive=true";
        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn();
        verify(memberService).deleteMembersByActive(true);
    }
}

