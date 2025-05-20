package com.corretora.repository;

import com.corretora.domain.MovimentacoesBancarias;
import com.corretora.domain.TipoMovimentacoes;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoBancariaRepository extends MongoRepository<MovimentacoesBancarias, String> {
    List<MovimentacoesBancarias> findByTipo(TipoMovimentacoes tipo);

    List<MovimentacoesBancarias> findByUsuarioIdAndTipo(ObjectId usuarioId, TipoMovimentacoes tipo);

    List<MovimentacoesBancarias> findByDataMovimentacaoBetween(LocalDate inicio, LocalDate fim);
}
