package io.jenkins.plugins.taobao;

import com.taobao.api.ApiRuleException;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoUploadRequest;
import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.request.AlibabaSecurityJaqAppRiskScanRequest;
import com.taobao.api.response.AlibabaSecurityJaqAppRiskScanResponse;

import java.util.HashMap;
import java.util.Map;

public class ScanRequest implements TaobaoUploadRequest<AlibabaSecurityJaqAppRiskScanResponse> {
    private String appInfo;
    private String extParam;
    private String scanTypes;
    private TaobaoHashMap udfParams;
    private String targetAppKey;
    private Map<String, String> headerMap;
    private String session;
    private int order;
    private Map<String,FileItem> fileItemMap;

    @Override
    public Map<String, FileItem> getFileParams() {
        if (fileItemMap  == null){
            fileItemMap = new HashMap<>();
        }
        return fileItemMap;
    }

    public void addFileParams(String fileName,FileItem fileItem){
        Map<String, FileItem> fileParams = getFileParams();
        fileParams.put(fileName,fileItem);
    }

    @Override
    public String getApiMethodName() {
        return "alibaba.security.jaq.app.risk.scan";
    }

    @Override
    public Map<String, String> getTextParams() {
        TaobaoHashMap txtParams = new TaobaoHashMap();
        txtParams.put("app_info", this.appInfo);
        txtParams.put("ext_param", this.extParam);
        txtParams.put("scan_types", this.scanTypes);
        if (this.udfParams != null) {
            txtParams.putAll(this.udfParams);
        }
        return txtParams;
    }

    @Override
    public Long getTimestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public String getTargetAppKey() {
        return targetAppKey;
    }

    @Override
    public Class<AlibabaSecurityJaqAppRiskScanResponse> getResponseClass() {
        return AlibabaSecurityJaqAppRiskScanResponse.class;
    }

    @Override
    public Map<String, String> getHeaderMap() {
        if (this.headerMap == null) {
            this.headerMap = new TaobaoHashMap();
        }
        return this.headerMap;
    }

    @Override
    public void check() throws ApiRuleException {
        RequestCheckUtils.checkMaxListSize(this.scanTypes, 20, "scanTypes");
    }

    @Override
    public String getBatchApiSession() {
        return session;
    }

    @Override
    public void setBatchApiSession(String s) {
        this.session = s;
    }

    @Override
    public int getBatchApiOrder() {
        return this.order;
    }

    @Override
    public void setBatchApiOrder(int i) {
        this.order = i;
    }

    public void setAppInfo(AlibabaSecurityJaqAppRiskScanRequest.ScanAppInfo appInfo){
        this.appInfo = (new JSONWriter(false, true)).write(appInfo);
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public void setScanTypes(String scanTypes) {
        this.scanTypes = scanTypes;
    }

    public void putOtherTextParam(String key, String value) {
        if (this.udfParams == null) {
            this.udfParams = new TaobaoHashMap();
        }

        this.udfParams.put(key, value);
    }

    public void setTargetAppKey(String targetAppKey) {
        this.targetAppKey = targetAppKey;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }


}
