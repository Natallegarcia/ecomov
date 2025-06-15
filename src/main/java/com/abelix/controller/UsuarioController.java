package com.abelix.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.abelix.repository.UsuarioRepository;
import com.abelix.model.UsuarioModel;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder; 

    public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
		
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos() {
        List<UsuarioModel> usuarios = repository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuario) {
    	 System.out.println("Recebido: " + usuario);
    	 usuario.setPassword(encoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(repository.save(usuario));
        
    }
    
    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password){
    	
    	
    	Optional<UsuarioModel> optUsuario = repository.findByLogin(login); // 1 consulto o usuário. Se o usuario não for encontrado pelo login será retornado não autorizado com o valor falso
    	if (optUsuario.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    	}
    	
    	boolean valid = false; 
    	
    	UsuarioModel usuario = optUsuario.get();
    	valid = encoder.matches(password,optUsuario.get().getPassword()); // valida se a senha informada é a mesma senha do banco de dados 
    	// o método matches está comparando a senha aberta com a senha encriptada feito pelo encoder. 
    	HttpStatus status;
		if (valid)
			status = HttpStatus.OK;
		else
			status = HttpStatus.UNAUTHORIZED;
    	
    	if (!valid) {}
    	
    	return ResponseEntity.status(status).body(valid);  //retornar o usuário pelo login
    	
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<UsuarioModel> optUsuario = repository.findByLogin(loginRequest.getLogin());

        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }

        UsuarioModel usuario = optUsuario.get();

        if (encoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
        }
    }
    
    
}
