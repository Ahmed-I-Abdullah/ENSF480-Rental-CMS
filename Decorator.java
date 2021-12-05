package src.main.view;

public abstract class Decorator implements Widget {
	protected Widget wid;
	protected  int x;
	protected  int y;
	protected  int w;
	protected int h;
	
	public Decorator(Widget wid, int x, int y, int w, int h){
		this.wid=wid;
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}
}	