package com.aws.outboxpattern.infrastructure.lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.aws.outboxpattern.dao.OrderItemDao;
import com.aws.outboxpattern.model.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.http.SdkHttpMethod;

public class LambdaRequestHandler implements RequestStreamHandler {

	private OrderItemDao dao;

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.US_ASCII));
		AwsProxyRequest request = mapper.readValue(reader, AwsProxyRequest.class);

		try {
			AwsProxyResponse awsProxyResponse = new AwsProxyResponse();
			awsProxyResponse.setStatusCode(HttpStatusCode.OK);

			dao = new OrderItemDao();

			if (request.getHttpMethod().equalsIgnoreCase(SdkHttpMethod.POST.name())) {
				System.out.println("Inside POST...");
				OrderItem entity = mapper.readValue(request.getBody(), OrderItem.class);
				System.out.println("request.getBody():"+request.getBody());
				dao.persistItem(entity);
				awsProxyResponse.setBody("Success");
			} else if (request.getHttpMethod().equalsIgnoreCase(SdkHttpMethod.GET.name())) {
				String pk = request.getPathParameters().get("orderId"); // replace with PK value
				String sk = request.getPathParameters().get("productId");
				OrderItem entity = dao.getItem(pk, sk);
				awsProxyResponse.setBody(mapper.writeValueAsString(entity));
			}

			output.write(mapper.writeValueAsString(awsProxyResponse).getBytes());

		} catch (Exception e) {
			System.out.println("Error..."+e.getMessage());
			e.printStackTrace();
		}

	}

}
