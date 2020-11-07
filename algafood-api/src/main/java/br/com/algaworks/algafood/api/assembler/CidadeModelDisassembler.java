package br.com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.algaworks.algafood.api.model.input.CidadeInput;
import br.com.algaworks.algafood.domain.model.Cidade;
import br.com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainModel(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainModel(CidadeInput cidadeInput, Cidade cidade) {
		
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInput, cidade);
		
	}

}
