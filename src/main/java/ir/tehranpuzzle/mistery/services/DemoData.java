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
        if(roles < 3){ 
        repo.save(new Role(ERole.ADMIN));
        repo.save(new Role(ERole.USER));
        repo.save(new Role(ERole.SHOP_OWNER));
        }
    }
}