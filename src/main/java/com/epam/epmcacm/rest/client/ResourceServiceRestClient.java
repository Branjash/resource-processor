package com.epam.epmcacm.rest.client;

import com.epam.epmcacm.model.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "resource-api",value = "resource-api", url = "${rest.client.resource-api.url}")
public interface ResourceServiceRestClient {

        @PostMapping
        Resource saveResource(@RequestParam("file") MultipartFile file);

        @PutMapping("{id}")
        ResponseEntity<?> updateResource(@PathVariable("id") Long id,@RequestParam("file") MultipartFile file);

        @GetMapping("{id}")
        ResponseEntity<ByteArrayResource> getResourceById(@PathVariable("id") Long id);

        @DeleteMapping
        ResponseEntity<?> deleteResourcesWithIds(@RequestParam("id") List<Long> ids);

}
