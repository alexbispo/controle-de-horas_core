package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.beans.RegistroArquivoBean;

/**
 * 
 * @author Cassio Lemos
 *
 */
public class RegistroDAO extends GenericDao<RegistroArquivoBean> {

	public void salvar(RegistroArquivoBean registro) {
		save(registro);
	}

	public void alterar(RegistroArquivoBean registro) {
		update(registro);
	}

	public void excluir(long id) {
		RegistroArquivoBean c = findById(id);
		delete(c);
	}

	public List<RegistroArquivoBean> findByRegistroArquivoBeans(
			RegistroArquivoBean registro) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("data", registro.getData());
		parametros.put("horaInicial", registro.getHoraInicial());
		parametros.put("horaFinal", registro.getHoraFinal());
		return findByMapFields(parametros);

	}

}
