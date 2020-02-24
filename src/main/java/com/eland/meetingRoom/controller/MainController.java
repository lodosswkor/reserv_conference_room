package com.eland.meetingRoom.controller;

import com.eland.meetingRoom.dto.MeetingRoomDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {

    @RequestMapping(value="", method= RequestMethod.GET)
    public String index(){
        return "redirect:/res";
    }
}
