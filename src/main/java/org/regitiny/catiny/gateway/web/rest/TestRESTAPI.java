package org.regitiny.catiny.gateway.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class TestRESTAPI
{

  final RestTemplate restTemplate;

  public TestRESTAPI(@Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate)
  {
    this.restTemplate = restTemplate;
  }

  @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public String authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params)
  {

    HttpHeaders reqHeaders = new HttpHeaders();
    reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
    formParams.set("username", "admin");
    formParams.set("password", "admin");
    formParams.set("grant_type", "password");
    reqHeaders.add("Authorization", "Basic d2ViX2FwcDpjaGFuZ2VpdA==");
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, reqHeaders);

    ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.postForEntity("http://catinyuaa/oauth/token", entity, OAuth2AccessToken.class);
    return "ok bebi" ;
  }
}
