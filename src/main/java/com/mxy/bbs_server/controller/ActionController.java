package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.ActionRequest;
import com.mxy.bbs_server.response.action.ActionResponse;
import com.mxy.bbs_server.service.ActionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action")
@CrossOrigin("*")
public class ActionController {

    private final ActionService actionService;

    public ActionController(@RequestBody ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/like")
    public ActionResponse like(@RequestBody ActionRequest actionRequest) {
        return actionService.like(actionRequest);
    }
}
