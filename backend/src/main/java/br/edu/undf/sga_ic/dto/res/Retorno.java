package br.edu.undf.sga_ic.dto.res;

import br.edu.undf.sga_ic.enums.SeverityStatus;
import lombok.Builder;

@Builder
public record Retorno(

		String message, SeverityStatus severityStatus) {
}
