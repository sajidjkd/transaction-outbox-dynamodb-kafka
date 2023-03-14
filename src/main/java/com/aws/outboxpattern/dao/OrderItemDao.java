package com.aws.outboxpattern.dao;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import com.aws.outboxpattern.client.DynamoDbConnection;
import com.aws.outboxpattern.model.OrderItem;

public class OrderItemDao {
	
 public static OrderItem getItem(String pk, String sk) { // To-Do conditional check pk and sk both needed ??? Handle Exceptions ?

        OrderItem result = null;

        try {
            DynamoDbTable<OrderItem> table = DynamoDbConnection.getDynamoDbEnhancedClient().table("OrderItem", TableSchema.fromBean(OrderItem.class));
            Key key = Key.builder()
                .partitionValue(pk).sortValue(sk)
                .build();

            // Get the item by using the key.
            result = table.getItem(
                    (GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    
    public void persistItem(OrderItem item) {

	}
}
