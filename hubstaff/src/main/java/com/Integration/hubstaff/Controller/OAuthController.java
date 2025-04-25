package com.Integration.hubstaff.Controller;

import com.Integration.hubstaff.Config.HubstaffConfig;
import com.Integration.hubstaff.Service.OAuthServiceImpl;
import com.Integration.hubstaff.Util.MessageUtil;
import com.Integration.hubstaff.Util.ResponseHandler;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authorization")
public class OAuthController {

    @Autowired
    private OAuthServiceImpl oAuthService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private HubstaffConfig hubstaffConfig;


    @PostMapping("/authorize")
    public ResponseEntity<ResponseHandler> authorize(){
        try{
            oAuthService.getCode();
            ResponseHandler repsonse= new ResponseHandler(messageUtil.getMessage("auth.success"), HttpStatus.OK.value(), true, "authorization", null);
            return ResponseEntity.ok(repsonse);
        }catch (IOException e){
            ResponseHandler repsonse= new ResponseHandler(e.getMessage(), 400, false, "authorization", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(repsonse);
        }
    }

    @GetMapping("/callback")
    public ResponseEntity<ResponseHandler> getAccessToken(@RequestParam("code") String code) {
        try {
            oAuthService.exchangeCodeForToken(code);
            ResponseHandler response = new ResponseHandler("Authorization successful!", HttpStatus.OK.value(), true, "authorization", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseHandler response = new ResponseHandler(
                    "Authorization failed: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), false, "authorization", null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    //testing scheduler
//    @PostMapping("/refreshToken")
//    public ResponseEntity<ResponseHandler> getRefreshToken(){
//        try{
//            oAuthService.refreshToken();
//            ResponseHandler response= new ResponseHandler(messageUtil.getMessage("refresh.get.success"),HttpStatus.OK.value(), true, "Authorization", null);
//            return ResponseEntity.ok(response);
//        }catch (Exception e){
//            ResponseHandler response= new ResponseHandler(e.getMessage(),400, false,"authorization",null);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
//


}
