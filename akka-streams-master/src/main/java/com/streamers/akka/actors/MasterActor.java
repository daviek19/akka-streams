package com.streamers.akka.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.streamers.akka.messages.MapData;
import com.streamers.akka.messages.ReduceData;
import com.streamers.akka.messages.Result;

public class MasterActor extends AbstractLoggingActor {

    static public Props props() {
        return Props.create(MasterActor.class, () -> new MasterActor());
    }

    private final ActorRef mapActor = getContext().actorOf(MapActor.props());
    private final ActorRef reduceActor = getContext().actorOf(ReduceActor.props());
    private final ActorRef aggregateActor = getContext().actorOf(AggregateActor.props());

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, (t) -> {
                    mapActor.tell(t, getSelf());
                })
                .match(MapData.class, (t) -> {
                    reduceActor.tell(t, getSelf());
                })
                .match(ReduceData.class, (t) -> {
                    aggregateActor.tell(t, getSelf());
                })
                .match(Result.class, (t) -> {
                    aggregateActor.forward(t, getContext());
                })
                .build();
    }
}
