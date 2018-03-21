# Akka-http Cassandra Hello World
Minimal example of akka-http-cassandra

## Install Cassandra/Python and Start Cassandra
```
$ brew install python
$ vi ~/.bash_profile ## add the followings to it
alias python='python3'
alias pip='pip3'
$ brew install cassandra
$ pip install cql
$ brew services start cassandra
$ brew services list
$ cqlsh
cqlsh> CREATE KEYSPACE test WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};
cqlsh:test> CREATE TABLE messages(id uuid PRIMARY KEY, message text);
cqlsh:test> INSERT INTO messages (id, message) values(uuid(), 'Hello World From Casssandra Table test.messages');
cqlsh:test> select * from messages;
 id                                   | message
--------------------------------------+-------------------------------------------------
 18cc0cf1-695c-4196-a2e6-c188ff88c914 | Hello World From Casssandra Table test.messages
```

## Start App.
```
$ ##### Tutorial Install/Run   #####
$ git clone https://github.com/allenhwkim/akka-http-helloworld.git
$ cd play-helloworld
$ sbt run
```
