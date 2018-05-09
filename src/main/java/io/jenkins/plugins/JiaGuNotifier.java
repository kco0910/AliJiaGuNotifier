package io.jenkins.plugins;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import io.jenkins.plugins.utils.MyLoggerUtil;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;

public class JiaGuNotifier extends Notifier {
    private String mApkPath;
    private String mAppKey;
    private String mSecret;

    @DataBoundConstructor
    public JiaGuNotifier(String key,String secret,String apkPath){
        mAppKey = key;
        mSecret = secret;
        mApkPath = apkPath;
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    public JiaGuService newJiaGuService(AbstractBuild build, TaskListener listener) {
        return new JiaGuServiceImpl(mAppKey,mSecret,mApkPath,build,listener);
    }


    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        MyLoggerUtil.i("perform -- >");
        getDefaultURL(build);
        return super.perform(build, launcher, listener);
    }

    @Override
    public JiaGuDescriptor getDescriptor() {
        return (JiaGuDescriptor)super.getDescriptor();
    }

    @Extension
    public static class JiaGuDescriptor extends BuildStepDescriptor<Publisher>{

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "阿里聚安全加固";
        }
    }

    public void getDefaultURL(AbstractBuild build) {
        Jenkins instance = Jenkins.getInstance();
        String rootUrl = instance.getRootUrl();
        String rawWorkspaceDir = instance.getRawWorkspaceDir();
        FilePath workspace = build.getWorkspace();
        MyLoggerUtil.i("getDefaultURL --- > rootUrl:"+rootUrl+",rawWorkspaceDir:"+rawWorkspaceDir+",workspace:"+workspace);
    }
}
