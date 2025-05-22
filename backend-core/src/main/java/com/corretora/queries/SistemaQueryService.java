package com.corretora.queries;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SistemaQueryService {

    private final MongoTemplate mongoTemplate;

    public <T> T executarConsulta(String queryId, Map<String, Object> parametros, Class<T> tipoResultado) {
        Document queryDoc = mongoTemplate.findById(queryId, Document.class, "queries");
        if (queryDoc == null) throw new IllegalArgumentException("Query não encontrada: " + queryId);

        String collection = queryDoc.getString("collection");
        if (collection == null || collection.isBlank())
            throw new IllegalArgumentException("Collection não especificada para a query: " + queryId);

        List<Document> pipelineOriginal = (List<Document>) queryDoc.get("pipeline");
        List<Document> pipelinePreparado = substituirParametros(pipelineOriginal, parametros);

        List<AggregationOperation> operations = pipelinePreparado.stream()
                .map(doc -> (AggregationOperation) context -> doc)
                .toList();
        Aggregation agg = Aggregation.newAggregation(operations);

        AggregationResults<T> results = mongoTemplate.aggregate(agg, collection, tipoResultado);
        return results.getUniqueMappedResult(); // ou .getMappedResults() para lista
    }

    private List<Document> substituirParametros(List<Document> pipeline, Map<String, Object> parametros) {
        List<Document> novoPipeline = new ArrayList<>();
        for (Document stage : pipeline) {
            Document novoStage = substituirRecursivo(stage, parametros);
            novoPipeline.add(novoStage);
        }
        return novoPipeline;
    }

    @SuppressWarnings("unchecked")
    private Document substituirRecursivo(Document doc, Map<String, Object> parametros) {
        Document result = new Document();
        for (Map.Entry<String, Object> entry : doc.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String && ((String) value).startsWith("#")) {
                String key = ((String) value).substring(1);
                result.put(entry.getKey(), parametros.getOrDefault(key, value));
            } else if (value instanceof Document) {
                result.put(entry.getKey(), substituirRecursivo((Document) value, parametros));
            } else if (value instanceof List<?>) {
                List<Object> novaLista = new ArrayList<>();
                for (Object item : (List<?>) value) {
                    if (item instanceof Document) {
                        novaLista.add(substituirRecursivo((Document) item, parametros));
                    } else if (item instanceof String && ((String) item).startsWith("#")) {
                        String key = ((String) item).substring(1);
                        novaLista.add(parametros.getOrDefault(key, item));
                    } else {
                        novaLista.add(item);
                    }
                }
                result.put(entry.getKey(), novaLista);
            } else {
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }
}

