package com.skillio.api_v1.controllers.security;

import com.skillio.api_v1.config.jwt.JwtUtils;
import com.skillio.api_v1.repository.estudiante.EstudianteRepository;
import com.skillio.api_v1.repository.usuario.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private JwtUtils jwtUtils;
    private UsuarioRepository usuarioRepository;
    private EstudianteRepository estudianteRepository;

    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestHeader("Authorization") String authHeader) {
        log.info("Validacion del token para control de seguridad");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", "false"));
        }

        String token = authHeader.substring(7);
        String isValid = String.valueOf(jwtUtils.isTokenValid(token));
        String username = "";
        String role = jwtUtils.getRoleFromToken(token);
        if (Boolean.parseBoolean(isValid)){
            username = jwtUtils.getUsernameFromToken(token);
        }
        return ResponseEntity.ok(Map.of("valid", isValid , "username", username, "role", role));
    }

    @GetMapping("/validateUser")
    public ResponseEntity<Map<String, String>> validateUser(@RequestParam(name="user") String user, @RequestParam(name="id") UUID id,
                                                            @RequestParam(name="matricula") Long matricula) {
        if(usuarioRepository.findByEmail(user.toLowerCase()).isEmpty()|| estudianteRepository.findById(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = jwtUtils.generatePasswordResetToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("resetToken", token);

        return ResponseEntity.ok(response);
    }
}
