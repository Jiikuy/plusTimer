package com.pluscubed.plustimer.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pluscubed.plustimer.R;
import com.pluscubed.plustimer.utils.PrefUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChangelogDialog extends DialogFragment {

    public static ChangelogDialog newInstance() {
        ChangelogDialog dialog = new ChangelogDialog();
        return dialog;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_webview, null);
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.changelog)
                .customView(customView, false)
                .positiveText(android.R.string.ok)
                .build();

        final WebView webView = (WebView) customView.findViewById(R.id.webview);
        try {
            // Load from changelog.html in the assets folder
            StringBuilder buf = new StringBuilder();
            InputStream html = getResources().openRawResource(R.raw.changelog);
            BufferedReader in = new BufferedReader(new InputStreamReader(html));
            String str;
            while ((str = in.readLine()) != null)
                buf.append(str);
            in.close();

            // Inject color values for WebView body background and links
            webView.loadData(buf.toString(), "text/html; charset=UTF-8", null);
        } catch (Throwable e) {
            webView.loadData("<h1>Unable to load</h1><p>" + e.getLocalizedMessage() + "</p>", "text/html", "UTF-8");
        }

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        PrefUtils.saveVersionCode(getActivity());
    }
}