import com.hualala.app.crm.service.grpc.CrmConsumeData;
import com.hualala.app.crm.service.grpc.CrmConsumeServiceGrpc;
import com.hualala.grpc.client.GrpcClient;
import common.GetGrpcClient;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import static common.GetGrpcClient.setFIieds;

public class CardConsumeSample extends AbstractJavaSamplerClient {
    CrmConsumeServiceGrpc.CrmConsumeServiceFutureStub futureStub;
    GrpcClient client = null;

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

    public CrmConsumeData.CrmConsumeReq cardInfo(String ss) throws Exception {
//        client = new GetGrpcClient().getClient();
        futureStub = (CrmConsumeServiceGrpc.CrmConsumeServiceFutureStub)client.getFutureStub(CrmConsumeServiceGrpc.class);
        Class cla = CrmConsumeData.CrmConsumeReq.class;
        Object obj = setFIieds(ss, cla);
        CrmConsumeData.CrmConsumeReq infoReq = (CrmConsumeData.CrmConsumeReq)obj ;
//        System.out.println(infoReq.toString());
        return infoReq;
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
            // 查询会员卡
            CrmConsumeData.CrmConsumeReq cardInfoReq = cardInfo(reqJson);
            switch (operation) {
                case "consume":
                    res = futureStub.cardConsume(cardInfoReq).get();
                    break;
                case "update":
                    res = futureStub.updatePointBalanceAfterConsumption(cardInfoReq).get();
                    break;
                default:
                    res = futureStub.cardConsume(cardInfoReq).get();
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

}
