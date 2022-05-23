package ir.tehranpuzzle.mistery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ir.tehranpuzzle.mistery.models.ERole;
import ir.tehranpuzzle.mistery.models.Role;
import ir.tehranpuzzle.mistery.repositorys.RoleRepository;

@Component
public class DemoData {

    @Autowired
    private RoleRepository repo;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Long roles = repo.count();
        if(roles < 2){ 
        repo.save(new Role(ERole.ROLE_ADMIN));
        repo.save(new Role(ERole.ROLE_USER));
        }
    }
}