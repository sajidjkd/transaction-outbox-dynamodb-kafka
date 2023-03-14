package com.aws.outboxpattern.dao;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.aws.outboxpattern.client.DynamoDbConnection;
import com.aws.outboxpattern.model.Order;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class OrderDao {
	
	public static Order getOrder(String customerId, String orderId) { // To-Do conditional check pk and sk both needed ??? Handle Exceptions ?

        Order result = null;

        try {
            DynamoDbTable<Order> table = DynamoDbConnection.getDynamoDbEnhancedClient().table("Order", TableSchema.fromBean(Order.class));
            Key key = Key.builder()
                .partitionValue(customerId).sortValue(orderId)
                .build();

            // Get the item by using the key.
            result = table.getItem(
                    (GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    
    public void putRecord(Order order) {

	}
    
    public static void putRecord(DynamoDbEnhancedClient enhancedClient) {
        try {
        	DynamoDbTable<Order> orderTable = DynamoDbConnection.getDynamoDbEnhancedClient().table("Order", TableSchema.fromBean(Order.class));

            // Create an Instant value.
            LocalDate localDate = LocalDate.parse("2020-04-07");
            LocalDateTime localDateTime = localDate.atStartOfDay();
            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

            // Populate the Table.
            Order custRecord = new Order();
          /*  custRecord.setCustName("Tom red");
            custRecord.setId("id101");
            custRecord.setEmail("tred@noserver.com");
            custRecord.setRegistrationDate(instant) ;

            // Put the customer data into an Amazon DynamoDB table.
            custTable.putItem(custRecord);*/

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Customer data added to the table with id id101");
    }

}
