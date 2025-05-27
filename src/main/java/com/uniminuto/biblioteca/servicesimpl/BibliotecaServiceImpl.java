package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.model.TestRs;
import com.uniminuto.biblioteca.services.BibliotecaService;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class BibliotecaServiceImpl implements BibliotecaService {

    @Override
    public TestRs probarApi() {
        TestRs testRs = new TestRs();
        testRs.setStatus(200);
        testRs.setMessage("Esto es la verg+ mi hermano");
        return testRs;
    }
    
}