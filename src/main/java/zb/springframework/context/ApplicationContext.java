package zb.springframework.context;

import org.springframework.core.io.support.ResourcePatternResolver;

import zb.springframework.beans.factory.HierarchicalBeanFactory;
import zb.springframework.beans.factory.ListableBeanFactory;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, MessageSource,
		ApplicationEventPublisher, ResourcePatternResolver {

}
