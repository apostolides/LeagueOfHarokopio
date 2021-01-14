package gr.hua.DistSysApp.ritoAPI.Services;

import gr.hua.DistSysApp.ritoAPI.Models.Entities.Authorities;
import gr.hua.DistSysApp.ritoAPI.Models.Entities.User;
import gr.hua.DistSysApp.ritoAPI.Models.Entities.UserPassword;
import gr.hua.DistSysApp.ritoAPI.Repositories.AuthoritiesRepository;
import gr.hua.DistSysApp.ritoAPI.Repositories.UserPasswordRepository;
import gr.hua.DistSysApp.ritoAPI.Repositories.UserRepository;
import gr.hua.DistSysApp.ritoAPI.Utilities.JsonUtils;
import gr.hua.DistSysApp.ritoAPI.Utilities.ResultUtils;
import gr.hua.DistSysApp.ritoAPI.Utilities.UrlUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MainAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String API_KEY = "RGAPI-a2c173c1-16e9-46a5-ba1b-d27da9054550";
    private final static String ROLE_ADMIN = "ROLE_ADMIN";
    private final static String ROLE_PREMIUM_ADMIN = "ROLE_PREMIUM_ADMIN";

    public String manageAllUsers(){
        List<Authorities> allUsers = authoritiesRepository.getAllUsers();

        //TODO CONVERT TO JSON IF NEEDED
        return allUsers.toString();
    }

    public String manageAllAdmins(){
        List<Authorities> allAdmins = authoritiesRepository.getAllAdmins();

        //TODO CONVERT TO JSON IF NEEDED
        return allAdmins.toString();
    }

    @Transactional
    public JSONObject RegisterAdmin(String username, String password, String firstName, String lastName, String email, String summonerName, String role) throws PremiumUserServiceException, JSONException {

        try {
            User tempUser = userRepository.findByUsername(username);
            if (tempUser != null ) {
                return JsonUtils.stringToJsonObject("Status", "Failed: Username already exists!");
            }
            tempUser = userRepository.findByEmail(email);
            if (tempUser != null) {
                return JsonUtils.stringToJsonObject("Status", "Failed: Email already exists!");
            }
            if ( !(role.equals(ROLE_ADMIN) || role.equals(ROLE_PREMIUM_ADMIN)) ) {
                return JsonUtils.stringToJsonObject("Status", "Failed: Wrong role or syntax!");
            }


            String url = UrlUtils.getSummonersURL(summonerName, API_KEY);
            JSONObject response = ResultUtils.getSummonerUrlResponse(url);

            String summonerId;
            if (response == null) {
                summonerName = null;
                summonerId = null;
                //return JsonUtils.stringToJsonObject("Status", "Failed: Summoner not found");
            } else {
                summonerId = JsonUtils.getSummonerId(response);
            }

            User user = new User();
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setSummoner_name(summonerName);
            user.setSummoner_id(summonerId);
            userRepository.saveAndFlush(user);

            String passwordHash = passwordEncoder.encode(password);
            UserPassword userPassword = new UserPassword();
            userPassword.setUser(user);
            userPassword.setUser_id(user.getId());
            userPassword.setPassword_hash(passwordHash);
            userPasswordRepository.saveAndFlush(userPassword);


            Authorities authorities = new Authorities();
            authorities.setUser(user);
            authorities.setUser_id(user.getId());
            authorities.setRole(role);
            authoritiesRepository.saveAndFlush(authorities);

            return JsonUtils.stringToJsonObject("Status", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.stringToJsonObject("Status", "exception");

        }
    }

    @Transactional
    public JSONObject DeleteAdmin(int user_id) throws PremiumUserServiceException, JSONException {

        try {
            //TODO check null exception
            //TODO check if cascade works on UserPassword and Authorities
            User user = userRepository.findById(user_id);
            //userPasswordRepository.delete();
            userRepository.delete(user);
            return JsonUtils.stringToJsonObject("Status", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.stringToJsonObject("Status", "exception");

        }
    }
}