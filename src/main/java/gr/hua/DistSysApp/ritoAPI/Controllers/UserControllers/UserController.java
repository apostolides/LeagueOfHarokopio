package gr.hua.DistSysApp.ritoAPI.Controllers.UserControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import gr.hua.DistSysApp.ritoAPI.Models.GoPremiumRequest;
import gr.hua.DistSysApp.ritoAPI.Models.RegisterRequest;
import gr.hua.DistSysApp.ritoAPI.Repositories.UserRepository;
import gr.hua.DistSysApp.ritoAPI.Services.AdminServiceException;
import gr.hua.DistSysApp.ritoAPI.Services.PremiumUserServiceException;
import gr.hua.DistSysApp.ritoAPI.Services.UserService;
import gr.hua.DistSysApp.ritoAPI.exceptionHandling.ResourceNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private final static String MatchHistoryRequestType = "Match History";
    private final static String MyProfileRequestType = "My Profile";
    private final static String ChampionStatisticsRequestType = "Champion Statistics";
    private final static String LeaderboardsRequestType = "Leaderboards";


    @GetMapping("/user")
    public String user(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return "Hello, " + username + " you have: " + authentication.getAuthorities() + " authorities";
    }

    @PostMapping(path="user/register")
    public String Register (@RequestBody RegisterRequest registerRequest) throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.Register(registerRequest.getUsername(), registerRequest.getPassword(),registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getEmail(),registerRequest.getSummonerName());
            if (response==null) throw new ResourceNotFoundException("Error while making the request");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @PostMapping(path="user/goPremium")
    public String goPremium (@RequestBody GoPremiumRequest goPremiumRequest) throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.requestGoPremium(goPremiumRequest.getPaysafe());
            if (response==null) throw new ResourceNotFoundException("Error while making the request");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/getMyRequests")
    public String getMyRequests (@RequestParam String requestStatus) throws JSONException, ResourceNotFoundException, PremiumUserServiceException {
        try{
            String response = userService.getMyRequests(requestStatus);
            if (response==null) throw new ResourceNotFoundException("Error while making the request");
            return response;
        } catch (JsonProcessingException | AdminServiceException e) {
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/requestMatchHistory")
    public String requestMatchHistory () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.requestMatchHistory();
            if (response==null) throw new ResourceNotFoundException("Error while making the request");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/showMatchHistory")
    public String getMatchHistory () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.showRequestResults(MatchHistoryRequestType);
            if (response==null) throw new ResourceNotFoundException("Match History Data not found");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/requestLeaderboards")
    public String requestLeaderboards () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.requestLeaderboards();
            if (response==null) throw new ResourceNotFoundException("Error while making the request");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/showLeaderboards")
    public String getLeaderboards () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.showRequestResults(LeaderboardsRequestType);
            if (response==null) throw new ResourceNotFoundException("Match History Data not found");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/requestMyProfile")
    public String requestMyProfile () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.requestMyProfile();
            if (response==null) throw new ResourceNotFoundException("Error while making the request");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/showMyProfile")
    public String getMyProfile () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.showRequestResults(MyProfileRequestType);
            if (response==null) throw new ResourceNotFoundException("Match History Data not found");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/requestChampionStatistics")
    public String requestChampionStats () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.requestChampionsMastery();
            if (response==null) throw new ResourceNotFoundException("Error while making the request");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }

    @GetMapping(path="/user/showChampionStats")
    public String getChampionStats () throws JSONException, PremiumUserServiceException, ResourceNotFoundException {
        try{
            JSONObject response = userService.showRequestResults(ChampionStatisticsRequestType);
            if (response==null) throw new ResourceNotFoundException("Match History Data not found");
            return response.toString();
        }catch (PremiumUserServiceException e){
            throw new PremiumUserServiceException("Internal Server Exception while getting exception");
        }
    }


}