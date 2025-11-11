package in.ankit.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.ankit.dto.Role;
import in.ankit.entity.RoleEntity;
import in.ankit.exceptions.IdNotFoundException;
import in.ankit.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> getAllRoles() {
        Iterable<RoleEntity> roleEntities = roleRepository.findAll();
        List<Role> roles = new ArrayList<>();
        roleEntities.forEach(role -> roles.add(modelMapper.map(role, Role.class)));
        return roles;
    }

    public Role getRoleById(Long id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException("role", id));
        return modelMapper.map(role, Role.class);
    }

    public Role createRole(Role role) {
        RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
        RoleEntity savedRole = roleRepository.save(roleEntity);
        return modelMapper.map(savedRole, Role.class);
    }

}
