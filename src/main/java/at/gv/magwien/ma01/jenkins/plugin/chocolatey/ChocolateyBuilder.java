package at.gv.magwien.ma01.jenkins.plugin.chocolatey;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;

public class ChocolateyBuilder extends Builder implements SimpleBuildStep {

    private final String msiPath;
    private boolean useFrench;
    private final String packageId;
    private final String title;
    private final String summary;
    private final String description;
    private final List<String> dependencies;
    private final String dependency = null;
    private final String authors;
    private final String projectUrl;
    private final String tags;
    private final String releaseNotes;
    private final String provides;
    private final String conflicts;
    private final String replaces;

    @DataBoundConstructor
    public ChocolateyBuilder(String msiPath,
                             String packageId,
                             String title,
                             String summary,
                             String description,
                             List<String> dependencies,
                             String authors,
                             String projectUrl,
                             String tags,
                             String releaseNotes,
                             String provides,
                             String conflicts,
                             String replaces) {
        this.msiPath = msiPath;
        this.packageId = packageId;
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.dependencies = dependencies;
        this.authors = authors;
        this.projectUrl = projectUrl;
        this.tags = tags;
        this.releaseNotes = releaseNotes;
        this.provides = provides;
        this.conflicts = conflicts;
        this.replaces = replaces;
    }

    public String getMsiPath() {
        return msiPath;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public void setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public String getDependency() {
        return dependency;
    }

    public String getAuthors() {
        return authors;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public String getTags() {
        return tags;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public String getProvides() {
        return provides;
    }

    public String getConflicts() {
        return conflicts;
    }

    public String getReplaces() {
        return replaces;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        if (useFrench) {
            listener.getLogger().println("Bonjour, " + msiPath + "!");
        } else {
            listener.getLogger().println("Hello, " + msiPath + "!");
        }
    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value, @QueryParameter boolean useFrench)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());
            if (value.length() < 4)
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_tooShort());
            if (!useFrench && value.matches(".*[éáàç].*")) {
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_reallyFrench());
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.HelloWorldBuilder_DescriptorImpl_DisplayName();
        }

    }

}
