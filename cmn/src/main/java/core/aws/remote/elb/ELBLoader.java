package core.aws.remote.elb;

import com.amazonaws.services.elasticloadbalancing.model.DescribeTagsRequest;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.elasticloadbalancing.model.Tag;
import core.aws.client.AWS;
import core.aws.env.Environment;
import core.aws.resource.Resources;
import core.aws.resource.elb.ELB;

import java.util.List;

/**
 * @author neo
 */
public class ELBLoader {
    private final Resources resources;
    private final Environment env;

    public ELBLoader(Resources resources, Environment env) {
        this.resources = resources;
        this.env = env;
    }

    public void load() {
        List<LoadBalancerDescription> remoteELBs = AWS.getElb().listELBs();
        for (LoadBalancerDescription remoteELB : remoteELBs) {
            String elbName = remoteELB.getLoadBalancerName();
            String prefix = env.name + "-";
            if (elbName.startsWith(prefix) && "1".equals(getVersion(elbName))) {
                String resourceId = elbName.substring(prefix.length());
                ELB elb = resources.find(ELB.class, resourceId).orElseGet(() -> resources.add(new ELB(resourceId)));
                elb.name = elbName;
                elb.remoteELB = remoteELB;
                elb.foundInRemote();
            }
        }
    }

    private String getVersion(String elbName) {
        List<Tag> tags = AWS.getElb().elb.describeTags(new DescribeTagsRequest()
            .withLoadBalancerNames(elbName)).getTagDescriptions().get(0).getTags();
        return tags.stream().filter(tag -> "cloud-manager:elb-version".equals(tag.getKey())).map(Tag::getValue).findFirst().orElse("1");
    }
}