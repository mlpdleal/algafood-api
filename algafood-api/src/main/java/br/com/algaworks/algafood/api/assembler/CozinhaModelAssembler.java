package br.com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.algaworks.algafood.api.model.CozinhaDTO;
import br.com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaDTO toModel(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	public List<CozinhaDTO> toCollectList(List<Cozinha> cozinhas){
		return cozinhas.stream()
				.map(cozinha -> toModel(cozinha))
				.collect(Collectors.toList());
	}

}
