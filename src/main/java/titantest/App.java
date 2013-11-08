package titantest;

import com.thinkaurelius.titan.core.TitanGraph;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        TitanGraph g = GraphOfTheGodsCreator.create("/tmp/titantest/");

        System.out.println(g.toString());

    }
}
