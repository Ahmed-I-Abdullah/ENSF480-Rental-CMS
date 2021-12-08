package src.main.view;


/**
abstract class all concrete Decorators inherit from
*/
public abstract class Decorator implements Widget {
	protected Widget wid;
	protected  int x;
	protected  int y;
	protected  int w;
	protected int h;
	
/**
Constructs a Decorator object, the decorator object is abstract
 @param  wid a Widget Reference passed in
 @param  x the x position of the Decorator
 @param  y the y position of the Decorator
 @param  w the width of the Decorator
 @param h the height of the Decorator
 */
	public Decorator(Widget wid, int x, int y, int w, int h){
		this.wid=wid;
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}
}	