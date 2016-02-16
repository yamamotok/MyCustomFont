package cc.butterjam.android.mycustomfont;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment which draws preview area
 */
public class MainActivityFragment extends Fragment {
    /**
     * Custom font name (path)
     */
    public static final String FONT_NAME = "NotoSansCJKjp-Bold.otf";

    /**
     * Shared Typeface object
     */
    private static Typeface customFontTypeFace;

    /**
     * Get typeface from my custom font
     * @return shared Typeface object
     */
    public Typeface getCustomFontTypeFace() {
        if (customFontTypeFace == null) {
            customFontTypeFace = Typeface.createFromAsset(getActivity().getAssets(), FONT_NAME);
        }
        return customFontTypeFace;
    }

    /**
     * Arguments(Bundle) key, which shows preview-type of a fragment
     */
    public static final String BUNDLE_KEY_TYPE = "preview_type";

    /**
     * Enum to distinguish Preview-types
     */
    public enum PreviewType {
        DEFAULT, CUSTOM
    }

    /**
     * Factory
     * @param previewType determine preview appearance
     * @return new MainActivityFragment instance
     */
    public static MainActivityFragment getInstance(PreviewType previewType) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_TYPE, previewType.name());

        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * @return Preview-type of this instance
     */
    private PreviewType getPreviewType() {
        Bundle args = getArguments();
        String name = args.getString(BUNDLE_KEY_TYPE);
        return PreviewType.valueOf(name);
    }

    /**
     * Prepare TextView with a customized style
     * @param textView target
     * @param adjustLine adjust attributes, includeFontPadding, etc.
     */
    private void prepareTextView(TextView textView, boolean adjustLine) {
        // Apply custom font
        if (getPreviewType() == PreviewType.CUSTOM) {
            Typeface typeface = getCustomFontTypeFace();
            textView.setTypeface(typeface);
        }

        if (adjustLine) {
            textView.setIncludeFontPadding(false);
            textView.setLineSpacing(textView.getTextSize() * 1.2f, 0.0f);
            // textView.setLineSpacing(0.0f, 1.0f); // This does not work as expected with a custom font
        }

        // Set sample text
        StringBuilder sb = new StringBuilder();
        for (String line : getResources().getStringArray(R.array.sample_words)) {
            sb.append(line).append("\n");
        }
        sb.append(String.format("textSize:%f\nlineSpacingExtra:%f\nlineSpacingMultiplier:%f\nincludeFontPadding:%b",
                textView.getTextSize(),
                textView.getLineSpacingExtra(),
                textView.getLineSpacingMultiplier(),
                textView.getIncludeFontPadding()));
        textView.setText(sb.toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        prepareTextView((TextView) view.findViewById(R.id.sample_text), false);
        prepareTextView((TextView) view.findViewById(R.id.sample_text_adjusted), true);

        return view;
    }

    @Override
    public String toString() {
        return this.getClass().toString() + this.getPreviewType().name();
    }
}
