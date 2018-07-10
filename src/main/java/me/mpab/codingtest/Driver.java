package me.mpab.codingtest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import me.mpab.codingtest.dataset.DataSet;

import java.util.List;

/**
 * Michael Bailey - TopBlocCodingTest - me.mpab.codingtest - Driver
 *
 * This driver performs the multiplied/divided/concat operations then
 * sends a request to the server. Then, it prints a response
 */
public class Driver {
    public static void main(String[] args) {
        // create data sets and exit if failed
        DataSet one;
        DataSet two;

        try {
            one = new DataSet("./src/main/resources/Data1.xlsx");
            two = new DataSet("./src/main/resources/Data2.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // perform operations on sets
        List<Integer> multipliedSet = DataSet.multiply(one, two);
        List<Integer> dividedSet = DataSet.divide(one, two);
        List<String> concatSet = DataSet.concat(one, two);

        System.out.println("Result of multiplying numberSetOne: "+multipliedSet);
        System.out.println("Result of dividing numberSetTwo: "+dividedSet);
        System.out.println("Result of concating wordSetOne: "+concatSet);
        System.out.println();

        // perform post request
        HttpResponse<String> response;

        try {
            response = Unirest.post("http://34.239.125.159:5000/challenge")
                    .header("accept", "application/json")
                    .field("id", "mpabailey@gmail.com")
                    .field("numberSetOne", multipliedSet)
                    .field("numberSetTwo", dividedSet)
                    .field("wordSetOne", concatSet)
                    .asString();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // print out response
        System.out.println("Server Response: "+response.getStatus());
        System.out.println(response.getBody());
    }
}
