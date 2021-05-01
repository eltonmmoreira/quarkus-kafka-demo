package com.demo.balance;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/balance")
public class BalanceResource {

    @GET
    @Produces("application/json")
    @Transactional
    public Response findAll() {
        List<Balance> balanceList = Balance.findAll().list();
        var response = balanceList.stream()
                .map(r -> new BalanceResponse(
                                    r.ticker,
                                    r.quantity,
                                    r.totalPrice,
                                    r.averagePrice,
                                    r.accountId)
                );
        return Response.ok(response).build();
    }

    @POST
    @Produces("application/json")
    @Transactional
    public void create(@RequestBody OrderEvent event) {
        var balance = Balance.builder()
                .ticker(event.ticker)
                .quantity(event.quantity)
                .totalPrice(event.totalPrice)
                .accountId(event.accountId)
                .build()
                .registerBalance(event.operation);

        System.out.println(balance);
    }
}
