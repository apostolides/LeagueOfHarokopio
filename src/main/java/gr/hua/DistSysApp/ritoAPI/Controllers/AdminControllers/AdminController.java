package gr.hua.DistSysApp.ritoAPI.Controllers.AdminControllers;

import gr.hua.DistSysApp.ritoAPI.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String admin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello, you have: " + authentication.getAuthorities() + " authorities";
    }


    @GetMapping(path="/users")
    public String getAlLUsers () {
        return adminService.getAllUsers().toString();
    }

    @GetMapping(path="admin/updateRequest/accept")
    @ResponseBody
    public String updateRequest_Accept (@RequestParam int requestId) {
        return adminService.acceptRequest(requestId);
    }
}
