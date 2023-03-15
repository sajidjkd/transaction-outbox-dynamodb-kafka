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
import com.aws.outboxpattern.application.dto.CreateOrderCommand;
import com.aws.outboxpattern.application.service.OrderApplicationService;
import com.aws.outboxpattern.application.service.OrderApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.http.SdkHttpMethod;
@Slf4j
public class OrderRequestHandler implements RequestStreamHandler {

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.US_ASCII));
		AwsProxyRequest request = mapper.readValue(reader, AwsProxyRequest.class);

		AwsProxyResponse awsProxyResponse = new AwsProxyResponse();

		log.info("Sysout awsProxyResponse:"+awsProxyResponse);
		log.info("Logging awsProxyResponse:"+awsProxyResponse);

		try {

			awsProxyResponse.setStatusCode(HttpStatusCode.OK);

			if (request.getHttpMethod().equalsIgnoreCase(SdkHttpMethod.POST.name())) {

				log.info("Logging:Inside POST...:::");

				CreateOrderCommand createOrderCommand = mapper.readValue(request.getBody(), CreateOrderCommand.class);
				System.out.println("createOrderCommand::"+createOrderCommand);

				OrderApplicationService service = new OrderApplicationServiceImpl();
				service.createOrder(createOrderCommand);
			}

			if (request.getHttpMethod().equalsIgnoreCase(SdkHttpMethod.GET.name())) {
				System.out.println("Inside GET...");
			}

		} catch (Exception e) {
			
			System.out.println("Error processing..."+e.getMessage());
			
			e.printStackTrace();

			awsProxyResponse.setStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
			output.write(mapper.writeValueAsString(awsProxyResponse).getBytes());
		}
		output.write(mapper.writeValueAsString(awsProxyResponse).getBytes());
	}
}
