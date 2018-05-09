package io.jenkins.plugins;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ScanTaskInfo;
import com.taobao.api.request.AlibabaSecurityJaqAppRiskScanRequest;
import com.taobao.api.response.AlibabaSecurityJaqAppRiskScanResponse;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.taobao.ScanRequest;
import io.jenkins.plugins.utils.MyLoggerUtil;

public class JiaGuServiceImpl implements JiaGuService {
    private String mKey;
    private String mSecret;
    private String mApkUrl;
    private AbstractBuild mBuild;
    private TaskListener mListener;
    public JiaGuServiceImpl(String key, String secret, String apkUrl, AbstractBuild build, TaskListener listener){
        MyLoggerUtil.i("JiaGuServiceImpl -- > key:"+key+",secret:"+secret+",apkUrl:"+apkUrl);
        mKey = key;
        mSecret = secret;
        mApkUrl = apkUrl;
        mBuild  = build;
        mListener = listener;
    }

    @Override
    public void start() {

    }

    @Override
    public void success() {
        MyLoggerUtil.i("build success");
        taobaoScan();
    }

    @Override
    public void failed() {
        MyLoggerUtil.i("build failed");
        taobaoScan();
    }

    private void getRealApkPath(){
    }

    /**
     * 淘宝扫描服务
     */
    private void taobaoScan(){
        try {
            mKey = "24865288";
            mSecret = "aac089444cab8a5fed5eb54320e862d5";
            TaobaoClient client = new DefaultTaobaoClient(Api.getApi(), mKey, mSecret);
            AlibabaSecurityJaqAppRiskScanRequest req = new AlibabaSecurityJaqAppRiskScanRequest();
            AlibabaSecurityJaqAppRiskScanRequest.ScanAppInfo obj1 = new AlibabaSecurityJaqAppRiskScanRequest.ScanAppInfo();
            obj1.setDataType(1L);
            obj1.setData("http://47.106.86.212:8080/fileManage/app_dajiankeshi_guanfang.apk");
            obj1.setMd5("450dcd7a39e69510d02fe293d8221fcc");
            obj1.setSize(22020096L);
            obj1.setCallbackUrl("http://aaa.com/callback");
            obj1.setAppOsType(1L);
            req.setAppInfo(obj1);
            req.setScanTypes("vuln,malware,fake");
            req.setExtParam("test");
            AlibabaSecurityJaqAppRiskScanResponse rsp = client.execute(req);
            ScanTaskInfo result = rsp.getResult();
            String body = rsp.getBody();
            MyLoggerUtil.i("taobaoScan -- > body :"+body);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
