package com.corretora;

import com.corretora.service.CompraVendaService;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CorretoraApplication {
    public static void main(String[] args) {
        SpringApplication.run(CorretoraApplication.class, args);

//        ApplicationContext context = SpringApplication.run(CorretoraApplication.class, args);
//
//        // Obtem o bean gerenciado pelo Spring
//        CompraVendaService compraVendaService = context.getBean(CompraVendaService.class);
//
//        // Simulação da compra
//        ObjectId id = new ObjectId("681a205a88220e2d2194689f");
//        String resultado = compraVendaService.comprarAcao(id, "PETR4", 10);
//        System.out.println(resultado);
    }
}





