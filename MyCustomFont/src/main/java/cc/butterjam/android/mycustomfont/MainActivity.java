package cc.butterjam.android.mycustomfont;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;

public class MainActivity extends Activity {

    /**
     * Update preview area, by replacing fragments
     */
    private void updatePreview() {
        // See a selected type of preview in the radio group
        RadioGroup selector = (RadioGroup) findViewById(R.id.font_selector);
        int selected = selector.getCheckedRadioButtonId();
        if (selected < 0) { // if none was selected..
            selected = R.id.default_font_button;
            selector.check(selected);
        }

        // Show the fragment
        MainActivityFragment.PreviewType previewType = MainActivityFragment.PreviewType.DEFAULT;
        switch (selected) {
            case R.id.custom_font_button:
                previewType = MainActivityFragment.PreviewType.CUSTOM;
                break;
        }
        FragmentManager fragmentManager = getFragmentManager();
        MainActivityFragment fragment = (MainActivityFragment) fragmentManager.findFragmentByTag(previewType.name());
        if (fragment == null) {
            fragment = MainActivityFragment.getInstance(previewType);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, previewType.name());
        fragmentTransaction.addToBackStack(previewType.name());
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup selector = (RadioGroup) findViewById(R.id.font_selector);
        selector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updatePreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        updatePreview();
    }
}
