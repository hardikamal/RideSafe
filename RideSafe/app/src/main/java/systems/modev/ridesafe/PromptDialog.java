package systems.modev.ridesafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.wallet.fragment.Dimension;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * helper for Prompt-Dialog creation
 */
public abstract class PromptDialog extends AlertDialog.Builder implements OnClickListener {
    private EditText input;
    private String phoneNumber;

    /**
     * @param context
     * @param title resource id
     * @param message resource id
     */
    public PromptDialog(Context context, int title, int message) {
        super(context);
        setTitle(title);
        setMessage(message);

        try {
            phoneNumber = MainActivity.read("number.txt", this.getContext()).trim();

        }

        catch (IOException e) {
            phoneNumber = "";
        }

        input = new EditText(context);
        input.setText(phoneNumber);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        setView(input);

        setPositiveButton("Accept", this);
        setNegativeButton("Cancel", this);
    }

    /**
     * will be called when "cancel" pressed.
     * closes the dialog.
     * can be overridden.
     * @param dialog
     */
    public void onCancelClicked(DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (onOkClicked(input.getText().toString())) {
                dialog.dismiss();
            }
        } else {
            onCancelClicked(dialog);
        }
    }

    /**
     * called when "ok" pressed.
     * @param input
     * @return true, if the dialog should be closed. false, if not.
     */
    abstract public boolean onOkClicked(String input);
}