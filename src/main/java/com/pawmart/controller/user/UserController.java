package com.pawmart.controller.user;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/api/user/test")
    public String user() {
        return "Hello User";
    }
}
