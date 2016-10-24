package zb.springframework.context;

import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject{

	private static final long serialVersionUID = 2223760484526384716L;

	public ApplicationEvent(Object source) {
		super(source);
	}

}
