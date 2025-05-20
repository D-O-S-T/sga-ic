package br.edu.undf.sga_ic.utils;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.ProjetoEdital;
import br.edu.undf.sga_ic.repository.ProjetoEditalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjetoEditalUtils {

    private final CustomExceptionUtils customExceptionUtils;

    private final ProjetoEditalRepository projetoEditalRepository;

    public ProjetoEdital findById(Long id) throws CustomException {
        return projetoEditalRepository.findById(id)
                .orElseThrow(() -> customExceptionUtils.errorAndNotFound("Registro informado não encontrado"));
    }
}