package br.iesb.songs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.SmartTabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var smartTabLayout: SmartTabLayout;
    private lateinit var viewPager: ViewPager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);

//        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//            getSupportFragmentManager(), FragmentPagerItems.with(this)
//                .add(R.string.titleA, PageFragment.class)
//                    .add(R.string.titleB, PageFragment.class)
//                        .create());
//
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(adapter);
//
//        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
//        viewPagerTab.setViewPager(viewPager);


    }
}
