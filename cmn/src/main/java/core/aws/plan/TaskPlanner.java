package core.aws.plan;

import core.aws.plan.as.ASGroupTaskPlanner;
import core.aws.plan.ec2.RoleTaskPlanner;
import core.aws.plan.ec2.InstanceTaskPlanner;
import core.aws.plan.ec2.SGTaskPlanner;
import core.aws.plan.elb.ELBTaskPlanner;
import core.aws.plan.elb.v2.TargetGroupTaskPlanner;
import core.aws.plan.vpc.InternetGatewayTaskPlanner;
import core.aws.plan.vpc.NATGatewayTaskPlanner;
import core.aws.plan.vpc.RouteTableTaskPlanner;
import core.aws.plan.vpc.SubnetTaskPlanner;
import core.aws.workflow.Tasks;

/**
 * @author neo
 */
public class TaskPlanner {
    public void plan(Tasks tasks) {
        new RoleTaskPlanner(tasks).plan();
        new SGTaskPlanner(tasks).plan();
        new InstanceTaskPlanner(tasks).plan();
        new SubnetTaskPlanner(tasks).plan();
        new RouteTableTaskPlanner(tasks).plan();
        new NATGatewayTaskPlanner(tasks).plan();
        new InternetGatewayTaskPlanner(tasks).plan();
        new TargetGroupTaskPlanner(tasks).plan();
        new ELBTaskPlanner(tasks).plan();
        new core.aws.plan.elb.v2.ELBTaskPlanner(tasks).plan();
        new ASGroupTaskPlanner(tasks).plan();
    }
}
