package com.demo.order;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/orders")
public class OrderResource {

    @Inject
    @Channel("orders")
    public Emitter<OrderEvent> emitter;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response create(@Valid OrderRequest orderRequest) {
        var order = new Order(
                orderRequest.ticker,
                orderRequest.operation,
                orderRequest.quantity,
                orderRequest.price,
                orderRequest.userName
        ).postSave(emitter::send);
        order.persist();
        //emitter.send(orderRequest.ticker);
        return Response.created(URI.create(String.format("/orders/%s", order.id)))
                .entity(order).build();
    }
}
