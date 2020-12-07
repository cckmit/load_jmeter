
import com.hualala.app.crm.service.grpc.*;
import com.hualala.grpc.client.GrpcClient;
import common.GetGrpcClient;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import static common.GetGrpcClient.setFIieds;

public class CardInfoSample extends AbstractJavaSamplerClient {
    public CardInfoServiceGrpc.CardInfoServiceFutureStub futureStub;
    public CrmGiftServiceGrpc.CrmGiftServiceFutureStub crmGiftServiceFutureStub;
    public CustomerServiceGrpc.CustomerServiceFutureStub customerServiceFutureStub;
    public CardTypeParamsServiceGrpc.CardTypeParamsServiceFutureStub cardTypeParamsServiceFutureStub;
    public GrpcClient client = null;

    /**
     * 自定义java方法入参的, 设置可用参数及默认值
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("reqJson", "");
        params.addArgument("operation", "");
        return params;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        client = new GetGrpcClient().getClient();

    }

    private CardInfoData.CardInfoReq cardInfo(String ss) throws Exception {
//        client = new GetGrpcClient().getClient();
        futureStub = (CardInfoServiceGrpc.CardInfoServiceFutureStub)client.getFutureStub(CardInfoServiceGrpc.class);
        Class cla = CardInfoData.CardInfoReq.class;
        Object obj = setFIieds(ss, cla);
        CardInfoData.CardInfoReq infoReq = (CardInfoData.CardInfoReq)obj ;
        return infoReq;
    }
    private CustomerData.CustomerReq customerReq(String ss) throws Exception {
        customerServiceFutureStub= (CustomerServiceGrpc.CustomerServiceFutureStub) client.getFutureStub(CustomerServiceGrpc.class);
        Class cla = CustomerData.CustomerReq.class;
        Object obj = setFIieds(ss, cla);
        CustomerData.CustomerReq infoReq = (CustomerData.CustomerReq) obj;
        return infoReq;
    }

    private CardTypeParamsData.CardTypeParamsReq cardTypeParamsReq(String ss) throws Exception {
        cardTypeParamsServiceFutureStub= (CardTypeParamsServiceGrpc.CardTypeParamsServiceFutureStub) client.getFutureStub(CardTypeParamsServiceGrpc.class);
        Class cla = CardTypeParamsData.CardTypeParamsReq.class;
        Object obj = setFIieds(ss, cla);
        CardTypeParamsData.CardTypeParamsReq infoReq = (CardTypeParamsData.CardTypeParamsReq) obj;
        return infoReq;
    }

    private CrmGiftData.CrmGiftRequest giftRequest(String ss) throws Exception {
//        client = new GetGrpcClient().getClient();
        crmGiftServiceFutureStub = (CrmGiftServiceGrpc.CrmGiftServiceFutureStub) client.getFutureStub(CrmGiftServiceGrpc.class);
        Class cla = CrmGiftData.CrmGiftRequest.class;
        Object obj = setFIieds(ss, cla);
        CrmGiftData.CrmGiftRequest request= (CrmGiftData.CrmGiftRequest)obj;
        return request;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        String reqJson = javaSamplerContext.getParameter("reqJson");
        String operation = javaSamplerContext.getParameter("operation");
        boolean flag = true;
        SampleResult result = new SampleResult();
        try {
            result.sampleStart();
            Object res = null;
            CardInfoData.CardInfoReq cardInfoReq;
            switch (operation) {
                case "query":
                    // 查询会员卡
                    cardInfoReq = cardInfo(reqJson);
                    res = futureStub.queryCardInfo(cardInfoReq).get();
                    break;
                case "open":
                    // 查询会员卡
                    cardInfoReq = cardInfo(reqJson);
                    res = futureStub.openCard(cardInfoReq).get();
                    break;
                case "getGiftFromHll":
                    CrmGiftData.CrmGiftRequest request = giftRequest(reqJson);
                    res = crmGiftServiceFutureStub.getGiftFromHll(request).get();
                case "customer":
                    CustomerData.CustomerReq customer= customerReq(reqJson);
                    res = customerServiceFutureStub.queryCustomerInfo(customer).get();
                case "cardTypeParam":
                    CardTypeParamsData.CardTypeParamsReq cardTypeParamsReqs = cardTypeParamsReq(reqJson);
                    res = cardTypeParamsServiceFutureStub.queryCardTypeParamsList(cardTypeParamsReqs).get();
                default:
                    // 查询会员卡
                    cardInfoReq = cardInfo(reqJson);
                    res = futureStub.queryCardInfo(cardInfoReq).get();
                    break;
            }
            result.setRequestHeaders(cardInfoReq.toString());
            result.setResponseMessage(res.toString());
            result.setResponseData(res.toString(), "utf-8");
        } catch (Exception e) {
            flag = false;
            result.setResponseMessage(e.toString());
        } finally {
            result.setSuccessful(flag);
            result.sampleEnd();
        }

        return result;
    }


    public static void main(String[] args) {
        CardInfoSample test = new CardInfoSample();
        Arguments arguments = new Arguments();
        String json = "{\n" +
                "    \"groupID\":121955,\n" +
                "    \"createShopID\":0,\n" +
                "    \"cardNO\":\"\",\n" +
                "    \"customerMobile\":\"17600370152\",\n" +
                "    \"cardTypeID\":6712356429883247715,\n" +
                "    \"cardLevelID\":6712356429883251811,\n" +
                "    \"shopID\":\"76160789\",\n" +
                "    \"sourceWay\":\"0\",\n" +
                "    \"sourceType\":\"60\",\n" +
                "}";
        arguments.addArgument("reqJson", json);
        arguments.addArgument("operation", "");
        JavaSamplerContext samplerContext = new JavaSamplerContext(arguments);
        test.runTest(samplerContext);
        test.teardownTest(samplerContext);
    }

}
