package br.com.peddroccas.vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.peddroccas.vacancy_management.exceptions.UserFoundException;
import br.com.peddroccas.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.peddroccas.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity) {

        this.companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getUsername())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword((password));

        return this.companyRepository.save(companyEntity);
    }
}
