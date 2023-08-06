package com.example.servermanagement.resource;

import com.example.servermanagement.enumeration.Status;
import com.example.servermanagement.model.Response;
import com.example.servermanagement.model.Server;
import com.example.servermanagement.service.implementation.ServerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
    private final ServerServiceImpl service;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("servers", service.list(20)))
                .message("Servers retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {
        Server server = service.ping(ipAddress);
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("server", server))
                .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("server", service.create(server)))
                .message("Server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("server", service.get(id)))
                .message("Server retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("deleted", service.delete(id)))
                .message("Server deleted")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/" + fileName));
    }
}
