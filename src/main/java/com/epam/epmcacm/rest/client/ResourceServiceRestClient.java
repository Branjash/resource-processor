package com.epam.epmcacm.rest.client;

import com.epam.epmcacm.model.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "resourcesplaceholder", url = "${rest.client.resources.api.url}")
public interface ResourceServiceRestClient {

        @PostMapping
        Resource saveResource(@RequestParam("file") MultipartFile file);

        @GetMapping("{id}")
        ResponseEntity<?> getResourceById(@PathVariable("id") Long id);

        @DeleteMapping
        ResponseEntity<?> deleteResourcesWithIds(@RequestParam("id") List<Long> ids);

}
