package br.com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.algaworks.algafood.api.model.input.EstadoInput;
import br.com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainModel(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	public void copyToDomainModel(EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}

}
