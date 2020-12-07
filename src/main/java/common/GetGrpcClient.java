package common;

import com.alibaba.fastjson.JSONObject;
import com.hualala.grpc.client.GrpcClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GetGrpcClient {

    private final String crmAdr;
    private final String crmAdr_test;
    private final String crmAdr_pre;
    private final String promotionAdr;
    private final String crmEtcd;

    public GetGrpcClient(){
        crmAdr = "";
        crmAdr_test = "";
        crmAdr_pre = "";
//        crmAdr_pre = "";
        promotionAdr = "";
        crmEtcd = "";
    }

    public GrpcClient getClient(){
        GrpcClient client = new GrpcClient(crmAdr_pre);
        return client;
    }

    public GrpcClient getClient(String str){
        GrpcClient client = new GrpcClient(str);
        return client;
    }

    public static Object setFIieds(String ss, Class cla) throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
//        System.out.println(cla.getName());
        Constructor con = cla.getDeclaredConstructor(null);
        con.setAccessible(true);
        Object obj = con.newInstance();
        // 将json文本转换成json对象
        JSONObject jsonObject = JSONObject.parseObject(ss);
//        System.out.println(jsonObject.toJSONString());
        for (int i = 0; i < cla.getDeclaredFields().length; i++) {
            Field fil = cla.getDeclaredFields()[i];
//            System.out.println(fil.getName());
            if(Modifier.isPrivate(fil.getModifiers()) && !fil.getName().contains("bitField")){
                fil.setAccessible(true);
                Object ob = jsonObject.get(fil.getName().replace("_",""));
                if(ob == null) {
                    continue;
                }
                Object result = null;
                switch (fil.getType().getSimpleName()) {
                    case "int":
                        result = Integer.valueOf(ob.toString());
                        break;
                    case "long":
                        result = Long.valueOf(ob.toString()).longValue();
                        break;
                    default:
                        result = ob;
                        break;
                }
                fil.set(obj,result);
            }
        }
        return obj;
    }


}
