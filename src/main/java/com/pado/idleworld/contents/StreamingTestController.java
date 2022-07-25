package com.pado.idleworld.contents;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StreamingTestController {

    @GetMapping("/streaming-test")
    public String streaming(){
        return "streaming-test";
    }
}
