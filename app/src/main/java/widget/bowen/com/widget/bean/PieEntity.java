package widget.bowen.com.widget.bean;

/**
 * Created by 肖稳华 on 2017/2/13.
 */

public class PieEntity {

	//扇形的占比
	float value;

	//扇形的颜色
	int color;

	public PieEntity(float value, int color) {
		this.value = value;
		this.color = color;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
