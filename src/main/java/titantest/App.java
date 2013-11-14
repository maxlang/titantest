package titantest;

import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.thinkaurelius.faunus.FaunusFactory;
import com.thinkaurelius.faunus.FaunusGraph;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.graphdb.query.TitanPredicate;
import com.tinkerpop.blueprints.*;
import com.tinkerpop.blueprints.Predicate;

import javax.annotation.Nullable;
import javax.print.StreamPrintServiceFactory;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
//    public enum IntegerPredicateOperator {
//        GREATER_THAN, LESS_THAN, EQUAL, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, NOT_EQUAL
//    }
//
//    public class MapIntegerPredicate implements com.google.common.base.Predicate<Map.Entry<Element,Number>> {
//        IntegerPredicateOperator o;
//        Number n;
//        MapIntegerPredicate(IntegerPredicateOperator o, Number n) {
//            this.o = o;
//            this.n = n;
//        }
//
//        @Override
//        public boolean apply(@Nullable Map.Entry<Element,Number> entry) {
//            if (entry == null) {
//                return false;
//            }
//            Number number = entry.getValue();
//            switch (this.o) {
//                case GREATER_THAN:
//                    return n.doubleValue() > number.doubleValue();
//                    break;
//                case LESS_THAN:
//                    return n.doubleValue() < number.doubleValue();
//                    break;
//                case EQUAL:
//                    return n.doubleValue() == number.doubleValue();
//                    break;
//                case GREATER_THAN_EQUAL:
//                    return n.doubleValue() >= number.doubleValue();
//                    break;
//                case LESS_THAN_EQUAL:
//                    return n.doubleValue() <= number.doubleValue();
//                    break;
//                case NOT_EQUAL:
//                    return n.doubleValue() != number.doubleValue();
//                    break;
//                default:
//                   return false;
//            }
//        }
//    }

    public static void main( String[] args ) throws Exception {
//        System.out.println( "Hello World!" );
//
//        TitanGraph g = GraphOfTheGodsCreator.create("/tmp/titantest/");
//
//        System.out.println(g.toString());

//        TitanGraph g  = TitanFactory.open('');

        String filePath = new File("").getAbsolutePath();

        System.out.println();

//        FaunusGraph g = com.thinkaurelius.faunus.formats.titan.GraphFactory.open(filePath.concat("/config.properties"));
        TitanGraph g = TitanFactory.open(filePath.concat("/config.properties"));
//        FaunusGraph g = FaunusFactory.open(filePath.concat("/config.properties"));


//        System.out.println(g);
//
//        for (Vertex user : g.query().has("type","User").vertices()) {
//            long moviesWatched = user.query().direction(Direction.OUT).has("stars").count();
//            long moviesLiked = user.query().direction(Direction.OUT).has("stars", Query.Compare.GREATER_THAN_EQUAL, 4).count();
//            long moviesHated = user.query().direction(Direction.OUT).has("stars", Query.Compare.LESS_THAN_EQUAL, 2).count();
//
//            System.out.println(moviesWatched + " " + moviesLiked + " " + moviesHated);
//
//            user.setProperty("moviesWatched", moviesWatched);
//            user.setProperty("moviesLiked", moviesLiked);
//            user.setProperty("moviesHated", moviesHated);
//
//        }
//        g.commit();


//        g.v(144).in('hasGenera').inE('rated').filter{it.stars >= 4}.outV.groupCount.cap.next()._().filter{tempTotal = it.key.outE.filter{ it.stars >= 4 }.count(); tempTotal < (it.value * 3)}.transform{it.key}.outE.filter{it.stars >= 4}.inV.out('hasGenera').map.groupCount.cap

        Vertex scifi = g.getVertex(144);
        HashMap<Element, Number> userMovieCounts = new HashMap<Element, Number>();

        long startTime = System.currentTimeMillis();



        // for get all movies with genre sci fi
        for (Vertex movie : scifi.query().direction(Direction.IN).vertices()) {
            // get all users who rated that movie more than 4 stars and count the number of times they've rated a scifi movie more than 4 stars
//            long startTime = System.currentTimeMillis();
//
//            System.out.println("scifi movie users " + movie.query().direction(Direction.IN).has("stars", Query.Compare.GREATER_THAN_EQUAL, 4).count());

            for (Vertex user : movie.query().direction(Direction.IN).has("stars", Query.Compare.GREATER_THAN_EQUAL, 4).vertices()) {
                Number prev = userMovieCounts.get(user);
                if (prev == null) {
                    userMovieCounts.put(user, 1);
                } else {
                    userMovieCounts.put(user, prev.intValue() + 1);
                }
            }
//            long endTime = System.currentTimeMillis();
//
//            long duration = endTime - startTime;
//            System.out.println("User Count Time " + (duration));

        }
        long startTime2 = System.currentTimeMillis();

        // filter out users who've rated fewer than 4 movies highly
            com.google.common.base.Predicate<Map.Entry<Element,Number>> p = new MapNumberPredicate(MapNumberPredicate.IntegerPredicateOperator.GREATER_THAN, 3);

            Iterable<Map.Entry<Element, Number>> filteredUsers = Iterables.filter(userMovieCounts.entrySet(), p);

            long endTime2 = System.currentTimeMillis();

            long duration2 = endTime2 - startTime2;
            System.out.println("Filter Time " + (duration2));

            LinkedList<Vertex> scifiLovers = new LinkedList<Vertex>();

            long startTime3 = System.currentTimeMillis();

            int i = 0;

            // for the remaining users, check that sci fi films are at least 1/3 of the films they've rated highly (so these are sci fi lovers)
            for (Map.Entry<Element, Number> user : filteredUsers) {
                i++;
                long scifiCount = user.getValue().longValue();
                Vertex v = (Vertex)user.getKey();

//                long startTime4 = System.nanoTime();


                long totalCount = v.getProperty("moviesLiked");
//                long endTime4 = System.nanoTime();
//
//                long duration4 = endTime4 - startTime4;
//                System.out.println("user movie count time " + (duration4));

//                if (scifiCount * 3 >= totalCount) {
//                    scifiLovers.push(v);
//                }
            }

        System.out.println(i);


        long endTime3 = System.currentTimeMillis();

            long duration3 = endTime3 - startTime3;
            System.out.println("Total user movie count1: " + (duration3));


        // for the remaining users, check that sci fi films are at least 1/3 of the films they've rated highly (so these are sci fi lovers)
        for (Map.Entry<Element, Number> user : filteredUsers) {
            i++;
            long scifiCount = user.getValue().longValue();
            Vertex v = (Vertex)user.getKey();

//                long startTime4 = System.nanoTime();


            long totalCount = v.getProperty("moviesLiked");
//                long endTime4 = System.nanoTime();
//
//                long duration4 = endTime4 - startTime4;
//                System.out.println("user movie count time " + (duration4));

//                if (scifiCount * 3 >= totalCount) {
//                    scifiLovers.push(v);
//                }
        }

        long endTime4 = System.currentTimeMillis();

        long duration4 = endTime4 - endTime3;
        System.out.println("Total user movie count try2: " + (duration4));

        int mb = 1024*1024;

            Runtime runtime = Runtime.getRuntime();


            System.out.println("Total Memory:" + runtime.totalMemory() / mb);

            //Print Maximum available memory
            System.out.println("Max Memory:" + runtime.maxMemory() / mb);
            System.out.println(scifiLovers.size());

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Total time " + (duration));

//            HashMap<Element, Number> populare

//            for (Vertex v : scifiLovers) {
////                v.query().labels("rated").has("stars", Query.Compare.GREATER_THAN_EQUAL, 4).co
////                for(String prop : v.getPropertyKeys()) {
////                    Sy
////                }
//            }
//        }



//        g.makeKey("movieId").dataType(String.class).indexed("search",Vertex.class).indexed("standard",Vertex.class).make();
//        g.makeKey("userId").dataType(String.class).indexed("search",Vertex.class).indexed("standard",Vertex.class).make();
//        g.makeKey("name").dataType(String.class).indexed("search",Vertex.class).indexed("standard",Vertex.class).make();
//        g.makeKey("title").dataType(String.class).indexed("search",Vertex.class).indexed("standard",Vertex.class).make();
//        g.makeKey("occupation").dataType(String.class).indexed("search",Vertex.class).indexed("standard",Vertex.class).make();
//        g.makeKey("genera").dataType(String.class).indexed("search",Vertex.class).indexed("standard",Vertex.class).make();
//        g.makeKey("type").dataType(String.class).indexed("search",Vertex.class).indexed("standard",Vertex.class).make();
//        g.makeKey("stars").dataType(Double.class).indexed("search", Edge.class).indexed("standard", Edge.class).make();
//
//        g.makeLabel("hasGenera").make();
//        g.makeLabel("hasOccupation").make();
//        g.makeLabel("rated").make();
//
//////
////        g.createKeyIndex("type", Vertex.class, new Parameter<String, Boolean>("search", true), new Parameter<String, Boolean>("standard", true));
////
////        g.createKeyIndex("genera", Vertex.class, new Parameter<String, Boolean>("search", true), new Parameter<String, Boolean>("standard", true));
//
//        g.commit();
//
//        g.makeKey("genera").dataType(String.class).indexed("search",Vertex.class).make();
//        g.makeKey("movieId").dataType(String.class).indexed("search",Vertex.class).make();
//        g.makeKey("userId").dataType(String.class).indexed("search",Vertex.class).make();
//        g.makeKey("name").dataType(String.class).indexed("search",Vertex.class).make();
//        g.makeKey("type").dataType(String.class).indexed("search",Vertex.class).make();
//        g.makeKey("stars").dataType(Double.class).indexed("search",Edge.class).make();
//
//
//        String[] occupations = {"other","academic/educator","artist","clerical/admin", "college/grad student","customer service","doctor/health care", "executive/managerial","farmer","homemaker","K-12 student","lawyer","programmer","retired","sales/marketing","scientist","self-employed","technician/engineer","tradesman/craftsman","unemployed","writer"};
//
//        String moviePath = filePath.concat("/movies.dat");
//
//        BufferedReader movieReader = null;
//        String line;
//
//        try {
//
//            movieReader = new BufferedReader(new FileReader(moviePath));
//
//            while ((line = movieReader.readLine()) != null) {
//
//                // use comma as separator
//                String[] country = line.split(cvsSplitBy);
//
//                System.out.println("Country [code= " + country[4]
//                        + " , name=" + country[5] + "]");
//
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (movieReader != null) {
//                try {
//                    movieReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        
        
        
//        new File('movies.dat').eachLine {line ->
//            components = line.split('::');
//        a = ['type':'Movie','movieId':components[0].toInteger(),'title':components[1]];
//        movieVertex = g.addVertex(a);
//        components[2].split('\\|').each { genera ->
//                hits = g.V('genera', genera).iterator();
//            generaVertex = hits.hasNext() ? hits.next() : g.addVertex(['type':'Genera', 'genera':genera]);
//            g.addEdge(movieVertex, generaVertex, 'hasGenera');
//        };
//    };
//        new File('users.dat').eachLine {line ->
//            components = line.split('::');
//        name = 'user'+components[0].toInteger();
//        a = ['type':'User','userId':components[0].toInteger(),'gender':components[1],'age':components[2].toInteger(),'name':name];
//        userVertex = g.addVertex(a);
//        occupation = occupations[components[3].toInteger()];
//        hits = g.V('occupation', occupation).iterator();
//        occupationVertex = hits.hasNext() ? hits.next() : g.addVertex(['type':'Occupation', 'occupation':occupation]);
//        g.addEdge(userVertex, occupationVertex, 'hasOccupation');
//    };
//        movie = [:];
//        user = [:];
//        new File('ratings.dat').eachLine {line ->
//            components = line.split('::');
//        if(movie[components[1].toInteger()]==null) {
//            x = g.V('movieId',components[1].toInteger()).iterator();
//            movie[components[1].toInteger()]=x;
//        };
//        if(user[components[0].toInteger()]==null) {
//            u = g.V('movieId',components[0].toInteger()).iterator();
//            user[components[0].toInteger()]=u;
//        };
//    };
//        if(true) {
//            new File('ratings.dat').eachLine {line ->
//                    components = line.split('::');
//                x = g.V('movieId',components[1].toInteger()).iterator();
//                u = g.V('userId',components[0].toInteger()).iterator();
//                if(x.hasNext()&&u.hasNext()) {
//                    ratedEdge = g.addEdge(u.next(), x.next(), 'rated');
//                    ratedEdge.setProperty('stars', components[2].toDouble());
//                };
//            };
//        }
//        if(false) {
//            new File('ratings.dat').eachLine {line ->
//                    components = line.split('::');
//                x = movie[components[1].toInteger()];
//                u = user[components[0].toInteger()];
//                if(x.hasNext() && u.hasNext()) {
//                    ratedEdge = g.addEdge(u.next(), x.next(), 'rated');
//                    ratedEdge.setProperty('stars', components[2].toDouble());
//                };
//            };
//        };
//        g.stopTransaction(TransactionalGraph.Conclusion.SUCCESS);


    }
}
