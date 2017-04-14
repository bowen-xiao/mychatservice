package widget.bowen.com.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

//	随机添加的颜色
	private int[] colors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
							0xFFE6B800, 0xFF7CFC00};

	private String[] texts = new String[]{"安全中心 ", "特色服务", "投资理财",
										  "转账汇款", "我的账户", "信用卡"};
	private int[] imgs = new int[]{R.drawable.home_mbank_1_normal,
								   R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
								   R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
								   R.drawable.home_mbank_6_normal};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final SettingView settingView = (SettingView) findViewById(R.id.setting1);
		settingView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				settingView.toggle();
			}
		});

//		MyPie pie = (MyPie) findViewById(R.id.mypie);
//		ArrayList<PieEntity> entities = new ArrayList<>();
//		for (int i = 8 ; i > 0 ;i--){
//			entities.add(new PieEntity(i,colors[i]));
//		}
//		pie.setData(entities);

//		CircleGroup circleGroup = (CircleGroup) findViewById(R.id.circle_group);
//		circleGroup.setData(texts,imgs);

	}

	//点击到下一个页面
	public void next(View view){
		Intent intent = new Intent(this, GoovActivity.class);
		startActivity(intent);
	}

}
