package gr.hua.DistSysApp.ritoAPI.Controllers.UserControllers;

import gr.hua.DistSysApp.ritoAPI.Models.Entities.User;
import gr.hua.DistSysApp.ritoAPI.Repositories.UserRepository;
import gr.hua.DistSysApp.ritoAPI.Services.AdminService;
import gr.hua.DistSysApp.ritoAPI.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public String user(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return "Hello, " + username + " you have: " + authentication.getAuthorities() + " authorities";
    }

    @GetMapping(path="/user/requestMatchHistory")
    public String requestMatchHistory () { return userService.requestMatchHistory(); }

    @GetMapping(path="/user/showMatchHistory")
    @ResponseBody
    public String getMatchHistory (@RequestParam int requestId) { return userService.showMatchHistory(requestId); }

    @GetMapping(path="/user/requestLeaderboards")
    public String requestLeaderboards () { return userService.requestLeaderboards(); }

}