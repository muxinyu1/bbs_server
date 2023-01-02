package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.ActionRequest;
import com.mxy.bbs_server.response.action.ActionResponse;
import com.mxy.bbs_server.service.ActionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/like")
    public ActionResponse like(ActionRequest actionRequest) {
        return actionService.like(actionRequest);
    }
}
