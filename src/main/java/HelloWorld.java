import akka.actor.*;
import akka.http.javadsl.*;
import akka.http.javadsl.server.*;
import akka.stream.*;

import java.io.*;
import java.util.concurrent.*;

import static akka.http.javadsl.server.Directives.*;

import akka.stream.alpakka.cassandra.javadsl.*;
import akka.stream.javadsl.*;
import java.util.*;
import com.datastax.driver.core.*;

public class HelloWorld {

    public static void main(String[] args) throws IOException {
        final ActorSystem system = ActorSystem.create();
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final ConnectHttp host = ConnectHttp.toHost("127.0.0.1", 8080);

        Http.get(system).bindAndHandle(appRoute().flow(system, materializer), host, materializer);

        System.console().readLine("Visit http://localhost:8080/cassandra, Type RETURN to exit...");
        system.terminate();
    }

    public static Route appRoute() {
        return
                route(
                        path("", () -> complete("Hello Akka Cassandra, visit /cassandra")),
                        path("cassandra", () -> {
                            final ActorSystem system = ActorSystem.create();
                            final ActorMaterializer materializer = ActorMaterializer.create(system);
                            final Statement stmt = new SimpleStatement("select * from test.messages").setFetchSize(1);
                            Cluster cluster = null;
                            final Session session = cluster.builder()
                                    .addContactPoint("127.0.0.1").withPort(9042)
                                    .build().connect();
                            CompletionStage<List<Row>> rows = CassandraSource.create(stmt, session)
                                    .runWith(Sink.seq(), materializer);

                            return onSuccess(() -> rows, result -> complete(result.get(0).getString("message")));
                        })
                );
    }

}