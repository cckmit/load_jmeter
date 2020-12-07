import com.hualala.app.crm.service.grpc.CrmGiftData;
import com.hualala.app.crm.service.grpc.CrmGiftServiceGrpc;
import com.hualala.grpc.client.GrpcClient;
import common.GetGrpcClient;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class GetGiftFromHll extends AbstractJavaSamplerClient {


    public CrmGiftServiceGrpc.CrmGiftServiceFutureStub crmGiftServiceFutureStub;
    private GrpcClient client = null;

    /**
     * 自定义java方法入参的, 设置可用参数及默认值
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("groupID", "");
        params.addArgument("pageNo", "");
        params.addArgument("PageSize", "");
        params.addArgument("PageCount", "");
        params.addArgument("TotalSize", "");
        params.addArgument("SourceWay", "");
        params.addArgument("SourceType", "");
        params.addArgument("SourceOpenID", "");
        params.addArgument("MpID", "");
        params.addArgument("AppID", "");
        params.addArgument("GiftItemID", "");
        params.addArgument("WechatGiftCode", "");
        params.addArgument("WechatGiftCardID", "");
        return params;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        client = new GetGrpcClient().getClient();

    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        Long groupID = javaSamplerContext.getLongParameter("groupID");
        String pageNo = javaSamplerContext.getParameter("pageNo");
        String PageCount = javaSamplerContext.getParameter("PageCount");
        String PageSize = javaSamplerContext.getParameter("PageSize");
        String TotalSize = javaSamplerContext.getParameter("TotalSize");
        String SourceWay = javaSamplerContext.getParameter("SourceWay");
        String SourceType = javaSamplerContext.getParameter("SourceType");
        String SourceOpenID = javaSamplerContext.getParameter("SourceOpenID");
        String MpID = javaSamplerContext.getParameter("MpID");
        String AppID = javaSamplerContext.getParameter("AppID");
        Long GiftItemID = javaSamplerContext.getLongParameter("GiftItemID");
        String WechatGiftCode = javaSamplerContext.getParameter("WechatGiftCode");
        String WechatGiftCardID = javaSamplerContext.getParameter("WechatGiftCardID");
        boolean flag = true;
        SampleResult result = new SampleResult();
        try {
            result.sampleStart();
            Object res = null;
            crmGiftServiceFutureStub = (CrmGiftServiceGrpc.CrmGiftServiceFutureStub) client.getFutureStub(CrmGiftServiceGrpc.class);
            CrmGiftData.CrmGiftRequest request = CrmGiftData.CrmGiftRequest.newBuilder().setGroupID(groupID)
                    .setPageNo(pageNo)
                    .setPageSize(PageSize)
                    .setPageCount(PageCount)
                    .setTotalSize(TotalSize)
                    .setSourceWay(SourceWay)
                    .setSourceType(SourceType)
                    .setSourceOpenID(SourceOpenID)
                    .setMpID(MpID)
                    .setAppID(AppID)
                    .addGiftDetailList(CrmGiftData.GiftDetailListModel.newBuilder().setGiftItemID(GiftItemID)
                            .setWechatGiftCode(WechatGiftCode)
                            .setWechatGiftCardID(WechatGiftCardID).build())
                    .build();
            res = crmGiftServiceFutureStub.getGiftFromHll(request).get();
            result.setRequestHeaders(request.toString());
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
