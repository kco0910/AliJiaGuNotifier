package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.Descriptor;
import hudson.model.Result;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import hudson.tasks.Publisher;

import javax.annotation.Nonnull;
import java.util.Map;

@Extension
public class JobListener extends RunListener<AbstractBuild> {

    public JobListener(){
        super(AbstractBuild.class);
    }

    @Override
    public void onStarted(AbstractBuild abstractBuild, TaskListener listener) {
        getService(abstractBuild,listener).start();
    }

    @Override
    public void onCompleted(AbstractBuild abstractBuild, @Nonnull TaskListener listener) {
        Result result = abstractBuild.getResult();
        if (null != result && result.equals(Result.SUCCESS)) {
            getService(abstractBuild, listener).success();
        } else {
            getService(abstractBuild, listener).failed();
        }
    }

    private JiaGuService getService(AbstractBuild build, TaskListener listener) {
        Map<Descriptor<Publisher>, Publisher> map = build.getProject().getPublishersList().toMap();
        for (Publisher publisher : map.values()) {
            if (publisher instanceof JiaGuNotifier) {
                return ((JiaGuNotifier) publisher).newJiaGuService(build, listener);
            }
        }
        return null;
    }
}
