package org.regitiny.catiny.gateway.web.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//    HttpHeaders reqHeaders = new HttpHeaders();
//    reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//    MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
//    formParams.set("username", "admin");
//    formParams.set("password", "admin");
//    formParams.set("grant_type", "password");
//    reqHeaders.add("Authorization", "Basic d2ViX2FwcDpjaGFuZ2VpdA==");
//    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, reqHeaders);
//
//    ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.postForEntity("http://catinyuaa/oauth/token", entity, OAuth2AccessToken.class);
//    return responseEntity.getBody().getValue() ;

    HttpHeaders httpHeaders = new HttpHeaders();
    UriBuilder uriBuilde = UriComponentsBuilder.newInstance();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://catinyuaa/entity/master/_search?query=\"yuvytungs\" OR \"yuvytung3\"", String.class);
    return responseEntity.getBody() ;
  }
}
