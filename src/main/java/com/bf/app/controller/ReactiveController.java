package com.bf.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.bf.app.entities.Authority;
import com.bf.app.service.AuthorityService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
public class ReactiveController {
	
	private AuthorityService authorityService;
	
	private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/demo/").build();
	
	@Autowired
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}
	
	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	@GetMapping("authority/tree")
	public Flux<Authority> authorityTree(long parentId) {
		return Flux.fromIterable(authorityService.getDescendentAuthTree(parentId));
	}
	
	@GetMapping("delegate")
	public Flux<Authority> delegateAuthorityTree() {
		return webClient.get()
				.uri("reactive/authority/tree?parentId=-1")
				.retrieve()
				.bodyToFlux(Authority.class);
	}

}
