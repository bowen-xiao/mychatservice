package widget.bowen.com.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GoovActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goov);

		List<Msg> msgs = new ArrayList<>();
		for (int i = 4; i < 25; i++) {
			msgs.add(new Msg("标题" + "i",i));
		}
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		MsgAdapter adapter = new MsgAdapter(msgs,this);
		recyclerView.setAdapter(adapter);

//		GoovView goovView = (GoovView) findViewById(R.id.gooview);
//		goovView.setText("6");
//		goovView.setPosition(300,500);
	}
}
