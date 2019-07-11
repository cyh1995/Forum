package cyh.nwu.spring.spring.Controller;

import cyh.nwu.spring.spring.DTO.Access_Token_DTO;
import cyh.nwu.spring.spring.DTO.GithubUser;
import cyh.nwu.spring.spring.Provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("github.clint.id ")
    private String clintid;
    @Value("github.client_secret")
    private String clintsecret;
    @Value("redircet_uri")
    private String redirecturi;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
        Access_Token_DTO accesstoken = new Access_Token_DTO();
        accesstoken.setClient_id(clintid);
        accesstoken.setClient_secret(clintsecret);
        accesstoken.setCode(code);
        accesstoken.setState(state);
        accesstoken.setRedirect_uri(redirecturi);
        String accessToken = githubProvider.getAccessToken(accesstoken);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
