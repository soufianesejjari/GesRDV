package com.sejjari.gesrdv.securite;

import com.sejjari.gesrdv.data.entity.Role;
import com.sejjari.gesrdv.data.entity.Utilisateur;
import com.sejjari.gesrdv.data.repository.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur utilisateur =utilisateurRepository.findByEmail(username).orElseThrow(() -> new RuntimeException(" Uttilisateur non trouve "));

        return new User(utilisateur.getEmail(),utilisateur.getMotDePasse(),mapRolesToAuthoriy( utilisateur.getRoles()));
    }

    public Collection <GrantedAuthority>  mapRolesToAuthoriy(List<Role> roles){
        System.out.println("Roles: 2" + roles.toString());

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
