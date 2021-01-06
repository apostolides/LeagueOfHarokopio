package gr.hua.DistSysApp.ritoAPI.Controllers.UserControllers;

import gr.hua.DistSysApp.ritoAPI.Repositories.UserRepository;
import gr.hua.DistSysApp.ritoAPI.Services.PremiumUserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PremiumUserController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private PremiumUserService premiumUserService;

    @GetMapping(path="/premiumUser/showLiveMatchStats")
    public String showLiveMatchStats () throws JSONException { return premiumUserService.showLiveMatchStats(); }

    @GetMapping(path="/premiumUser/requestTopPlayersProfiles")
    public JSONObject requestTopPlayersProfiles () throws JSONException { return premiumUserService.requestTopPlayersProfiles(); }

    @GetMapping(path="/premiumUser/showTopPlayersProfiles")
    @ResponseBody
    public JSONObject showTopPlayersProfiles (@RequestParam int requestId) throws JSONException { return premiumUserService.showRequestResults(requestId); }
}
