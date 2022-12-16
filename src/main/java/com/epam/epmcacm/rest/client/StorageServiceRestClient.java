package com.epam.epmcacm.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(name = "storage-api",value = "storage-api", url = "${rest.client.storage-api.url}")
public interface StorageServiceRestClient {

    @PutMapping("{id}")
    ResponseEntity<?> updateResourceInStorageToPermanent(@PathVariable("id") Long storageId);

    @GetMapping("{id}")
    ResponseEntity<ByteArrayResource> getResourceFromStorage(@PathVariable("id") Long storageId);


}