package org.salinerobotics.mouseodometry;

public class MouseOdometrySubsystem {
	
	Mouse[] mice;
	
	public MouseOdometrySubsystem(Mouse[] mice, String host, int port){
		this.mice = mice;
		ClientManyMouse client = new ClientManyMouse(host, port);
		
	}
	
	public MouseOdometrySubsystem(Mouse[] mice, String host) {
		
		this.mice = mice;
		ClientManyMouse client = new ClientManyMouse(host);
	}
	
	public MouseOdometrySubsystem(Mouse[] mice) {
		
		this.mice = mice;
	}
	
}
