package com.example.sunbase.assignment.controller;

import com.example.sunbase.assignment.entity.Customer;
import com.example.sunbase.assignment.entity.Request;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
public class MyController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${external.url}")
    private String URI;
    private String TOKEN;
    @GetMapping("/")
    public String index() {
        return "index"; // Return the JSP file name without the extension
    }
    @PostMapping("/loginPage")
    public ResponseEntity<String> authentication(@RequestBody Request request) {
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Request> httpEntity=new HttpEntity<>(request,headers);
        ResponseEntity<String> response=restTemplate.postForEntity(URI,httpEntity, String.class);
        if(response.getStatusCode()==HttpStatus.OK) {
            TOKEN=response.getBody();
            return response;
        }
        return new ResponseEntity<>("Credentials are Invalid.",HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer,HttpServletRequest request) {
        TOKEN=request.getHeader("Authorization").substring(7).trim();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);
        HttpEntity<Customer> customerHttpEntity=new HttpEntity<>(customer,headers);
        return restTemplate.postForEntity(URI+"?cmd=create",customerHttpEntity, String.class);
    }
    @GetMapping("/customers")
    public ResponseEntity<String> getAllCustomers(HttpServletRequest request) {
        TOKEN=request.getHeader("Authorization").substring(7).trim();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);
        HttpEntity<Customer> customerHttpEntity=new HttpEntity<>(headers);
        return restTemplate.exchange(URI+"?cmd=get_customer_list", HttpMethod.GET,customerHttpEntity,String.class);
    }
    @PostMapping("/update/{uuid}")
    public ResponseEntity<String> updateCustomer(@PathVariable String uuid,@RequestBody Customer customer,HttpServletRequest request) {
        TOKEN=request.getHeader("Authorization").substring(7).trim();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);
        HttpEntity<Customer> customerHttpEntity=new HttpEntity<>(customer,headers);
        return restTemplate.exchange(URI+"?cmd=update&uuid="+uuid, HttpMethod.POST,customerHttpEntity,String.class);
    }
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String uuid,HttpServletRequest request) {
        TOKEN=request.getHeader("Authorization").substring(7).trim();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);
        HttpEntity<Customer> customerHttpEntity=new HttpEntity<>(headers);
        return restTemplate.exchange(URI+"?cmd=delete&uuid="+uuid, HttpMethod.POST,customerHttpEntity,String.class);
    }
}
