import com.hualala.grpc.client.GrpcClient;
import com.hualala.shop.wechat.service.grpc.WechatCardCouponTemplateData;
import com.hualala.shop.wechat.service.grpc.WechatCardCouponTemplateServiceGrpc;
import common.GetGrpcClient;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import static common.GetGrpcClient.setFIieds;

public class WechatSample  extends AbstractJavaSamplerClient {
    static final String wechatAddr = "pre.shop.wechat.service.hualala.com";
    WechatCardCouponTemplateServiceGrpc.WechatCardCouponTemplateServiceFutureStub futureStub;
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
        client = new GetGrpcClient().getClient(wechatAddr);

    }



    public WechatCardCouponTemplateData.WechatCardEventHandlerReq reqs(String ss) throws Exception {
//        wechatClient = new GetGrpcClient().getClient(wechatAddr);
        futureStub = (WechatCardCouponTemplateServiceGrpc.WechatCardCouponTemplateServiceFutureStub) client.getFutureStub(WechatCardCouponTemplateServiceGrpc.class);
        Class cla = WechatCardCouponTemplateData.WechatCardEventHandlerReq.class;
        Object obj = setFIieds(ss, cla);
        WechatCardCouponTemplateData.WechatCardEventHandlerReq infoReq = (WechatCardCouponTemplateData.WechatCardEventHandlerReq)obj ;
        System.out.println(infoReq.toString());
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
            WechatCardCouponTemplateData.WechatCardEventHandlerReq req = reqs(reqJson);
            switch (operation) {
                case "query":
                    res = futureStub.queryCardTemplateByCardTemplateID(req).get();
                    break;
                default:
                    res = futureStub.queryCardTemplateByCardTemplateID(req).get();
                    break;
            }
            result.setRequestHeaders(req.toString());
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
